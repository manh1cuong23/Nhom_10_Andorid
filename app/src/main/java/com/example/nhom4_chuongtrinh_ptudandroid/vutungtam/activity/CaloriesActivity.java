package com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.activity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom4_chuongtrinh_ptudandroid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.dao.ProfileDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model.Profile;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.dao.CaloriesDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model.Calories;

public class CaloriesActivity extends AppCompatActivity {
    ImageButton imgBtnDate;
    EditText edtIntake, edtBurned;
    TextView txtDate;
    Button btnAdd,btnList;
    CaloriesDAO dbHandler;
    ProfileDAO profileDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vtt_activity_calories);
        mapping();
    }
    public void mapping(){
        profileDAO = new ProfileDAO(this);
        dbHandler = new CaloriesDAO(this);
        imgBtnDate = findViewById(R.id.imgDate);

        edtIntake = findViewById(R.id.edtIntake);
        edtBurned = findViewById(R.id.edtBurned);
        txtDate = findViewById(R.id.txtDate);
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);

        imgBtnDate.setOnClickListener(new doSomeThing());
        btnAdd.setOnClickListener(new doSomeThing());
        btnList.setOnClickListener(new doSomeThing());
    }
    public void addCalories(){
        float intake = Float.parseFloat(edtIntake.getText().toString());
        float burned = Float.parseFloat(edtBurned.getText().toString());
        String date = txtDate.getText().toString();
        if(date.equalsIgnoreCase("Now")){
            date = getCurrentDateAsString();
        }
        String status = "";
        if (valid(intake,burned)){
            ArrayList<String> arrDiseases = new ArrayList<>();
            arrDiseases.addAll(profileDAO.getDisease());
            status = getStatusFromAgeGroup(intake,arrDiseases);
            Calories calories = new Calories(intake,burned,status,date);
            dbHandler.insert(calories);
            edtIntake.setText("");
            edtBurned.setText("");
            txtDate.setText("Now");
            showAdviceDialog(status,intake,burned);
        }
        else{
            Toast.makeText(this,"Inappropriate data",Toast.LENGTH_LONG).show();
        }
    }
    public boolean valid(float intake, float burned){
        if(intake<1000 || burned<1000){
            return false;
        }
        else{
            return true;
        }
    }
    private String getStatusFromAgeGroup(float intake, ArrayList<String> arrDiseases) {
        String priorityDisease = "No underlying diseases";
        String[] priority  = {"Mental illness","Fatigue","High blood pressure","Diabetes"};
        for(String p : priority){
            for (String d : arrDiseases){
                if(d.equalsIgnoreCase(p)){
                    priorityDisease = p;
                    break;
                }
            }
        }
        String ageGroup = profileDAO.getAgeGroup();
        Profile profile = profileDAO.getProfile();
        String gender = profile.getSex();
        if (ageGroup.equals("Children")) {
            return evaluateCaloriesStatusForYoungChildren(intake,gender);
        } else if (ageGroup.equals("Youth")) {
            return evaluateCaloriesStatusForChildren(intake,gender);
        } else if (ageGroup.equals("Teenager")) {
            return evaluateCaloriesStatusForTeens(intake,gender);
        } else if (ageGroup.equals("Adolescent")||ageGroup.equals("Middle-age")) {
            return evaluateCaloriesStatusForAdults(intake,gender,priorityDisease);
        } else if (ageGroup.equals("Old")) {
            return evaluateCaloriesStatusForSeniors(intake,gender,priorityDisease);
        }
        return "";
    }
    private String evaluateCaloriesStatus(float intake, float minNeed, float maxNeed) {
        if (intake >= minNeed && intake <= maxNeed) {
            return "Balance" ;
        } else if (intake > maxNeed) {
            return "Too much" ;
        } else {
            return "Too few";
        }
    }
    private String recommendation(String status, float intake, float burned){
        float difference = intake - burned;
        String recommend = "Today your calorie intake is "+status;
        if(difference>500){
            recommend+=" and your calorie burned is too low. Please exercise more to avoid the risk of obesity. ";
        }
        else if(difference<250){
            recommend+=" and your calorie burned is too high." +
                    " Pay attention to the intensity of your exercise. You need to add "+(250-difference)+" cal now.";
        }
        else{
            recommend+=" and your calorie difference is balance. Try to maintain healthy living habits. ";
        }
        return recommend;
    }
    private String evaluateCaloriesStatusForYoungChildren(float intake, String gender){
        if(gender.equals("Male")){
            return evaluateCaloriesStatus(intake, 1200, 1600);
        }
        else{
            return evaluateCaloriesStatus(intake, 1000, 1400);
        }

    }
    private String evaluateCaloriesStatusForChildren(float intake, String gender){
        if(gender.equals("Male")){
            return evaluateCaloriesStatus(intake, 1600, 2200);
        }
        else{
            return evaluateCaloriesStatus(intake,  1400, 2000);
        }
    }
    private String evaluateCaloriesStatusForTeens(float intake, String gender){
        if(gender.equals("Male")){
            return evaluateCaloriesStatus(intake, 2200, 3000);
        }
        else{
            return evaluateCaloriesStatus(intake,  2000, 2800);
        }
    }
    private String evaluateCaloriesStatusForAdults(float intake, String gender,String disease){
        if(gender.equals("Male")){
            if(disease.equals("Diabetes")){
                return evaluateCaloriesStatus(intake, 1400, 2000);
            }
            else if(disease.equals("High blood pressure")){
                return evaluateCaloriesStatus(intake, 2200, 2800);
            }
            else{
                return evaluateCaloriesStatus(intake, 2400, 3000);
            }
        }
        else{
            if(disease.equals("Diabetes")){
                return evaluateCaloriesStatus(intake, 1200, 1800);
            }
            else if(disease.equals("High blood pressure")){
                return evaluateCaloriesStatus(intake, 1800, 2200);
            }
            else{
                return evaluateCaloriesStatus(intake,  2000, 2800);
            }

        }
    }
    private String evaluateCaloriesStatusForSeniors(float intake, String gender,String disease){
        if(gender.equals("Male")){
            if(disease.equals("Diabetes")){
                return evaluateCaloriesStatus(intake, 1400, 1800);
            }
            else if(disease.equals("High blood pressure")){
                return evaluateCaloriesStatus(intake, 2000, 2400);
            }
            else{
                return evaluateCaloriesStatus(intake, 2000, 2600);
            }

        }
        else{
            if(disease.equals("Diabetes")){
                return evaluateCaloriesStatus(intake, 1200, 1600);
            }
            else if(disease.equals("High blood pressure")){
                return evaluateCaloriesStatus(intake, 1600, 2000);
            }
            else{
                return evaluateCaloriesStatus(intake,  1800, 2400);
            }

        }
    }
    public String getCurrentDateAsString() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(currentDate);

        return dateString;
    }
    public void showDatePicker(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                txtDate.setText(i + "-" + (i1 + 1) + "-" + i2);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(CaloriesActivity.this, callback, year, month, day);
        datePickerDialog.setTitle("my datetime picker");
        datePickerDialog.show();
    }
    public class doSomeThing implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id==R.id.imgDate){
                showDatePicker();
            }
            else if(id==R.id.btnAdd){
                addCalories();
            }
            else if(id == R.id.btnList){
                Intent intent = new Intent(CaloriesActivity.this, CaloriesListActivity.class);
                startActivity(intent);
            }
        }
    }
    public void back(View v){
        finish();
    }
    private void showAdviceDialog(String status, float intake,float burned) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Calories Advise");
        builder.setMessage(recommendation(status,intake,burned));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}