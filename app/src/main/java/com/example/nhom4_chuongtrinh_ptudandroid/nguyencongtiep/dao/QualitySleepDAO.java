package com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhom4_chuongtrinh_ptudandroid.common.database.DbHelper;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.model.QualitySleep;
import java.util.ArrayList;
import java.util.List;

public class QualitySleepDAO {
    private SQLiteDatabase db;
    private int user_id;
    public QualitySleepDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        UserSession userSession = UserSession.getInstance();
        user_id = userSession.getUserId();
    }
    public int getUser_id(){
        return this.user_id;
    }
    public long insert(QualitySleep obj){
        ContentValues values = new ContentValues();
        values.put("start_sleep",obj.getStart_sleep());
        values.put("finish_sleep",obj.getFinish_sleep());
        values.put("status",obj.getStatus());
        values.put("created_date",obj.getCrated_date());
        values.put("user_id",this.user_id);
        return db.insert("quality_sleep", null, values);
    }
    public long delete(String id){
        return db.delete("quality_sleep", "sleep_id=?", new String[]{id});
    }
    @SuppressLint("Range")
    public List<QualitySleep> getData(String startDateString, String finishDateString){
        List<QualitySleep> list = new ArrayList();
        Cursor cursor = null;
        String query = "SELECT * FROM quality_sleep";
        query+=" WHERE user_id = "+String.valueOf(this.user_id) ;
        if(startDateString!=null && finishDateString!=null){
            query+=" AND created_date BETWEEN ? AND ?";
            String[] selectionArgs = {startDateString, finishDateString};
            cursor = db.rawQuery(query, selectionArgs);
        }
        else{
            cursor = db.rawQuery(query, null);
        }
        while (cursor.moveToNext()){
            QualitySleep obj = new QualitySleep();
            obj.setSleep_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("sleep_id"))));
            obj.setStart_sleep(cursor.getString(cursor.getColumnIndex("start_sleep")));
            obj.setFinish_sleep(cursor.getString(cursor.getColumnIndex("finish_sleep")));
            obj.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            obj.setCrated_date(cursor.getString(cursor.getColumnIndex("created_date")));
            obj.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
            list.add(obj);
        }
        cursor.close();
        return list;
    }
    public List<QualitySleep> getAll(){
        String sql = "SELECT * FROM quality_sleep";
        sql+=" WHERE user_id = "+String.valueOf(this.user_id);
        return getData(null,null);
    }
    public QualitySleep getID(String id){
        String sql = "SELECT * FROM quality_sleep WHERE sleep_id =?";
        List<QualitySleep> list = getData(sql, id);
        return list.get(0);
    }

}
