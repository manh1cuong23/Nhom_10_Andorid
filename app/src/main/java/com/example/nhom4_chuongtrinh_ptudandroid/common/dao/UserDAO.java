package com.example.nhom4_chuongtrinh_ptudandroid.common.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhom4_chuongtrinh_ptudandroid.common.database.DbHelper;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.User;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model.Calories;

public class UserDAO {
        private SQLiteDatabase db;
        public UserDAO(Context context){
            DbHelper dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
        }
        public long insert(User obj){
            ContentValues values = new ContentValues();
            values.put("user_name",obj.getUser_name());
            values.put("email",obj.getEmail());
            values.put("user_password",obj.getUser_password());
            return db.insert("user", null, values);
        }
        public boolean checkUser(String username, String password){
            String sqlQuery = "SELECT * FROM user WHERE user_name = ? AND user_password = ?";
            String[] selectionArgs = {username,password};
            Cursor cursor = db.rawQuery(sqlQuery,selectionArgs);
            if (cursor.getCount() != 0){
                return true;
            } else {
                return false;
            }
        }
        @SuppressLint("Range")
        public int getUserId(String username, String password){
            String sqlQuery = "SELECT user_id FROM user WHERE user_name = ? AND user_password = ?";
            String[] selectionArgs = {username,password};
            Cursor cursor = db.rawQuery(sqlQuery,selectionArgs);
            int userId = 0;
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex("user_id"));
            }
            cursor.close();
            return userId;
        }
        public boolean checkExistUser(String username){
            String sqlQuery = "SELECT * FROM user WHERE user_name = ?";
            String[] selectionArgs = {username};
            Cursor cursor = db.rawQuery(sqlQuery,selectionArgs );
            if (cursor.getCount() != 0){
                return true;
            } else {
                return false;
            }
        }
        public boolean checkExistEmail(String email){
            String sqlQuery = "SELECT * FROM user WHERE email = ?";
            String[] selectionArgs = {email};
            Cursor cursor = db.rawQuery(sqlQuery,selectionArgs );
            if (cursor.getCount() != 0){
                return true;
            } else {
                return false;
            }
        }

        @SuppressLint("Range")
        public String changePassword(String email, String oldPassword, String newPassword){
            UserSession userSession = UserSession.getInstance();
            int user_id = userSession.getUserId();
            String sqlQuery = "SELECT email, user_password FROM user WHERE user_id = ?";
            String[] selectionArgs = {String.valueOf(user_id)};
            Cursor cursor = db.rawQuery(sqlQuery,selectionArgs);
            String dbPassword, dbEmail;
            dbPassword ="";
            dbEmail = "";
            try{
                if (cursor.moveToFirst()) {
                    dbEmail = cursor.getString(cursor.getColumnIndex("email"));
                    dbPassword = cursor.getString(cursor.getColumnIndex("user_password"));
                }
                cursor.close();
                if(!dbPassword.equals(oldPassword)){
                    return "Old password is incorrect";
                } else if (!dbEmail.equals(email)) {
                    return "Email is incorrect";
                }
                else{
                    String tableName = "user";
                    ContentValues values = new ContentValues();
                    values.put("user_password", newPassword);
                    String whereClause = "user_id = ?";
                    String[] whereArgs = { String.valueOf(user_id) };
                    db.update(tableName, values, whereClause, whereArgs);
                    return "Success";
                }

            }
            catch (Exception e){
                e.printStackTrace();
                return "Error";

            }
        }
}

