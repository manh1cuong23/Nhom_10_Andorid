package com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nhom4_chuongtrinh_ptudandroid.common.dao.UserDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.dao.ProfileDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model.Profile;
import com.example.nhom4_chuongtrinh_ptudandroid.R;

public class ShowProfileActivity extends AppCompatActivity {
    TextView txtFullName, txtDateOfBirth, txtSex, txtDiseases;
    Button btnUpdate;
    ProfileDAO profileDao;
    ImageButton imgBtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpd_activity_show_profile);
        getWidget();
        profileDao = new ProfileDAO(this);
        Profile profile = profileDao.getProfile();
        txtFullName.setText(profile.getFullName());
        txtDateOfBirth.setText(profile.getDob());
        txtSex.setText(profile.getSex());
        txtDiseases.setText(profile.getDiseases());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgBtnBack = findViewById(R.id.custom_back_button);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void getWidget(){
        txtFullName = findViewById(R.id.txtFullName);
        txtDateOfBirth = findViewById(R.id.txtDateOfBirth);
        txtSex = findViewById(R.id.txtSex);
        txtDiseases = findViewById(R.id.txtDiseases);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
    public void back(View v){
        finish();
    }
}