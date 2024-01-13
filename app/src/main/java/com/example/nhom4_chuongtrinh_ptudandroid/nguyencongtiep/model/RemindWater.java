package com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.model;

public class RemindWater {

    int remind_water_id;

    float amount;
    float frequency;
    String created_time;
    String created_date;
    int user_id;

    public RemindWater() {

    }

    public RemindWater( float amount, float frequency, String created_time,String created_date, int user_id) {
        this.amount = amount;
        this.frequency = frequency;
        this.created_time = created_time;
        this.created_date = created_date;
        this.user_id = user_id;
    }

    public int getRemind_water_id() {
        return remind_water_id;
    }

    public void setRemind_water_id(int remind_water_id) {
        this.remind_water_id = remind_water_id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
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

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}