package com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.common.activity.MainActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.UserSession;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.validate.Validate;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.dao.ProfileDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model.Profile;

public class CreateProfileActivity extends AppCompatActivity {
    ProfileDAO profileDao;
    EditText editName, txtDob;
    TextView txtNote;
    RadioGroup radGender;
    RadioButton radMale, radFemale;
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    CheckBox cbNo;
    Button btnSave;
    ImageButton btnDate;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpd_activity_create_profile);
        profileDao = new ProfileDAO(this);
        getWidget();
        addEventFormWidgets();
        getCheckBox();

    }
    private void addEventFormWidgets() {
        btnSave.setOnClickListener(new MyButtonEvent());
        btnDate.setOnClickListener(new MyButtonEvent());
    }
    private class MyButtonEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (v.getId()== R.id.btnSave){
                addProfile();
            }
            if (v.getId()== R.id.btnDate){
                showDatePickerDialog();
            }

        }
    }
    public void getWidget(){
        editName = findViewById(R.id.editName);
        radGender = findViewById(R.id.radGender);
        btnSave = findViewById(R.id.btnSave);
        txtDob = findViewById(R.id.txtDate);
        btnDate = findViewById(R.id.btnDate);
        txtNote = findViewById(R.id.txtNote);
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cbNo = findViewById(R.id.cbNo);
        radMale = findViewById(R.id.radMale);
        radFemale = findViewById(R.id.radFemale);
    }
    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear,
                                          int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                        txtDob.setText(year +"-"+(monthOfYear+1)+"-"+dayOfMonth);
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
        int nam=Integer.parseInt(strArrtmp[1]);
        DatePickerDialog pic=new DatePickerDialog(
                CreateProfileActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
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

    public void addProfile(){
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

            diseases += cbNo.getText().toString() + ",";
        }
        if(!Validate.containDigits(name) || name.length() < 2 || name.equalsIgnoreCase("") || !Validate.containsSpecialCharacters(name)){
            int redColor = ContextCompat.getColor(CreateProfileActivity.this, R.color.red);
            editName.setBackgroundTintList(ColorStateList.valueOf(redColor));
            txtNote.setTextColor(redColor);
            Validate.shakeEditText(editName);
        }
        else{
            diseases = diseases.replaceAll("\\s+", " ");
            if(diseases.endsWith(",")){
                diseases = diseases.substring(0, diseases.length() - 1);
            }
            UserSession userSession = UserSession.getInstance();
            int user_id = userSession.getUserId();
            Profile profile = new Profile(name,dob,gender, diseases,user_id);
            profileDao.addProfile(profile);
            Toast.makeText(CreateProfileActivity.this,"Create new profile is successfully!!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}