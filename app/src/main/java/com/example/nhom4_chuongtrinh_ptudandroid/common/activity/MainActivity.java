package com.example.nhom4_chuongtrinh_ptudandroid.common.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.activity.BMIIndexActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.activity.HeartHealthActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.activity.QualitySleepActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.activity.RemindWaterActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.activity.ExercisePlanActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.activity.ShowProfileActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.activity.CaloriesActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.activity.HealthStatisticActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton imgBtnStatistic,imgBtnCalories,imgBtnQualitySleep,imgBtnExercise,imgBtnHeartHealth,imgBtnBMIIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cm_activity_main);
        mapping();
        actionToolBar();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menuItemProfile){
                    Intent intent = new Intent(MainActivity.this, ShowProfileActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.menuItemRemindWater){
                    Intent intent = new Intent(MainActivity.this, RemindWaterActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.menuItemPassword){
                    Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.menuItemLogout){
                    logOut();

                }
                else{

                }
                return false;
            }
        });
    }
    private void actionToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }
    public void mapping(){
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        imgBtnStatistic = findViewById(R.id.imgBtnStatistic);
        imgBtnStatistic.setOnClickListener(new doSomeThing());
        imgBtnCalories= findViewById(R.id.imgBtnCalories);
        imgBtnCalories.setOnClickListener(new doSomeThing());
        imgBtnQualitySleep = findViewById(R.id.imgBtnQualitySleep);
        imgBtnQualitySleep.setOnClickListener(new doSomeThing());
        imgBtnExercise = findViewById(R.id.imgBtnExercisePlan);
        imgBtnExercise.setOnClickListener(new doSomeThing());
        imgBtnHeartHealth = findViewById(R.id.imgBtnHeartHealth);
        imgBtnHeartHealth.setOnClickListener(new doSomeThing());
        imgBtnBMIIndex = findViewById(R.id.imgBtnBMI);
        imgBtnBMIIndex.setOnClickListener(new doSomeThing());
    }
    public class doSomeThing implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.imgBtnStatistic){
                Intent intent = new Intent(MainActivity.this, HealthStatisticActivity.class);
                startActivity(intent);
            }
            if(id == R.id.imgBtnCalories){
                Intent intent = new Intent(MainActivity.this, CaloriesActivity.class);
                startActivity(intent);
            }
            if(id == R.id.imgBtnQualitySleep){
                Intent intent = new Intent(MainActivity.this, QualitySleepActivity.class);
                startActivity(intent);
            }
            if(id == R.id.imgBtnExercisePlan){
                Intent intent = new Intent(MainActivity.this, ExercisePlanActivity.class);
                startActivity(intent);
            }
            if(id == R.id.imgBtnHeartHealth){
                Intent intent = new Intent(MainActivity.this, HeartHealthActivity.class);
                startActivity(intent);
            }
            if(id == R.id.imgBtnBMI){
                Intent intent = new Intent(MainActivity.this, BMIIndexActivity.class);
                startActivity(intent);
            }
        }
    }

    public void logOut(){
        UserSession userSession = UserSession.getInstance();
        userSession.clearSession();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }
}