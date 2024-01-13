package com.example.nhom4_chuongtrinh_ptudandroid.common.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.common.dao.UserDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.common.objects.User;

public class RegisterActivity extends AppCompatActivity {
    EditText editUserName, editPassword, editConfirm, editEmail;
    Button btnSignUp;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cm_activity_register);
        getWidget();
    }
    private void getWidget(){
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        editConfirm = findViewById(R.id.editConfirm);
        editEmail = findViewById(R.id.editEmail);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new doSomeThing());
    }
    protected class doSomeThing implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnSignUp){
                SignUp();
            }
        }
    }


    /*
     * .*\\d.*: Kiểm tra xem chuỗi có chứa ít nhất một số không.
     * .*[A-Z].*: Kiểm tra xem chuỗi có chứa ít nhất một chữ cái in hoa không.
     * .*[a-z].*: Kiểm tra xem chuỗi có chứa ít nhất một chữ cái thường không.
     * .*[!@#$%^&*()-_+=<>?].*: Kiểm tra xem chuỗi có chứa ít nhất một ký tự đặc biệt không.
     * */
    private boolean isValidUserName(String userName) {
        if (userName.length() < 8 || !userName.matches(".*[A-Z].*") || !userName.matches(".*\\d.*")){
            return false;
        }
        return true;
    }
    private boolean isValidPassword(String password) {
        if (password.contains(" ") || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[!@#$%^&*()-_+=<>?].*")){
            return false;
        }
        return true;
    }
    private boolean isConfirm(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }
    /*
     * [A-Za-z0-9._%+-]: chứa chữ cái, số, các ký tự như '.', '_',...
     * \.: ngăn cách tên miền
     * [A-Za-z]{2,6}$: kết thúc chuỗi với 1 tên miền có độ dài từ 2 đến 6 ký tự từ bảng chữ tiếng anh
     * */
    private boolean isValidEmail(String email){
        String regexEmail = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(regexEmail);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void SignUp() {
        userDAO = new UserDAO(RegisterActivity.this);
        String userName = editUserName.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirm.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if (userName.isEmpty()) {
            showToast("Must enter user name!");
            editUserName.requestFocus();
        }
        if (password.isEmpty()) {
            showToast("Must enter password!");
            editPassword.requestFocus();
        }
        if (confirmPassword.isEmpty()) {
            showToast("Must confirm password!");
            editConfirm.requestFocus();
        }
        if(email.isEmpty()){
            showToast("Must enter email!");
            editEmail.requestFocus();
        }

        User rg = new User();
        if (!isValidUserName(userName)) {
            showToast("Invalid username");
            editUserName.requestFocus();
        }
        else if(userDAO.checkExistUser(userName)){
            showToast("Username already exists");
            editUserName.requestFocus();
        }
        else if (!isValidEmail(email)){
            showToast("Invalid email");
            editEmail.requestFocus();
        }
        else if (userDAO.checkExistEmail(email)) {
            showToast("Email was registered");
            editEmail.requestFocus();
        }
        else if (!isValidPassword(password)) {
            showToast("Invalid password");
            editPassword.requestFocus();
        }
        else if (!isConfirm(password, confirmPassword)) {
            showToast("Mismatched password");
            editConfirm.requestFocus();
        }
        else{
            rg.setUser_name(userName);
            rg.setUser_password(password);
            rg.setEmail(email);
            userDAO.insert(rg);
            showToast("Registration successful");
            finish();
        }
    }
    public void backToLogin(View v){
        finish();
    }
}