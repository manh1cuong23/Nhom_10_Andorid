package com.example.nhom4_chuongtrinh_ptudandroid.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "group4.db";
    private static final int DB_VERSION = 2;

    static final String CREATE_TABLE_USER =
            "CREATE TABLE user (" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "email TEXT NOT NULL, " +
                    "user_name TEXT NOT NULL, " +
                    "user_password TEXT NOT NULL)";
    //
    static final String CREATE_TABLE_HEART_HEALTH =
            "CREATE TABLE heart_health (" +
                    "   heart_health_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   heartbeat FLOAT NOT NULL, " +
                    "   heart_pressure FLOAT NOT NULL,"+
                    "   status          TEXT NOT NULL," +
                    "   created_date    TEXT NOT NULL," +
                    "   user_id INTEGER REFERENCES user(user_id)" +
                    ");";
    static final String CREATE_TABLE_CALORIES =
            "CREATE TABLE calories (" +
                    "   calories_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   calories_intake FLOAT NOT NULL, " +
                    "   calories_burned FLOAT NOT NULL,"+
                    "   status          TEXT NOT NULL," +
                    "   created_date    TEXT NOT NULL," +
                    "   user_id INTEGER REFERENCES user(user_id)" +
                    ");";
    //
    static final String CREATE_TABLE_BMI_INDEX =
            "CREATE TABLE bmi_index (" +
                    "   bmi_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   height FLOAT NOT NULL, " +
                    "   weight FLOAT NOT NULL,"+
                    "   status          TEXT NOT NULL," +
                    "   created_date    TEXT NOT NULL," +
                    "   user_id INTEGER REFERENCES user(user_id)" +
                    ");";
    static final String CREATE_TABLE_QUALITY_SLEEP =
            "CREATE TABLE quality_sleep (" +
                    "   sleep_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   start_sleep TEXT NOT NULL, " +
                    "   finish_sleep TEXT NOT NULL,"+
                    "   status          TEXT NOT NULL," +
                    "   created_date    TEXT NOT NULL," +
                    "   user_id INTEGER REFERENCES user(user_id)" +
                    ");";
    static final String CREATE_TABLE_REMIND_WATER =
            "CREATE TABLE remind_water (" +
                    "   remind_water_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   amount FLOAT NOT NULL, " +
                    "   frequency FLOAT NOT NULL,"+
                    "   created_time TEXT NOT NULL,"+
                    "   created_date TEXT NOT NULL,"+
                    "   user_id INTEGER REFERENCES user(user_id)" +
                    ");";
    static final String CREATE_TABLE_PROFILE = "CREATE TABLE profile (" +
            "   profile_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "   user_id INTEGER REFERENCES user(user_id)," +
            "   full_name         TEXT NOT NULL," +
            "   date_of_birth    TEXT NOT NULL," +
            "   sex         INT NOT NULL," +
            "   diseases TEXT NOT NULL" +
            ");";
    static final String CREATE_TABLE_EXERCISE_PLAN =  "CREATE TABLE exercise_plan (" +
            "   exercise_plan_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "   exercise_name         TEXT NOT NULL," +
            "   exercise_type         TEXT NOT NULL," +
            "   exercise_plan_start         TEXT NOT NULL," +
            "   exercise_plan_finish        TEXT NOT NULL," +
            "   progress    INTEGER NOT NULL," +
            "   status         TEXT NOT NULL," +
            "   score INTEGER NOT NULL," +
            "   created_date         TEXT NOT NULL," +
            "   goal  INTERGER NOT NULL," +
            "   user_id INTEGER REFERENCES user(user_id)" +
            ");";



    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PROFILE);
        db.execSQL(CREATE_TABLE_HEART_HEALTH);
        db.execSQL(CREATE_TABLE_CALORIES);
        db.execSQL(CREATE_TABLE_BMI_INDEX);
        db.execSQL(CREATE_TABLE_QUALITY_SLEEP);
        db.execSQL(CREATE_TABLE_EXERCISE_PLAN);
        db.execSQL(CREATE_TABLE_REMIND_WATER);
        //Insert data
        db.execSQL(FakeData.INSERT_USER);
        db.execSQL(FakeData.INSERT_HEART_HEALTH);
        db.execSQL(FakeData.INSERT_CALORIES);
        db.execSQL(FakeData.INSERT_QUALITY_SLEEP);
        db.execSQL(FakeData.INSERT_EXERCISE_PLAN);
        db.execSQL(FakeData.INSERT_BMI_INDEX);
        db.execSQL(FakeData.INSERT_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTableUser = "drop table if exists user";
        db.execSQL(dropTableUser);
        String dropTableProfile = "drop table if exists proflie";
        db.execSQL(dropTableProfile);
        String dropTableHeartHealth = "drop table if exists hearth_health";
        db.execSQL(dropTableHeartHealth);
        String dropTableBMIIndex = "drop table if exists bmi_index";
        db.execSQL(dropTableBMIIndex);
        String dropTableQualitySleep = "drop table if exists quality_sleep";
        db.execSQL(dropTableQualitySleep);
        String dropTableExercisePlan = "drop table if exists exercise_plan";
        db.execSQL(dropTableExercisePlan);
        String dropTableRemindWater = "drop table if exists remind_water";
        db.execSQL(dropTableRemindWater);

        onCreate(db);
    }


}
