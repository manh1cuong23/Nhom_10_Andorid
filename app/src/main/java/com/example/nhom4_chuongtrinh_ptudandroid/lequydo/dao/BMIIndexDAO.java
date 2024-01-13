package com.example.nhom4_chuongtrinh_ptudandroid.lequydo.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhom4_chuongtrinh_ptudandroid.common.database.DbHelper;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model.BMIIndex;

import java.util.ArrayList;
import java.util.List;

public class BMIIndexDAO {
    private SQLiteDatabase db;
    private int user_id;

    public BMIIndexDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        UserSession userSession = UserSession.getInstance();
        user_id = userSession.getUserId();
    }

    public long insert(BMIIndex obj) {
        ContentValues values = new ContentValues();
        values.put("height", obj.getHeight());
        values.put("weight", obj.getWeight());
        values.put("status", obj.getStatus());
        values.put("created_date", obj.getCreated_date());
        values.put("user_id", this.user_id);
        return db.insert("bmi_index", null, values);
    }

        public long delete(String id) {
            return db.delete("bmi_index", "bmi_id=?", new String[]{id});
        }

    @SuppressLint("Range")
    public List<BMIIndex> getData(String startDateString,String finishDateString) {
        List<BMIIndex> list = new ArrayList();
        Cursor cursor = null;
        String query = "SELECT * FROM bmi_index";
        query+=" WHERE user_id = "+String.valueOf(this.user_id) ;
        if(startDateString!=null && finishDateString!=null){
            query+=" AND created_date BETWEEN ? AND ?";
            String[] selectionArgs = {startDateString, finishDateString};
            cursor = db.rawQuery(query, selectionArgs);
        }
        else{
            cursor = db.rawQuery(query, null);
        }
        while (cursor.moveToNext()) {
            BMIIndex obj = new BMIIndex();
            obj.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
            obj.setBmi_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("bmi_id"))));
            obj.setHeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex("height"))));
            obj.setWeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex("weight"))));
            obj.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            obj.setCreated_date(cursor.getString(cursor.getColumnIndex("created_date")));
            list.add(obj);
        }
        return list;
    }

    public List<BMIIndex> getAll() {
        return getData(null,null);
    }

    public BMIIndex getID(String id) {
        String sql = "SELECT * FROM bmi_index WHERE bmi_id=?";
        List<BMIIndex> list = getData(sql, id);
        return list.get(0);
    }
}
