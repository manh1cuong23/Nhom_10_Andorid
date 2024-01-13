package com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhom4_chuongtrinh_ptudandroid.common.database.DbHelper;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model.ExercisePlan;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model.StatisticStatus;

import java.util.ArrayList;
import java.util.List;

public class ExercisePlanDAO {
    private static final String TABLE_EXERCISE_PLAN = "exercise_plan";
    private static final String COLUMN_ID = "exercise_plan_id";
    private static final String COLUMN_NAME = "exercise_name";
    private static final String COLUMN_TYPE = "exercise_type";
    private static final String COLUMN_START = "exercise_plan_start";
    private static final String COLUMN_FINISH = "exercise_plan_finish";
    private static final String COLUMN_PROGRESS = "progress";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_CREATED_DATE = "created_date";
    private static final String COLUMN_GOAL = "goal";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_USER_ID = "user_id";
    private DbHelper dbHelper;
    private int user_id;
    public ExercisePlanDAO(Context context){
        dbHelper = new DbHelper(context);
        UserSession userSession = UserSession.getInstance();
        user_id = userSession.getUserId();
    }
    public long addExercisePlan(ExercisePlan exercisePlan){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, exercisePlan.getName());
        values.put(COLUMN_TYPE, exercisePlan.getType());
        values.put(COLUMN_START, exercisePlan.getStartTime());
        values.put(COLUMN_FINISH, exercisePlan.getEndTime());
        values.put(COLUMN_PROGRESS, exercisePlan.getProgress());
        values.put(COLUMN_STATUS, exercisePlan.getStatus());
        values.put(COLUMN_CREATED_DATE, exercisePlan.getCreateDate());
        values.put(COLUMN_GOAL, exercisePlan.getGoal());
        values.put(COLUMN_SCORE, exercisePlan.getScore());
        values.put(COLUMN_USER_ID,this.user_id );
        long insertedId = db.insert(TABLE_EXERCISE_PLAN, null, values);
        db.close();
        return insertedId;
    }
    public List<ExercisePlan> getAllExercisePlan(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT exercise_plan_id, exercise_name, exercise_type, exercise_plan_start, exercise_plan_finish, progress," +
                "status, created_date,goal,score, user_id FROM exercise_plan WHERE user_id = ?";
        String[] selectionArgs = {String.valueOf(this.user_id)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        List<ExercisePlan> exercisePlans = new ArrayList<>();

        while (cursor.moveToNext()) {
            int exercisePlanId = cursor.getInt(0);
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            String start = cursor.getString(3);
            String finish = cursor.getString(4);
            int progress = cursor.getInt(5);
            String createDate = cursor.getString(7);
            String status = cursor.getString(6);
            int goal = cursor.getInt(8);
            int score = cursor.getInt(9);
            int user_id = cursor.getInt(10);
            exercisePlans.add(new ExercisePlan(exercisePlanId,name, type,start,finish,progress,status, createDate,goal,score, user_id));
        }
        cursor.close();
        return exercisePlans;
    }
    public ExercisePlan getExercisePlan(int exercisePlanId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT exercise_plan_id, exercise_name, exercise_type, exercise_plan_start, exercise_plan_finish, progress," +
                "status, created_date,goal,score, user_id FROM exercise_plan WHERE exercise_plan_id = ?";
        String[] selectionArgs = {String.valueOf(exercisePlanId)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        ExercisePlan exercisePlan = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            String start = cursor.getString(3);
            String finish = cursor.getString(4);
            int progress = cursor.getInt(5);
            String createDate = cursor.getString(6);
            String status = cursor.getString(7);
            int goal = cursor.getInt(8);
            int score = cursor.getInt(9);
            int user_id = cursor.getInt(10);
            exercisePlan = new ExercisePlan(id,
                    name,
                    type,
                    start,
                    finish,
                    progress,
                    status,
                    createDate,
                    goal,
                    score,
                    user_id);
            cursor.close();
        }
        return exercisePlan;
    }
    public boolean updateExercisePlan(ExercisePlan exercisePlan) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("exercise_name", exercisePlan.getName());
        values.put("exercise_type", exercisePlan.getType());
        values.put("exercise_plan_start", exercisePlan.getStartTime());
        values.put("exercise_plan_finish", exercisePlan.getEndTime());
        values.put("progress", exercisePlan.getProgress());
        values.put("status", exercisePlan.getStatus());
        values.put("goal", exercisePlan.getGoal());
        values.put("score", exercisePlan.getScore());
        String whereClause = "exercise_plan_id = ?";
        String[] whereArgs  = { String.valueOf(exercisePlan.getId()) };
        int rowsAffected = database.update("exercise_plan", values, whereClause, whereArgs);
        database.close();
        return rowsAffected > 0;
    }

    public void deleteExercisePlan(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_EXERCISE_PLAN, COLUMN_ID +  " = ?", new String[]{String.valueOf(id)});
    }
}
