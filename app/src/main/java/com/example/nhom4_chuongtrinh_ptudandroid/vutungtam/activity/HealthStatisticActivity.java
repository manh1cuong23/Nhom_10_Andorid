package com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.statistic.BMIIndexStatisticActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.statistic.CaloriesStatistic;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.statistic.ExercisePlanStatistic;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.statistic.HeartHealthStatisticActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.statistic.QualitySleepStatisticActivity;

public class HealthStatisticActivity extends AppCompatActivity {

    ImageButton btnHeart,btnBMI,btnSleep,btnCalories,btnExercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vtt_activity_health_statistic);
        mapping();
    }
    public void mapping(){
        btnHeart = findViewById(R.id.imgBtnHeartHealth);
        btnBMI = findViewById(R.id.imgBtnBMI);
        btnSleep = findViewById(R.id.imgBtnQualitySleep);
        btnCalories = findViewById(R.id.imgBtnCalories);
        btnExercise = findViewById(R.id.imgBtnExercisePlan);
        btnHeart.setOnClickListener(new doSomeThing());
        btnBMI.setOnClickListener(new doSomeThing());
        btnSleep.setOnClickListener(new doSomeThing());
        btnCalories.setOnClickListener(new doSomeThing());
        btnExercise.setOnClickListener(new doSomeThing());
    }
    public class doSomeThing implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.imgBtnHeartHealth){
                Intent intent = new Intent(HealthStatisticActivity.this, HeartHealthStatisticActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.imgBtnBMI){
                Intent intent = new Intent(HealthStatisticActivity.this, BMIIndexStatisticActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.imgBtnQualitySleep){
                Intent intent = new Intent(HealthStatisticActivity.this, QualitySleepStatisticActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.imgBtnCalories){
                Intent intent = new Intent(HealthStatisticActivity.this, CaloriesStatistic.class);
                startActivity(intent);
            }
            else if(id == R.id.imgBtnExercisePlan){
                Intent intent = new Intent(HealthStatisticActivity.this, ExercisePlanStatistic.class);
                startActivity(intent);
            }
        }
    }
    public void back(View v){
        finish();
    }
}