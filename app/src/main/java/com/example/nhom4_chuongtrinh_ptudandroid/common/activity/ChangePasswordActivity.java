package com.example.nhom4_chuongtrinh_ptudandroid.common.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.common.dao.UserDAO;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText editEmail, editOldPassword, editNewPassword, editConfirm;
    Button btnChange;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cm_activity_change_password);
        mapping();
    }
    public void mapping(){
        userDAO = new UserDAO(this);
        editEmail = findViewById(R.id.editEmail);
        editOldPassword = findViewById(R.id.editOldPass);
        editNewPassword = findViewById(R.id.editNewPass);
        editConfirm = findViewById(R.id.editConfirm);
        btnChange = findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
    }
    public void change(){
        String email = editEmail.getText().toString();
        String oldPassword = editOldPassword.getText().toString();
        String newPassword = editNewPassword.getText().toString();
        String confirmPassword = editConfirm.getText().toString();
        if(!isValidPassword(newPassword)){
            Toast.makeText(this, "Password must be at least 8 characters, contain uppercase letters, numbers, lowercase letters and special characters", Toast.LENGTH_SHORT).show();
        }
        if (!isConfirm(newPassword, confirmPassword)) {
            Toast.makeText(this, "Mismatched password", Toast.LENGTH_SHORT).show();
        } else {
            String changePasswordResult = userDAO.changePassword(email, oldPassword, newPassword);
            Toast.makeText(this, changePasswordResult, Toast.LENGTH_SHORT).show();

            if (changePasswordResult.equals("Success")) {
                finish();
            }
        }
    }
    private boolean isConfirm(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }
    private boolean isValidPassword(String password) {
        if (password.contains(" ") || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[!@#$%^&*()-_+=<>?].*")){
            return false;
        }
        return true;
    }
    public void back(View v){
        finish();
    }
}
