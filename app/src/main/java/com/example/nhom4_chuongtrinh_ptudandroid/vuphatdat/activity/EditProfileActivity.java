package com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.nhom4_chuongtrinh_ptudandroid.R;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.dao.ProfileDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model.Profile;


public class EditProfileActivity extends AppCompatActivity {
    ProfileDAO profileDao;
    EditText editName;
    EditText txtDob;
    Button btnExit;
    RadioGroup radGender;
    RadioButton radMale, radFeMale;
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    CheckBox cbNo;
    Button btnEdit;
    ImageButton btnDate;
    Calendar cal = Calendar.getInstance();
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpd_activity_edit_profile);
        profileDao = new ProfileDAO(this);
        getWidget();
        //addEventFormWidgets();
        getCheckBox();
        Profile profile = profileDao.getProfile();
        displayProfileInfor(profile);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(profile);
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,ShowProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void getWidget(){
        editName = findViewById(R.id.editName);
        radGender = findViewById(R.id.radGender);
        btnEdit = findViewById(R.id.btnEdit);
        txtDob = findViewById(R.id.txtDate);
        btnDate = findViewById(R.id.btnDate);
        btnExit = findViewById(R.id.btnExit);
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cbNo = findViewById(R.id.cbNo);
        radMale = findViewById(R.id.radMale);
        radFeMale = findViewById(R.id.radFemale);

    }
    public void getCheckBox(){
        CompoundButton.OnCheckedChangeListener checkboxCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView == cbNo && isChecked) {
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                }
                if((buttonView == cb1 && isChecked) || (buttonView == cb2 && isChecked)
                        || (buttonView == cb3 && isChecked) || (buttonView == cb4 && isChecked)
                ){
                    cbNo.setChecked(false);
                }
            }
        };
        cb1.setOnCheckedChangeListener(checkboxCheckedChangeListener);
        cb2.setOnCheckedChangeListener(checkboxCheckedChangeListener);
        cb3.setOnCheckedChangeListener(checkboxCheckedChangeListener);
        cb4.setOnCheckedChangeListener(checkboxCheckedChangeListener);
        cbNo.setOnCheckedChangeListener(checkboxCheckedChangeListener);
    }
    private void displayProfileInfor(Profile profile){
        if(profile!=null){
            editName.setText(profile.getFullName());
            txtDob.setText(profile.getDob());
            if("Male".equals(profile.getSex())){
                radMale.setChecked(true);
            }
            if("Female".equals(profile.getSex())){
                radFeMale.setChecked(true);
            }
            String diseases = profile.getDiseases();
            String[] disease = diseases.split(",");
            for(String i : disease){
                if(i.equalsIgnoreCase("Fatigue")){
                    cb1.setChecked(true);
                }
                if(i.equalsIgnoreCase("high blood pressure")){
                    cb2.setChecked(true);
                }
                if(i.equalsIgnoreCase("diabetes")){
                    cb3.setChecked(true);
                }
                if(i.equalsIgnoreCase("metal illness")){
                    cb4.setChecked(true);
                }
                if(i.equalsIgnoreCase("no underlying diseases")){
                    cbNo.setChecked(true);
                }
            }

        }
    }
    private void updateProfile(Profile profile){
        String name = editName.getText().toString();
        int selectedGenderId = radGender.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedGenderId);
        String gender = radioButton.getText().toString();
        String dob = txtDob.getText().toString();
        String diseases = "";
        if(cb1.isChecked()){
            diseases += cb1.getText().toString() + ",";
        }
        if(cb2.isChecked()){
            diseases += cb2.getText().toString() + ",";
        }
        if(cb3.isChecked()){
            diseases += cb3.getText().toString() + ",";
        }
        if(cb4.isChecked()){
            diseases += cb4.getText().toString() + ",";
        }
        if(cbNo.isChecked()){
            cb1.setActivated(false);
            cb2.setActivated(false);
            cb3.setActivated(false);
            cb4.setActivated(false);
            diseases = "";
            diseases += cbNo.getText().toString() ;
        }
        if(diseases.endsWith(",")){
            diseases = diseases.substring(0, diseases.length() - 1);
        }
        profile.setFullName(name);
        profile.setDob(dob);
        profile.setSex(gender);
        profile.setDiseases(diseases);
        boolean update = profileDao.updateProfile(profile);
        if(update){
            Toast.makeText(getApplicationContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear,
                                          int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                        txtDob.setText((year) +"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        //Lưu vết lại biến ngày hoàn thành
                        cal.set(year, monthOfYear, dayOfMonth);

                    }
                };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtDob.getText()+"";
        String strArrtmp[]=s.split("-");
        int ngay=Integer.parseInt(strArrtmp[2]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[0]);
        DatePickerDialog pic=new DatePickerDialog(
                EditProfileActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }

}