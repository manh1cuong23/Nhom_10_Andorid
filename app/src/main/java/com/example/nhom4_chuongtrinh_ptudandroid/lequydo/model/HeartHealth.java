package com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model;

public class HeartHealth {
    private int user_id;
    private int heart_health_id;
    private float heartbeat;
    private float heart_pressure;
    private String status;
    private String created_date;

    public HeartHealth() {
    }

    public HeartHealth(float heartbeat, float heart_pressure, String status, String created_date) {
        this.heartbeat = heartbeat;
        this.heart_pressure = heart_pressure;
        this.status = status;
        this.created_date = created_date;
    }

    public int getHeart_health_id() {
        return heart_health_id;
    }

    public void setHeart_health_id(int heart_health_id) {
        this.heart_health_id = heart_health_id;
    }

    public float getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(float heartbeat) {
        this.heartbeat = heartbeat;
    }

    public float getHeart_pressure() {
        return heart_pressure;
    }

    public void setHeart_pressure(float heart_pressure) {
        this.heart_pressure = heart_pressure;
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

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return  "Heartbeat=" + heartbeat +
                ". Heart pressure=" + heart_pressure +
                ". Heart status:'" + status +
                ". Created date:'" + created_date;
    }
}
