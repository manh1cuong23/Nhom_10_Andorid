package com.example.nhom4_chuongtrinh_ptudandroid.lequydo.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.adapter.BMIIndexAdapter;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.dao.BMIIndexDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model.BMIIndex;
import com.example.nhom4_chuongtrinh_ptudandroid.common.activity.MainActivity;

import java.util.ArrayList;

public class BMIIndexListActivity extends AppCompatActivity {
    ListView lvBI;
    Button  btnHome, btnRemove;
    ArrayList<BMIIndex> arrayList = new ArrayList<>();
    BMIIndexDAO bmiIndexDAO = null;
    int selectedItemPosition = -1;
    String itemId;
    BMIIndexAdapter adapterBmiIndex = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lqd_activity_bmiindex_list);
        getWidget();
        loadData();
        lvBI.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemId = String.valueOf(((BMIIndex) parent.getItemAtPosition(position)).getBmi_id());
                selectedItemPosition = position;
            }
        });
    }
    private void getWidget(){
        lvBI = findViewById(R.id.lvBI);
        btnHome = findViewById(R.id.btnHome);
        btnRemove = findViewById(R.id.btnRemove);
        btnHome.setOnClickListener(new doSomeTHing());
        btnRemove.setOnClickListener(new doSomeTHing());
        adapterBmiIndex = new BMIIndexAdapter(this,R.layout.lqd_custom_view_bmiindex,arrayList);
        lvBI.setAdapter(adapterBmiIndex);
    }
    protected class doSomeTHing implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnAdd){
                Intent intent = new Intent(BMIIndexListActivity.this, BMIIndexActivity.class);
                startActivity(intent);
            }
            if (id == R.id.btnHome){
                Intent intent = new Intent(BMIIndexListActivity.this, MainActivity.class);
                startActivity(intent);
            }
            if (id == R.id.btnRemove){
                removeData();
            }
        }
    }
    private void loadData() {
        arrayList.clear();
        bmiIndexDAO = new BMIIndexDAO(this);
        arrayList.addAll(bmiIndexDAO.getAll());
        adapterBmiIndex.notifyDataSetChanged();
    }

    private void showDeleteConfirm(String id, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BMIIndexListActivity.this);
        builder.setTitle("Confirm remove!");
        builder.setMessage("Do you want to remove this item?");
        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bmiIndexDAO.delete(id);
                arrayList.remove(position);
                adapterBmiIndex.notifyDataSetChanged();
                Toast.makeText(BMIIndexListActivity.this, "Remove successful", Toast.LENGTH_SHORT).show();
                selectedItemPosition = -1;
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void removeData() {
        if (selectedItemPosition != AdapterView.INVALID_POSITION) {
            showDeleteConfirm(itemId, selectedItemPosition);
        } else {
            Toast.makeText(BMIIndexListActivity.this, "Please select an item to remove!", Toast.LENGTH_SHORT).show();
        }
    }
    public void back(View v){
        finish();
    }
}