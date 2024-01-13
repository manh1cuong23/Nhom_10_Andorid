package com.example.nhom4_chuongtrinh_ptudandroid.lequydo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.dao.BMIIndexDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model.BMIIndex;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.dao.ProfileDAO;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BMIIndexActivity extends AppCompatActivity {
    EditText editHeight, editWeight, editDate;
    Button btnAdd, btnList;
    ImageButton btnDate;
    Calendar cal;
    BMIIndexDAO myDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lqd_activity_bmiindex);
        getWidget();
        getDateDefault();
    }
    private void getWidget(){
        editHeight = findViewById(R.id.editHeight);
        editDate = findViewById(R.id.editDate);
        editWeight = findViewById(R.id.editWeight);
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);
        btnDate = findViewById(R.id.btnDate);
        btnAdd.setOnClickListener(new doSomeThing());
        btnDate.setOnClickListener(new doSomeThing());
        btnList.setOnClickListener(new doSomeThing());
    }
    private void getDateDefault(){
        //Lấy ngày hiện tại của hệ thống
        cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = null;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String strDate = dateFormat.format(cal.getTime());
        editDate.setText(strDate);
    }
    private void showDatePickerDialog(){
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                cal.set(year,month,dayOfMonth);
            }
        };
        //Set date khi mở hộp thoại
        String s = editDate.getText()+"";
        String strArrTmp[] = s.split("-");
        int day = Integer.parseInt(strArrTmp[2]);
        int month = Integer.parseInt(strArrTmp[1])-1;
        int year = Integer.parseInt(strArrTmp[0]);
        DatePickerDialog pic = new DatePickerDialog(BMIIndexActivity.this,callback,year,month,day);
        pic.setTitle("Choose create date");
        pic.show();
    }
    protected class doSomeThing implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnAdd){
                Add();
            }
            if (id == R.id.btnDate){
                showDatePickerDialog();
            }
            if (id == R.id.btnList){
                Intent intent1 = new Intent(BMIIndexActivity.this, BMIIndexListActivity.class);
                startActivity(intent1);
            }
        }
    }
    private void Add() {
        try {
            myDatabase = new BMIIndexDAO(BMIIndexActivity.this);
            BMIIndex bmiIndex = new BMIIndex();
            ProfileDAO profileDAO = new ProfileDAO(this);
            if (editHeight.getText().toString().isEmpty()) {
                Toast.makeText(this, "Height is empty!", Toast.LENGTH_SHORT).show();
                editHeight.requestFocus();
            } else if (editWeight.getText().toString().isEmpty()) {
                Toast.makeText(this, "Weight is empty!", Toast.LENGTH_SHORT).show();
                editWeight.requestFocus();
            } else {
                float weight = Float.parseFloat(editWeight.getText().toString());
                float height = Float.parseFloat(editHeight.getText().toString());
                String date = editDate.getText().toString();

                ArrayList<String> arrDisease = new ArrayList<>();
                arrDisease.addAll(profileDAO.getDisease());
                String ageGroup = profileDAO.getAgeGroup();
                String status = getStatus(arrDisease, height, weight,ageGroup);
                bmiIndex.setHeight(height);
                bmiIndex.setWeight(weight);
                bmiIndex.setCreated_date(date);
                bmiIndex.setStatus(status);

                myDatabase.insert(bmiIndex);
                Toast.makeText(this,"Add successfully", Toast.LENGTH_SHORT).show();
                clear();

            }
        }catch (Exception ex){
            Toast.makeText(this, "Error permission: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private String getStatus(ArrayList<String> arrDiseases, float height, float weight, String ageGroup){
        String status = "";
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

        float BMI = weight / (height*2);
        switch (priorityDisease) {
            case "Fatigue":
                switch (ageGroup){
                    case "Children":
                    case "Youth":
                        if(BMI < 15) status = "Malnutrition";
                        if (BMI >= 15 && BMI <= 16) status = "Underweight";
                        if (BMI > 16 && BMI <= 23 ) status = "Normal";
                        if (BMI > 23 && BMI <= 28 ) status = "Overweight";
                        if (BMI > 28 ) status = "Fat";
                        break;
                    case "Teenager":
                    case "Adolescent":
                        if(BMI < 16) status = "Malnutrition";
                        if (BMI >= 16 && BMI <= 17 ) status = "Underweight";
                        if (BMI > 17 && BMI <= 23 ) status = "Normal";
                        if (BMI > 23 && BMI <= 28 ) status = "Overweight";
                        if (BMI > 28 ) status = "Fat";
                        break;
                    case "Middle-age":
                    case "Old":
                        if(BMI < 17.5) status = "Malnutrition";
                        if (BMI >= 17.5 && BMI < 18.5 ) status = "Underweight";
                        if (BMI >= 18.5 && BMI <= 25 ) status = "Normal";
                        if (BMI > 25 && BMI <= 30 ) status = "Overweight";
                        if (BMI > 30 ) status = "Fat";
                        break;
                }
                break;
            case "High blood pressure":
                switch (ageGroup){
                    case "Children":
                    case "Youth":
                        if(BMI < 15) status = "Malnutrition";
                        if (BMI >= 15 && BMI <= 16) status = "Underweight";
                        if (BMI > 16 && BMI <= 24 ) status = "Normal";
                        if (BMI > 24 && BMI <= 28 ) status = "Overweight";
                        if (BMI > 28 ) status = "Fat";
                        break;
                    case "Teenager":
                    case "Adolescent":
                        if(BMI < 16) status = "Malnutrition";
                        if (BMI >= 16 && BMI <= 17 ) status = "Underweight";
                        if (BMI > 17 && BMI <= 24 ) status = "Normal";
                        if (BMI > 24 && BMI <= 28 ) status = "Overweight";
                        if (BMI > 28 ) status = "Fat";
                        break;
                    case "Middle-age":
                    case "Old":
                        if(BMI < 17.5) status = "Malnutrition";
                        if (BMI >= 17.5 && BMI <= 18.5 ) status = "Underweight";
                        if (BMI > 18.5 && BMI <= 29 ) status = "Normal";
                        if (BMI > 29 && BMI <= 33 ) status = "Overweight";
                        if (BMI > 33 ) status = "Fat";
                        break;
                }
                break;
            case "Diabetes":
                switch (ageGroup){
                    case "Children":
                    case "Youth":
                        if(BMI < 15) status = "Malnutrition";
                        if (BMI >= 15 && BMI <= 16) status = "Underweight";
                        if (BMI > 16 && BMI <= 25 ) status = "Normal";
                        if (BMI > 25 && BMI <= 28 ) status = "Overweight";
                        if (BMI > 28 ) status = "Fat";
                        break;
                    case "Teenager":
                    case "Adolescent":
                        if(BMI < 16) status = "Malnutrition";
                        if (BMI >= 16 && BMI <= 17 ) status = "Underweight";
                        if (BMI > 17 && BMI <= 25 ) status = "Normal";
                        if (BMI > 25 && BMI <= 28 ) status = "Overweight";
                        if (BMI > 28 ) status = "Fat";
                        break;
                    case "Middle-age":
                    case "Old":
                        if(BMI < 17.5) status = "Malnutrition";
                        if (BMI >= 17.5 && BMI <= 18.5 ) status = "Underweight";
                        if (BMI > 18.5 && BMI <= 30 ) status = "Normal";
                        if (BMI > 30 && BMI <= 33 ) status = "Overweight";
                        if (BMI > 33 ) status = "Fat";
                        break;
                }
                break;
            case "Mental illness":
                switch (ageGroup){
                    case "Children":
                    case "Youth":
                        if(BMI <= 15) status = "Malnutrition";
                        if (BMI > 15 && BMI <= 16) status = "Underweight";
                        if (BMI > 16 && BMI <= 25 ) status = "Normal";
                        if (BMI > 25 && BMI <= 28 ) status = "Overweight";
                        if (BMI > 28 ) status = "Fat";
                        break;
                    case "Teenager":
                    case "Adolescent":
                        if(BMI < 16) status = "Malnutrition";
                        if (BMI >= 16 && BMI <= 17 ) status = "Underweight";
                        if (BMI > 17 && BMI <= 25 ) status = "Normal";
                        if (BMI > 25 && BMI <= 28 ) status = "Overweight";
                        if (BMI > 28 ) status = "Fat";
                        break;
                    case "Middle-age":
                    case "Old":
                        if(BMI < 17.5) status = "Malnutrition";
                        if (BMI >= 17.5 && BMI <= 18.5 ) status = "Underweight";
                        if (BMI > 18.5 && BMI <= 30 ) status = "Normal";
                        if (BMI > 30 && BMI <= 33 ) status = "Overweight";
                        if (BMI > 33 ) status = "Fat";
                        break;
                }
                break;
            case "No underlying diseases":
                switch (ageGroup){
                    case "Children":
                    case "Youth":
                        if(BMI < 16) status = "Malnutrition";
                        if (BMI >= 16 && BMI <= 17) status = "Underweight";
                        if (BMI > 17 && BMI <= 23 ) status = "Normal";
                        if (BMI > 23 && BMI <= 28 ) status = "Overweight";
                        if (BMI > 28 ) status = "Fat";
                        break;
                    case "Teenager":
                    case "Adolescent":
                        if(BMI < 17) status = "Malnutrition";
                        if (BMI >= 17 && BMI <= 18 ) status = "Underweight";
                        if (BMI > 18 && BMI <= 23 ) status = "Normal";
                        if (BMI > 23 && BMI <= 28 ) status = "Overweight";
                        if (BMI > 28 ) status = "Fat";
                        break;
                    case "Middle-age":
                    case "Old":
                        if(BMI < 18.5) status = "Malnutrition";
                        if (BMI >= 18.5 && BMI <= 20 ) status = "Underweight";
                        if (BMI > 20 && BMI <= 25 ) status = "Normal";
                        if (BMI > 25 && BMI <= 30 ) status = "Overweight";
                        if (BMI > 30 ) status = "Fat";
                        break;
                }
        }
        return status;
    }
    private void clear(){
        editHeight.setText("");
        editWeight.setText("");
        editHeight.requestFocus();
    }

    public void back(View v){
        finish();
    }

}