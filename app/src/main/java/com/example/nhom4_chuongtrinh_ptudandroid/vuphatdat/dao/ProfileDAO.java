package com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.nhom4_chuongtrinh_ptudandroid.common.database.DbHelper;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model.Profile;

public class ProfileDAO {
    private static final String TABLE_PROFILES = "profile";

    private static final String COLUMN_NAME = "full_name";
    private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    private static final String COLUMN_GENDER = "sex";
    private static final String COLUMN_DISEASES = "diseases";
    private static final String COLUMN_USER_ID = "user_id";
    private DbHelper dbHelper;
    private int userId;
    public ProfileDAO(Context context){
        dbHelper = new DbHelper(context);
        UserSession userSession = UserSession.getInstance();
        this.userId = userSession.getUserId();
    }
    public long addProfile(Profile profile){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, profile.getFullName());
        values.put(COLUMN_DATE_OF_BIRTH, profile.getDob());
        values.put(COLUMN_DISEASES, profile.getDiseases());
        values.put(COLUMN_GENDER, profile.getSex());
        values.put(COLUMN_USER_ID,profile.getUserId() );
        long insertedId = db.insert(TABLE_PROFILES, null, values);
        db.close();
        return insertedId;
    }

    public Profile getProfile(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT profile_id, full_name, date_of_birth, sex, diseases, user_id FROM Profile WHERE user_id = ?";
        String[] selectionArgs = {String.valueOf(this.userId)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        Profile profile = null;
        if (cursor.moveToFirst()) {
            int profile_id = cursor.getInt(0);
            String full_name = cursor.getString(1);
            String date_of_birth = cursor.getString(2);
            String sex = cursor.getString(3);
            String diseases = cursor.getString(4);
            int user_id = cursor.getInt(5);
            profile = new Profile(profile_id,full_name, date_of_birth,sex,diseases, user_id);
        }
        cursor.close();
        return profile;
    }
    public boolean checkProfile(int userId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT profile_id, full_name, date_of_birth, sex, diseases, user_id FROM Profile WHERE user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        Profile profile = null;
        if (cursor.moveToFirst()) {
            int profile_id = cursor.getInt(0);
            String full_name = cursor.getString(1);
            String date_of_birth = cursor.getString(2);
            String sex = cursor.getString(3);
            String diseases = cursor.getString(4);
            int user_id = cursor.getInt(5);
            profile = new Profile(profile_id,full_name, date_of_birth,sex,diseases, user_id);
        }
        cursor.close();
        if(profile==null){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean updateProfile(Profile profile) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("full_name", profile.getFullName());
        values.put("date_of_birth", profile.getDob());
        values.put("sex", profile.getSex());
        values.put("diseases", profile.getDiseases());
        String whereClause = "profile_id = ?";
        String[] whereArgs  = { String.valueOf(profile.getId()) };
        int rowsAffected = database.update("profile", values, whereClause, whereArgs);
        database.close();
        return rowsAffected > 0;
    }
    @SuppressLint("Range")
    public ArrayList<String> getDisease() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String disease = "";
        String query = "SELECT diseases FROM profile ";
        query += " WHERE user_id = " + this.userId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            disease = cursor.getString(cursor.getColumnIndex("diseases"));
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
        if (disease == null) {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
        ArrayList<String> result = new ArrayList<>(Arrays.asList(disease.split(",")));
        cursor.close();
        return result;
    }
    @SuppressLint("Range")
    public int getAge() {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String dateOfBirth = "";
            String query = "SELECT date_of_birth FROM profile ";
            query += " WHERE user_id = " + this.userId;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                dateOfBirth = cursor.getString(cursor.getColumnIndex("date_of_birth"));
            } else {
                if (cursor != null) {
                    cursor.close();
                }

            }
            if (dateOfBirth == null) {
                if (cursor != null) {
                    cursor.close();
                }

            }
            if(dateOfBirth != null && !dateOfBirth.isEmpty()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date birthDate = format.parse(dateOfBirth);
                Calendar today = Calendar.getInstance();
                Calendar birthCalendar = Calendar.getInstance();
                birthCalendar.setTime(birthDate);
                int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
                // Kiểm tra xem ngày sinh trong năm nay đã qua hay chưa
                if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                }
                return age;
            }
            else {
                return -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public String getAgeGroup(){
        int age = getAge();
        if (age < 6) {
            return "Children";
        } else if (age >= 6 && age <=13) {
            return "Youth";
        } else if (age >= 14 && age <= 17) {
            return "Teenager";
        }else if (age >= 18 && age <= 40) {
            return "Adolescent";
        }else if (age > 40 && age <= 64) {
            return "Middle-age";
        } else {
            return "Old";
        }
    }
}
