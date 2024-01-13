package com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhom4_chuongtrinh_ptudandroid.common.database.DbHelper;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model.StatisticStatus;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model.Calories;

import java.util.ArrayList;
import java.util.List;

public class CaloriesDAO {
    private SQLiteDatabase db;
    private int user_id;

    public CaloriesDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        UserSession userSession = UserSession.getInstance();
        user_id = userSession.getUserId();
    }

    public long insert(Calories obj) {
        ContentValues values = new ContentValues(); //Khởi tạo một ContentValues rỗng
        values.put("calories_intake", obj.getCalories_intake()); //Thêm thông tin lấy từ đối tượng
        values.put("calories_burned", obj.getCalories_burned());
        values.put("created_date", obj.getCreated_date());
        values.put("status", obj.getStatus());
        values.put("user_id", this.user_id);//Thêm user_id hiện đang đăng nhập
        return db.insert("calories", null, values); //Thêm vào CSDL
    }

    public long delete(String id) {
        return db.delete("calories", "calories_id=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<Calories> getData(String startDateString, String finishDateString) {
        List<Calories> list = new ArrayList();
        Cursor cursor = null;
        String query = "SELECT * FROM calories";
        query += " WHERE user_id = " + String.valueOf(this.user_id);
        if (startDateString != null && finishDateString != null) {
            query += " AND created_date BETWEEN ? AND ?";
            String[] selectionArgs = {startDateString, finishDateString};
            cursor = db.rawQuery(query, selectionArgs);
        } else {
            cursor = db.rawQuery(query, null);
        }
        while (cursor.moveToNext()) {
            Calories obj = new Calories();
            obj.setCalories_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("calories_id"))));
            obj.setCalories_intake(Float.parseFloat(cursor.getString(cursor.getColumnIndex("calories_intake"))));
            obj.setCalories_burned(Float.parseFloat(cursor.getString(cursor.getColumnIndex("calories_burned"))));
            obj.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            obj.setCreated_date(cursor.getString(cursor.getColumnIndex("created_date")));
            obj.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id"))));
            list.add(obj);
        }
        cursor.close();
        return list;
    }

    public List<Calories> getAll() {
        return getData(null, null);
    }
}


