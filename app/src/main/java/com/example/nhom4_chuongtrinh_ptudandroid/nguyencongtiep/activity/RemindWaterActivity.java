package com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.adapter.RemindWaterAdapter;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.dao.RemindWaterDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.model.RemindWater;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.receiver.ReminderReceiver;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RemindWaterActivity extends AppCompatActivity {
    TextView tvTarget;
    ProgressBar progressDrinkW;

    ListView lvWater;

    ImageButton imgCreate,imgUpdate;

    RemindWaterDAO remindWaterDAO;

    List<RemindWater> list = new ArrayList<>();

    RemindWaterAdapter arrayAdapter;
    Context context;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nct_activity_remind_water);
        mapping();
        startNewDay();
        updateTvTarget();
        // Tự động đặt lịch nhắc nhở khi Activity được tạo
        autoReminder();
        imgCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddWaterDialog();
            }
        });
        imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateFrequencyDialog();
            }
        });
        lvWater.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                remove(position);
                return false;
            }
        });
    }
    public void mapping(){
        remindWaterDAO = new RemindWaterDAO(this);
        tvTarget = findViewById(R.id.tvTarget);
        progressDrinkW = findViewById(R.id.progressDrinkW);
        lvWater = findViewById(R.id.lvWater);
        imgCreate = findViewById(R.id.imgCreate);
        imgUpdate = findViewById(R.id.imgUpdate);
        user_id = remindWaterDAO.getUser_id();
        //khởi tạo các biến
        context = this;
        //hien thi du lieu khi chay chuong trinh
        list.clear();


        list = remindWaterDAO.getAllInDay(getCurrentDate());
        arrayAdapter = new RemindWaterAdapter(context,list);
        lvWater.setAdapter(arrayAdapter);

        int totalConsumed = getTotalConsumed(); // Hàm này cần được triển khai để tính tổng lượng nước đã uống
        float target = calculateTarget();
        progressDrinkW.setMax((int)target);
        progressDrinkW.setProgress(totalConsumed);
    }
    private void autoReminder() {
        // Lấy dữ liệu từ cơ sở dữ liệu
        float frequency = remindWaterDAO.getFrequencyFromDatabase();

        if (frequency > 0) {
            // Tính toán thời gian giữa mỗi lần nhắc nhở (đơn vị: milliseconds)
            long reminderInterval = (long) (frequency * 60 * 1000); // Chuyển đổi phút thành milliseconds

            int totalConsumed = getTotalConsumed();
            float target = calculateTarget();
            Log.d("ReminderDebug", "totalConsumed: " + totalConsumed);
            Log.d("ReminderDebug", "target: " + target);
            if (totalConsumed < target) {
                // Đặt lịch nhắc nhở sử dụng AlarmManager
                scheduleReminder(reminderInterval);
            } else {
                // Nếu totalConsumed >= target, hủy bỏ lịch nhắc nhở
                cancelReminder();
            }
        }
    }
    private void scheduleReminder(long interval) {
        // Sử dụng AlarmManager để đặt lịch nhắc nhở
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int totalConsumed = getTotalConsumed();
        float target = calculateTarget();

        int remain = (int) (target - totalConsumed);

        // Tạo Intent để gửi broadcast khi nhắc nhở được kích hoạt
        Intent intent = new Intent(RemindWaterActivity.this, ReminderReceiver.class);
        intent.setAction("MyAction");
        intent.putExtra("remain",remain);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Thiết lập lịch nhắc nhở cứ sau mỗi khoảng thời gian interval
        // Bắt đầu từ thời điểm hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Log.d("ReminderService", "Setting up repeating alarm");

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + interval,interval, pendingIntent);
    }
    private void cancelReminder() {
        Intent intent = new Intent(RemindWaterActivity.this, ReminderReceiver.class);
        intent.setAction("MyAction");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Hủy bỏ lịch nhắc nhở
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        // Hủy bỏ cả PendingIntent
        pendingIntent.cancel();
    }
    private void showUpdateFrequencyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Frequency");

        View view = getLayoutInflater().inflate(R.layout.nct_dialog_update_frequency, null);
        final EditText edtFrequency = view.findViewById(R.id.edtFrequency);

        builder.setView(view);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Lấy giá trị từ EditText
                String frequencyStr = edtFrequency.getText().toString();

                // Kiểm tra xem người dùng đã nhập frequency chưa
                if (!frequencyStr.isEmpty()) {
                    float frequency = Float.parseFloat(frequencyStr);

                    // Cập nhật giá trị trong CSDL
                    updateFrequency(frequency);

                    autoReminder();

                    // Cập nhật giao diện
                    updateUI();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
    private void startNewDay() {
        // Lấy ngày hiện tại
        String currentDate = getCurrentDate();

        // Lấy ngày lưu trữ trong CSDL (nếu có)
        String createdDate = remindWaterDAO.getCreatedDate(); // Triển khai hàm này trong RemindWaterDAO

        // So sánh ngày hiện tại với ngày lưu trữ
        if (!currentDate.equals(createdDate)) {
            // Nếu là ngày mới, thực hiện reset CSDL và đặt lịch nhắc nhở
            remindWaterDAO.deleteAll();
            updateProgressBar();
            updateListView();

        }
    }

    private void updateFrequency(float frequency) {
        remindWaterDAO.updateFrequency( frequency);
    }
    private void showAddWaterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Water");

        View view = getLayoutInflater().inflate(R.layout.nct_dialog_add_water, null);
        final EditText edtAmount = view.findViewById(R.id.edtAmount);

        builder.setView(view);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Lấy giá trị từ EditText
                String amountStr = edtAmount.getText().toString();

                // Kiểm tra xem người dùng đã nhập amount chưa
                if (!amountStr.isEmpty()) {
                    float amount = Float.parseFloat(amountStr);

                    // Thêm vào CSDL
                    insertWater(amount);

                    // Cập nhật giao diện
                    updateUI();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    private void insertWater(float amount) {

        String time = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        String date = getCurrentDate();
        RemindWater remindWater = new RemindWater(amount,2,time,date,user_id);
        remindWaterDAO.insert(remindWater);
        autoReminder();
    }

    private void updateUI() {
        updateProgressBar();
        updateListView();
        updateTvTarget();
    }
    private void updateListView() {
        context = this;
        list.clear();
        remindWaterDAO = new RemindWaterDAO(context);

        list = remindWaterDAO.getAllInDay(getCurrentDate());

        arrayAdapter = new RemindWaterAdapter(context,list);
        lvWater.setAdapter(arrayAdapter);
    }
    // Phương thức để lấy ngày hiện tại
    private String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(formatter);
    }
    private float calculateTarget() {
        // Lấy giá trị cân nặng từ CSDL
        float weight = getWeightFromDatabase();
        // Áp dụng công thức tính target
        return (weight * 0.03f) * 1000;
    }

    private float getWeightFromDatabase() {
        // Triển khai hàm này để truy vấn CSDL và lấy giá trị cân nặng
        int userId = remindWaterDAO.getUser_id(); // Thay thế bằng userId thích hợp
        float weight = remindWaterDAO.getWeightFromDatabase(userId);
        // Kiểm tra nếu giá trị trọng lượng không hợp lệ hoặc là rỗng
        if (Float.isNaN(weight) || weight <= 0) {
            return 66.6f;
        }
        return weight;
    }
    @SuppressLint("DefaultLocale")
    private void updateTvTarget() {
        int totalConsumed = getTotalConsumed();
        float target = calculateTarget();

        // Tính toán giá trị cần hiển thị trong edtTarget
        float remainingTarget = target - totalConsumed;

        // Hiển thị giá trị trong edtTarget
        tvTarget.setText(String.format("%d/%.0f ml", totalConsumed, target));
    }
    private void updateProgressBar() {
        int totalConsumed = getTotalConsumed(); // Hàm này cần được triển khai để tính tổng lượng nước đã uống
        float target = calculateTarget();
        ProgressBar progressDrinkW = findViewById(R.id.progressDrinkW);
        progressDrinkW.setMax((int)target);
        progressDrinkW.setProgress(totalConsumed);
    }
    private int getTotalConsumed() {
        // Triển khai hàm này để tính tổng lượng nước đã uống
        return remindWaterDAO.getTotalConsumed(); // Thay thế 0 bằng user_id thích hợp
    }
    public void remove(int position){
        if (position != ListView.INVALID_POSITION) {
            RemindWater selectedRemindWater = list.get(position);

            new AlertDialog.Builder(context)
                    .setTitle("Confirm delete")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Gọi phương thức xóa từ RemindWaterDAO để xóa dữ liệu từ cơ sở dữ liệu
                            int result = (int)remindWaterDAO.delete(String.valueOf(selectedRemindWater.getRemind_water_id()));

                            if (result > 0) {
                                // Nếu xóa thành công, cập nhật danh sách và cập nhật ListView
                                list.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                updateTvTarget();
                                updateProgressBar();
                                Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        } else {
            Toast.makeText(context, "No item selected", Toast.LENGTH_SHORT).show();
        }
    }
    public void back(View v){
        finish();
    }
}