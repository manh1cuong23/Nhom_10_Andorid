package com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.dao.QualitySleepDAO;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.model.QualitySleep;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.dao.ProfileDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model.Profile;

public class QualitySleepActivity extends AppCompatActivity {
    ImageButton  imgStart, imgFinish, imgDate;
    EditText edtStart, edtFinish;
    TextView tvDate;

    Button btnAdd,btnList;
    Calendar dateCal;
    Calendar timeCal;
    ProfileDAO profileDAO = null;
    QualitySleepDAO qualitySleepDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nct_activity_quality_sleep);
        mapping();
        getShowTimeStart();
        getShowTimeFinish();
        getShowDate();
        getProcessAdd();
        getShowListA();
    }
    public void mapping(){
        profileDAO = new ProfileDAO(this);
        imgStart = findViewById(R.id.imgStart);
        imgFinish = findViewById(R.id.imgFinish);
        imgDate = findViewById(R.id.imgDate);
        edtStart = findViewById(R.id.edtStart);
        edtFinish = findViewById(R.id.edtFinish);
        tvDate = findViewById(R.id.tvDate);
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);
        qualitySleepDAO = new QualitySleepDAO(this);
    }
    public void getShowDate(){
        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lấy ngày hiện tại
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                // Thiết lập giá trị mặc định cho tvDate
                String currentDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                tvDate.setText(currentDate);

                //Tạo DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(QualitySleepActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        //Xử lý khi người dùng chọn một ngày
                        dateCal = Calendar.getInstance();
                        dateCal.set(selectedYear,selectedMonth,selectedDay);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String selectedDate = dateFormat.format(dateCal.getTime());
                        tvDate.setText(selectedDate);
                    }
                },year,month,dayOfMonth);

                //Hiển thị Dialog
                datePickerDialog.show();
            }
        });
    }
    // Phương thức để lấy ngày hiện tại
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = new Date(System.currentTimeMillis());
        return dateFormat.format(currentDate);
    }
    public void getShowTimeStart(){
        imgStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lấy thời gian hiện tại
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Tạo TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(QualitySleepActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //Xử lý khi người dùng chọn giờ
                        timeCal = Calendar.getInstance();
                        timeCal.set(Calendar.HOUR_OF_DAY, selectedHour);
                        timeCal.set(Calendar.MINUTE, selectedMinute);
                        String selectedTime = selectedHour + ":" + selectedMinute;
                        edtStart.setText(selectedTime);

                    }
                },hour,minute,true);

                //Hiển thị TimePickerDialog
                timePickerDialog.show();
            }
        });
    }
    public void getShowTimeFinish() {
        imgFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lấy thời gian hiện tại
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Tạo TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(QualitySleepActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //Xử lý khi người dùng chọn giờ
                        timeCal = Calendar.getInstance();
                        timeCal.set(Calendar.HOUR_OF_DAY, selectedHour);
                        timeCal.set(Calendar.MINUTE, selectedMinute);
                        String selectedTime = selectedHour + ":" + selectedMinute;
                        edtFinish.setText(selectedTime);

                    }
                }, hour, minute, true);

                //Hiển thị TimePickerDialog
                timePickerDialog.show();
            }
        });
    }
    public void getProcessAdd(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ giao diện người dùng
                String startSleep = edtStart.getText().toString();
                String finishSleep = edtFinish.getText().toString();
                String status = "";
                String createdDate = tvDate.getText().toString();
                if (startSleep.isEmpty() && finishSleep.isEmpty()){
                    Toast.makeText(QualitySleepActivity.this,"Must enter startSleep and finishSleep ",Toast.LENGTH_SHORT).show();
                }
                // Kiểm tra nếu tvDate trống rỗng, gán giá trị là ngày hiện tại
                if (createdDate == null || createdDate.isEmpty()) {
                    createdDate = getCurrentDate();
                }

                Profile profile = profileDAO.getProfile();
                String dateOfBirth = profile.getDob();
                int age = calculateAge(dateOfBirth);
                float sleepDuration = calculateSleepDurationInHours(startSleep, finishSleep);
                if (sleepDuration != -1) {
                  // Tính toán trạng thái giấc ngủ
                    status = getStatusFromAgeGroup(sleepDuration);
                    // Tạo đối tượng QualitySleep
                    QualitySleep qualitySleep = new QualitySleep(startSleep,finishSleep,status,createdDate);

                    // Thêm đối tượng QualitySleep vào cơ sở dữ liệu
                    qualitySleepDAO.insert(qualitySleep);

                    // Xóa sạch các PlainText
                    edtStart.setText("");
                    edtFinish.setText("");
                    tvDate.setText("");

                    int minHours, maxHours;
                    if (age < 6) {
                        minHours = 10;
                        maxHours = 12;
                    } else if (age >= 6 && age <= 13) {
                        minHours = 9;
                        maxHours = 11;
                    } else if (age >= 14 && age <= 17) {
                        minHours = 8;
                        maxHours = 10;
                    } else if (age >= 18 && age <= 64) {
                        minHours = 7;
                        maxHours = 9;
                    } else {
                        minHours = 7;
                        maxHours = 8;
                    }

                    // Hiển thị dialog lời khuyên
                    showAdviceDialog(status, sleepDuration,minHours,maxHours);
                } else {
                    // Xử lý lỗi từ calculateSleepDurationInHours, ví dụ như hiển thị thông báo lỗi
                    Toast.makeText(QualitySleepActivity.this, "Invalid format time", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private float calculateSleepDurationInHours(String startSleep, String finishSleep) {
        try {
            // Định dạng để chuyển đổi chuỗi thành đối tượng Date
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

            // Chuyển đổi thời gian bắt đầu và kết thúc thành đối tượng Date
            Date startTime = format.parse(startSleep);
            Date finishTime = format.parse(finishSleep);

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
    private int calculateAge(String dateOfBirth) {
                    try {
                        if(dateOfBirth != null && !dateOfBirth.isEmpty()) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            Date birthDate = format.parse(dateOfBirth);

                            Calendar today = Calendar.getInstance();
                            Calendar birthCalendar = Calendar.getInstance();
                            birthCalendar.setTime(birthDate);

                            int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

                            // Kiểm tra xem ngày sinh trong năm nay đã qua hay chưa
                            if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                }
                return age;
            }
            else {
                return -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
    private String getStatusFromAgeGroup(float sleepDuration) {
        String ageGroup = profileDAO.getAgeGroup();
        if (ageGroup.equals("Children")) {
            return evaluateSleepStatusForYoungChildren(sleepDuration);
        } else if (ageGroup.equals("Youth")) {
            return evaluateSleepStatusForChildren(sleepDuration);
        } else if (ageGroup.equals("Teenager")) {
            return evaluateSleepStatusForTeens(sleepDuration);
        } else if (ageGroup.equals("Adolescent")||ageGroup.equals("Middle-age")) {
            return evaluateSleepStatusForAdults(sleepDuration);
        } else if (ageGroup.equals("Old")) {
            return evaluateSleepStatusForSeniors(sleepDuration);
        }
        return "";
    }
    private String evaluateSleepStatus(float sleepDuration, int minHours, int maxHours) {
        if (sleepDuration >= minHours && sleepDuration <= maxHours) {
            return "Enough" ;
        } else if (sleepDuration > maxHours) {
            return "Sleep too much" ;
        } else {
            return "Sleepless";
        }
    }
    private String evaluateSleepStatusForYoungChildren(float sleepDuration){
        return evaluateSleepStatus(sleepDuration, 10, 12);
    }
    private String evaluateSleepStatusForChildren(float sleepDuration){
        return evaluateSleepStatus(sleepDuration, 9, 11);
    }
    private String evaluateSleepStatusForTeens(float sleepDuration){
        return evaluateSleepStatus(sleepDuration, 8, 10);
    }
    private String evaluateSleepStatusForAdults(float sleepDuration){
        return evaluateSleepStatus(sleepDuration, 7, 9);
    }
    private String evaluateSleepStatusForSeniors(float sleepDuration){
        return evaluateSleepStatus(sleepDuration, 7, 8);
    }

    public void getShowListA(){
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QualitySleepActivity.this, QualitySleepListActivity.class);
                startActivity(intent);
            }
        });
    }
    public void back(View v){
        finish();
    }
    private void showAdviceDialog(String status, float sleepDuration, int minHours, int maxHours) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sleep Advice");

        if (status.equals("Sleep too much")) {
            builder.setMessage("You sleep too much.Need to reduce sleep time " + calHours(sleepDuration - maxHours) + " hours to good for health.");
        } else if (status.equals("Sleepless")) {
            builder.setMessage("You sleepless. Need to increase sleep time " + calHours(minHours - sleepDuration) + " hours to good for health.");
        } else {
            return;
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
    private String calHours(float time){
        int hour = (int) time;
        int minute = Math.round((time - hour) * 60);
        return hour + " hours "+ minute+" minutes";
    }
}