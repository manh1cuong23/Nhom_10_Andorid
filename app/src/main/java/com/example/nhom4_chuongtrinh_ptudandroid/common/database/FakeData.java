package com.example.nhom4_chuongtrinh_ptudandroid.common.database;

public class FakeData {
    public static final String INSERT_USER = "insert into user(user_name, email,user_password) values" +
            "('Admin123','admin1@gmail.com','Password@gmail.com')";
    public static final String INSERT_PROFILE = "insert into profile(profile_id, user_id,full_name,date_of_birth,sex,diseases) values" +
            "('1','1','Vu Tung Tam','2003-12-26','Male','High blood pressure,Diabetes')";
    public static final String INSERT_HEART_HEALTH = "insert into heart_health(heartbeat, heart_pressure,status,created_date,user_id) values" +
            "(74.0, 118.0, 'Normal', '2023-12-16', 1)," +
            "(71.5, 121.0, 'Normal', '2023-12-17', 1)," +
            "(83.0, 128.0, 'High', '2023-12-18', 1)," +
            "(78.5, 119.0, 'Normal', '2023-12-19', 1)," +
            "(80.0, 123.0, 'Normal', '2023-12-20', 1),"+
            "(75.5,120.0,'Normal','2023-12-23',1)," +
            "(65.5,110.0,'Low','2023-12-24',1)," +
            "(85.5,135.0,'High','2023-12-25',1)," +
            "(75.5,125.0,'Normal','2023-12-26',1)," +
            "(55.5,115.0,'Low','2023-12-27',1)";
    public static final String INSERT_CALORIES = "INSERT INTO calories (calories_intake, calories_burned, status, created_date, user_id) VALUES " +
            "(1900, 1500, 'Enough', '2023-12-15', 1), " +
            "(1650, 1350, 'Too few', '2023-12-16', 1), " +
            "(2050, 1700, 'Too much', '2023-12-18', 1), " +
            "(1900, 1600, 'Enough', '2023-12-19', 1), " +
            "(1600, 1230, 'Too much', '2023-12-20', 1), " +
            "(1200, 1100, 'Enough', '2023-12-24', 1), " +
            "(2000, 1500, 'Too much', '2023-12-25', 1), " +
            "(1805, 1500, 'Too few', '2023-12-26', 1), " +
            "(1900, 1700, 'Enough', '2023-12-23', 1)";
    public static final String INSERT_QUALITY_SLEEP = "insert into quality_sleep(start_sleep,finish_sleep,status,created_date,user_id) values" +
            "('23:00','05:00','Sleepless','2023-12-01', 1)," +
            "('00:00','05:30','Sleepless','2023-12-02', 1)," +
            "('23:30','06:00','Sleepless','2023-12-03', 1)," +
            "('00:00','07:30','Enough','2023-12-04', 1)," +
            "('00:30','08:00','Enough','2023-12-05', 1)," +
            "('22:45','09:30','Sleep too much','2023-12-06', 1)," +
            "('01:00','11:15','Sleep too much','2023-12-07', 1)," +
            "('23:15','10:30','Sleep too much','2023-12-08', 1)" ;
    public static final String INSERT_EXERCISE_PLAN = "INSERT INTO exercise_plan (exercise_name, exercise_type, exercise_plan_start, exercise_plan_finish, progress, status,score,goal, created_date, user_id)" +
            "VALUES" +
            "    ('Squats', 'gym', '2023-01-20', '2023-01-28', 100, 'Complete',100,100, '2023-01-18', 1)," +
            "    ('Tadasana', 'Yoga', '2023-11-25', '2024-02-01', 60, 'Incomplete',60,100, '2023-01-23', 1)," +
            "    ('Lunges', 'gym', '2023-02-03', '2023-02-09', 50, 'Incomplete',50,100, '2023-02-01', 1)," +
            "    ('Pushups', 'gym', '2023-12-10', '2024-02-18', 100, 'Complete',100,100, '2023-02-08', 1)," +
            "    ('Vrikshasana', 'Yoga', '2023-02-15', '2023-02-22', 10, 'Incomplete',10,100, '2023-02-13', 1)," +
            "    ('Pullups', 'gym', '2023-02-25', '2023-03-03', 80, 'Incomplete',80,100, '2023-02-23', 1)," +
            "	('Plank', 'gym', '2023-12-29', '2024-01-10', 50, 'Incomplete',50,100 ,'2023-12-27', 1)," +
            "    ('Balasana', 'Yoga', '2023-12-28', '2023-12-30', 30, 'Incomplete',30,100, '2023-12-27', 1)," +
            "    ('Deadlifts', 'gym', '2023-12-30', '2024-01-02', 100, 'Complete',100,100, '2023-12-28', 1)," +
            "    ('Crunches', 'gym', '2024-01-01', '2024-01-20', 20, 'Incomplete',20,100, '2023-12-28', 1);" ;
    public static final String INSERT_BMI_INDEX = "insert into bmi_index (height, weight,status,created_date,user_id) values" +
            "(1.7, 98, 'Fat', '2023-06-1', 1)," +
            "(1.7, 96, 'Fat', '2023-07-18', 1),"+
            "(1.7, 92, 'Overweight', '2023-08-15', 1),"+
            "(1.7, 85, 'Normal', '2023-09-30', 1)," +
            "(1.71, 76, 'Normal', '2023-10-29', 1)," +
            "(1.7, 67, 'Normal', '2023-11-24', 1)," +
            "(1.7, 62.5, 'Normal', '2023-12-20', 1),"+
            "(1.7, 62.2, 'Normal', '2023-12-22', 1)," +
            "(1.7, 62, 'Normal', '2023-12-25', 1)," +
            "(1.7, 60.5, 'Normal', '2023-12-27', 1);" ;
}
