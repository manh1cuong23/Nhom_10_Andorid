package com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nhom4_chuongtrinh_ptudandroid.common.database.DbHelper;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.dao.BMIIndexDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.dao.HeartHealthDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model.BMIIndex;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model.HeartHealth;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.dao.QualitySleepDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.model.QualitySleep;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model.Calories;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model.StatisticStatus;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;

import java.util.ArrayList;
import java.util.List;

public class StatisticDAO {
    private SQLiteDatabase db;
    private int user_id;
    private HeartHealthDAO heartHealthDAO;
    private BMIIndexDAO bmiIndexDAO;
    private CaloriesDAO caloriesDAO;
    private QualitySleepDAO qualitySleepDAO;
    private DbHelper dbHelper;
    public StatisticDAO(Context context) {
        dbHelper = new DbHelper(context);
        heartHealthDAO = new HeartHealthDAO(context);
        bmiIndexDAO = new BMIIndexDAO(context);
        caloriesDAO = new CaloriesDAO(context);
        qualitySleepDAO = new QualitySleepDAO(context);

        UserSession userSession = UserSession.getInstance();
        user_id = userSession.getUserId();
    }
    @SuppressLint("Range")
    public List<HeartHealth> getHeartHealthData(String startDateString, String finishDateString){
        return this.heartHealthDAO.getData(startDateString,finishDateString);
    }
    public List<Calories> getCaloriesData(String startDateString, String finishDateString){
        return this.caloriesDAO.getData(startDateString,finishDateString);
    }
    public List<BMIIndex> getBMIData(String startDateString, String finishDateString){
        return this.bmiIndexDAO.getData(startDateString,finishDateString);
    }
    public List<QualitySleep> getQualitySleepData(String startDateString, String finishDateString){
        return this.qualitySleepDAO.getData(startDateString,finishDateString);
    }
    public ArrayList<StatisticStatus> getPercentStatus(String tableName, String startDateString, String finishDateString){
        db = dbHelper.getWritableDatabase();
        ArrayList<StatisticStatus> result = new ArrayList<>();
        Cursor cursor = null;
        try {
            String[] selectionArgs = {startDateString, finishDateString};
            String query = "SELECT status, COUNT(*) AS count, (COUNT(*) * 100.0 / (SELECT COUNT(*) FROM " + tableName + " WHERE user_id = " + this.user_id + ")) AS percentage " +
                    "FROM " + tableName + " " +
                    "WHERE user_id = " + this.user_id;
            if (startDateString != null && finishDateString != null) {
                query += " AND created_date BETWEEN ? AND ?";
            }
            query += " GROUP BY status";
            if(startDateString!=null && finishDateString!=null){
                cursor = db.rawQuery(query, selectionArgs);
            }
            else{
                cursor = db.rawQuery(query, null);
            }
            // Duyệt qua dữ liệu từ Cursor
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
                @SuppressLint("Range") float percentage = cursor.getFloat(cursor.getColumnIndex("percentage"));
                StatisticStatus newSS = new StatisticStatus(status,percentage);
                result.add(newSS);
            }
        } catch (Exception e) {
            Log.e("PieChart", e+"");
        } finally {
            // Đảm bảo đóng cursor và SQLiteDatabase
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return result;
    }
    public ArrayList<StatisticStatus> getHeartHealthPercentStatus(String startDateString, String finishDateString){
        return getPercentStatus("heart_health", startDateString, finishDateString);
    }
    public ArrayList<StatisticStatus> getQualitySleepPercentStatus(String startDateString, String finishDateString){
        return getPercentStatus("quality_sleep", startDateString, finishDateString);
    }
    public ArrayList<StatisticStatus> getBMIPercentStatus(String startDateString, String finishDateString){
        return getPercentStatus("bmi_index", startDateString, finishDateString);
    }
    public ArrayList<StatisticStatus> getCaloriesPercentStatus(String startDateString, String finishDateString){
        return getPercentStatus("calories", startDateString, finishDateString);
    }
    public ArrayList<StatisticStatus> getExercisePercentStatus(String startDateString, String finishDateString){
        return getPercentStatus("exercise_plan", startDateString, finishDateString);
    }
}
