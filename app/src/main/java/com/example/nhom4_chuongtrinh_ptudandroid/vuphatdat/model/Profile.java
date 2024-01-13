package com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model;

public class Profile {
    private int id;
    private int userId;
    private String fullName;
    private String dob;
    private String sex;
    private String diseases;
    public Profile() {
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public Profile(int id,String fullName, String dob, String sex, String diseases, int userId) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.dob = dob;
        this.sex = sex;
        this.diseases = diseases;
    }
    public Profile( String fullName, String dob, String sex, String diseases,int userId) {
        this.fullName = fullName;
        this.dob = dob;
        this.sex = sex;
        this.diseases = diseases;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
