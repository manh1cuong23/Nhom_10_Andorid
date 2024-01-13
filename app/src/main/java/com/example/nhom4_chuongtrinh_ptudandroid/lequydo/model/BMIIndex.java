package com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model;

public class BMIIndex {
    private int user_id;
    private int bmi_id;
    private float height;
    private float weight;
    private String status;
    private String created_date;

    public BMIIndex(float height, float weight, String status, String created_date) {
        this.height = height;
        this.weight = weight;
        this.status = status;
        this.created_date = created_date;
    }

    public BMIIndex() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBmi_id() {
        return bmi_id;
    }

    public void setBmi_id(int bmi_id) {
        this.bmi_id = bmi_id;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status){
        this.status =status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
    public float getResult(){
        return this.weight/(this.height*2);
    }


    @Override
    public String toString() {
        return "Height=" + height +
                ", weight=" + weight +
                ", status='" + status + '\'' +
                ", created_date='" + created_date;
    }
}
