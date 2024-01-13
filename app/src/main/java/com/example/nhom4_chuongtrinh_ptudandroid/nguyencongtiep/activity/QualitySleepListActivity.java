package com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.common.activity.MainActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.activity.BMIIndexListActivity;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.adapter.QualitySleepAdapter;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.dao.QualitySleepDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.model.QualitySleep;

import java.util.ArrayList;
import java.util.List;

public class QualitySleepListActivity extends AppCompatActivity {
    ListView lvQuality;
    Button btnHome,btnRemove;

    QualitySleepDAO qualitySleepDAO;
    QualitySleepAdapter arrayAdapter;
    List<QualitySleep> list = new ArrayList<>();

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nct_activity_quality_sleep_list);
        mapping();
        qualitySleepDAO = new QualitySleepDAO(this);
        getSelectLV();
        backHome();
    }
    public void mapping(){
        lvQuality = findViewById(R.id.lvQuality);
        btnHome = findViewById(R.id.btnHome);
        btnRemove = findViewById(R.id.btnRemove);

        //khởi tạo các biến
        context = this;
        //hien thi du lieu khi chay chuong trinh
        list.clear();
        qualitySleepDAO = new QualitySleepDAO(context);

        list = qualitySleepDAO.getAll();

        arrayAdapter = new QualitySleepAdapter(context,list);
        lvQuality.setAdapter(arrayAdapter);
    }
    public void getSelectLV(){
        lvQuality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                getProcessRemove(position);
            }
        });
    }
    public void getProcessRemove(final int selectedPosition){
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition != ListView.INVALID_POSITION) {
                    QualitySleep selectedQualitySleep = list.get(selectedPosition);

                    // Hiển thị AlertDialog để xác nhận việc xóa
                    new AlertDialog.Builder(context)
                            .setTitle("Confirm delete")
                            .setMessage("Are you sure you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Gọi phương thức xóa từ QualitySleepDAO để xóa dữ liệu từ cơ sở dữ liệu
                                    int result = (int) qualitySleepDAO.delete(String.valueOf(selectedQualitySleep.getSleep_id()));

                                    if (result > 0) {
                                        // Nếu xóa thành công, cập nhật danh sách và cập nhật ListView
                                        list.remove(selectedPosition);
                                        arrayAdapter.notifyDataSetChanged();
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
        });
    }
    public void backHome(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QualitySleepListActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
    public void back(View v){
        finish();
    }
}