package com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model;

public class Calories {
    int calories_id;
    float calories_intake;
    float calories_burned;
    String status;
    String created_date;
    int user_id;

    public Calories() {
    }

    public Calories(float calories_intake, float calories_burned,String status, String created_date) {
        this.calories_intake = calories_intake;
        this.calories_burned = calories_burned;
        this.status = status;
        this.created_date = created_date;

    }

    public int getCalories_id() {
        return calories_id;
    }

    public void setCalories_id(int calories_id) {
        this.calories_id = calories_id;
    }

    public float getCalories_intake() {
        return calories_intake;
    }

    public void setCalories_intake(float calories_intake) {
        this.calories_intake = calories_intake;
    }

    public float getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(float calories_burned) {
        this.calories_burned = calories_burned;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id  ) {
        this.user_id = user_id;
    }
}
