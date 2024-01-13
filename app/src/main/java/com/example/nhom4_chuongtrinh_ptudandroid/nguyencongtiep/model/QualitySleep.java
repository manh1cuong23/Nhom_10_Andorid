package com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class QualitySleep {
    int sleep_id;
    String start_sleep;
    String finish_sleep;
    String status;
    String crated_date;
    int user_id;
    public QualitySleep(){

    }

    public QualitySleep(String start_sleep, String finish_sleep, String status, String crated_date) {
        this.start_sleep = start_sleep;
        this.finish_sleep = finish_sleep;
        this.status = status;
        this.crated_date = crated_date;
    }

    public int getSleep_id() {
        return sleep_id;
    }

    public void setSleep_id(int sleep_id) {
        this.sleep_id = sleep_id;
    }

    public String getStart_sleep() {
        return start_sleep;
    }

    public void setStart_sleep(String start_sleep) {
        this.start_sleep = start_sleep;
    }

    public String getFinish_sleep() {
        return finish_sleep;
    }

    public void setFinish_sleep(String finish_sleep) {
        this.finish_sleep = finish_sleep;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCrated_date() {
        return crated_date;
    }

    public void setCrated_date(String crated_date) {
        this.crated_date = crated_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public float getDuration() {
        try {
            // Định dạng để chuyển đổi chuỗi thành đối tượng Date
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

            // Chuyển đổi thời gian bắt đầu và kết thúc thành đối tượng Date
            Date startTime = format.parse(this.start_sleep);
            Date finishTime = format.parse(this.finish_sleep);

            // Nếu thời gian kết thúc trước thời gian bắt đầu, thêm 1 ngày đầy đủ vào thời gian kết thúc
            if (finishTime.before(startTime)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(finishTime);
                calendar.add(Calendar.DATE, 1);
                finishTime = calendar.getTime();
            }

            // Tính toán thời lượng giấc ngủ (đơn vị: giờ)
            long durationInMillis = finishTime.getTime() - startTime.getTime();
            float durationInHours = durationInMillis / (60 * 60 * 1000f);

            return durationInHours;

        } catch (ParseException e) {
            // Xử lý ngoại lệ nếu có lỗi khi chuyển đổi thời gian
            e.printStackTrace();
            return -1; // hoặc có thể trả về giá trị khác để biểu thị lỗi
        }
    }


}
