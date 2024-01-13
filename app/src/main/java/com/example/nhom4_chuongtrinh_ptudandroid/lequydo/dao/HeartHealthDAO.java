package com.example.nhom4_chuongtrinh_ptudandroid.lequydo.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhom4_chuongtrinh_ptudandroid.common.database.DbHelper;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model.HeartHealth;

import java.util.ArrayList;
import java.util.List;

public class HeartHealthDAO {
    private SQLiteDatabase db;
    private int user_id;
    public HeartHealthDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        UserSession userSession = UserSession.getInstance();
        user_id = userSession.getUserId();
    }
    public long insert(HeartHealth obj){
        ContentValues values = new ContentValues();;
        values.put("heartbeat",obj.getHeartbeat());
        values.put("heart_pressure",obj.getHeart_pressure());
        values.put("created_date",obj.getCreated_date());
        values.put("status",obj.getStatus());
        values.put("user_id",this.user_id);
        return db.insert("heart_health", null, values);
    }
    public long delete(String id){
        return db.delete("heart_health", "heart_health_id=?", new String[]{id});
    }
    @SuppressLint("Range")
    public List<HeartHealth> getData(String startDateString, String finishDateString){
        List<HeartHealth> list = new ArrayList();
        Cursor cursor = null;
        String query = "SELECT * FROM heart_health";
        query+=" WHERE user_id = "+String.valueOf(this.user_id);
        if(startDateString!=null && finishDateString!=null){
            query+=" AND created_date BETWEEN ? AND ?";
            String[] selectionArgs = {startDateString, finishDateString};
            cursor = db.rawQuery(query, selectionArgs);
        }
        else{
            cursor = db.rawQuery(query, null);
        }
        while (cursor.moveToNext()){
            HeartHealth obj = new HeartHealth();
            obj.setHeart_health_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("heart_health_id"))));
            obj.setHeartbeat(Float.parseFloat(cursor.getString(cursor.getColumnIndex("heartbeat"))));
            obj.setHeart_pressure(Float.parseFloat(cursor.getString(cursor.getColumnIndex("heart_pressure"))));
            obj.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            obj.setCreated_date(cursor.getString(cursor.getColumnIndex("created_date")));
            obj.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
            list.add(obj);
        }
        cursor.close();
        return list;
    }
    public List<HeartHealth> getAll(){
        return getData(null,null);
    }
    public HeartHealth getID(String id){
        String sql = "SELECT * FROM heart_health WHERE heart_health_id =?";
        List<HeartHealth> list = getData(sql, id);
        return list.get(0);
    }

}
