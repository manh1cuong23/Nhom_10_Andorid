package com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nhom4_chuongtrinh_ptudandroid.common.database.DbHelper;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.model.RemindWater;

import java.util.ArrayList;
import java.util.List;

public class RemindWaterDAO {
    private SQLiteDatabase db;
    private int user_id;
    public RemindWaterDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        UserSession userSession = UserSession.getInstance();
        user_id = userSession.getUserId();
    }
    public long insert(RemindWater obj){
        ContentValues values = new ContentValues();
        values.put("amount",obj.getAmount());
        values.put("frequency",obj.getFrequency());
        values.put("created_time",obj.getCreated_time());
        values.put("created_date",obj.getCreated_date());
        values.put("user_id",this.user_id);
        return db.insert("remind_water", null, values);
    }
    public int getUser_id(){
        return this.user_id;
    }
    @SuppressLint("Range")
    public List<RemindWater> getData(String sql, String...selectionArgs){
        List<RemindWater> list = new ArrayList();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            RemindWater obj = new RemindWater();
            obj.setRemind_water_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("remind_water_id"))));
            obj.setAmount(Float.parseFloat(cursor.getString(cursor.getColumnIndex("amount"))));
            obj.setFrequency(Float.parseFloat(cursor.getString(cursor.getColumnIndex("frequency"))));
            obj.setCreated_time(cursor.getString(cursor.getColumnIndex("created_time")));
            obj.setCreated_date(cursor.getString(cursor.getColumnIndex("created_date")));
            obj.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
            list.add(obj);
        }
        cursor.close();
        return list;
    }
    public List<RemindWater> getAllInDay(String date) {
        String sql = "SELECT * FROM remind_water WHERE created_date = ? AND user_id = ?";
        return getData(sql, date, String.valueOf(this.user_id));
    }
    public int getTotalConsumed() {
        int totalConsumed = 0;

        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM remind_water WHERE user_id = ?", new String[]{String.valueOf(this.user_id)});
        if (cursor.moveToFirst()) {
            totalConsumed = cursor.getInt(0);
        }
        cursor.close();

        return totalConsumed;
    }
    public void deleteAll() {
        db.delete("remind_water", null, null);
    }
    @SuppressLint("Range")
    public String getCreatedDate() {
        String createdDate = "";
        Cursor cursor = db.rawQuery("SELECT created_date FROM remind_water LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            createdDate = cursor.getString(cursor.getColumnIndex("created_date"));
            cursor.close();
        }
        return createdDate;
    }
    public void updateFrequency(float frequency) {
        ContentValues values = new ContentValues();
        values.put("frequency", frequency);

        db.update("remind_water", values, "user_id=?", new String[]{String.valueOf(this.user_id)});
    }
    @SuppressLint("Range")
    public float getFrequencyFromDatabase() {
        float frequency = 0.0f;
        String query = "SELECT frequency FROM remind_water LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            frequency = cursor.getFloat(cursor.getColumnIndex("frequency"));
            cursor.close();
        } else {
            // Xử lý trường hợp cursor không có dữ liệu
            Log.e("ReminderService", "Error: Cursor is null or empty");
        }
        return frequency;
    }
    @SuppressLint("Range")
    public float getWeightFromDatabase(int userId) {
        float weight = 0.0f;

        String query = "SELECT weight FROM bmi_index WHERE user_id = ? ORDER BY created_date DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    weight = cursor.getFloat(cursor.getColumnIndex("weight"));
                }
            } finally {
                cursor.close();
            }
        }

        return weight;
    }
    public long delete(String id){
        return db.delete("remind_water", "remind_water_id=?", new String[]{id});
    }
}
