package com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model;

public class ExercisePlan {
    private int id;
    private int userId;
    private String name;
    private String type;
    private String startTime;
    private String endTime;
    private int progress;
    private String status;
    private int goal;
    private String createDate;
    private int score;
    public ExercisePlan() {
    }

    public ExercisePlan(String name, String type, String startTime, String endTime, int progress, String status, String createDate,int goal,int score) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.progress = progress;
        this.status = status;
        this.goal = goal;
        this.createDate = createDate;
        this.score = score;
    }

    public ExercisePlan(int id, String name, String type, String startTime, String endTime, int progress, String status, String createDate, int goal,int score, int userId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.progress = progress;
        this.status = status;
        this.createDate = createDate;
        this.goal = goal;
        this.score  = score;
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
