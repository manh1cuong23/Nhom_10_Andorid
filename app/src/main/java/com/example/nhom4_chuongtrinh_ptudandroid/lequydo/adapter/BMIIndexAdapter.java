package com.example.nhom4_chuongtrinh_ptudandroid.lequydo.adapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model.BMIIndex;

import java.util.ArrayList;

public class BMIIndexAdapter extends ArrayAdapter {
    Activity context;
    int LayoutID;
    ArrayList<BMIIndex> list = null;

    public BMIIndexAdapter(@NonNull Activity context, int resource, ArrayList<BMIIndex> list) {
        super(context, resource,list);
        this.context = context;
        LayoutID = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(LayoutID,null);
        if (list.size() > 0 && position >= 0){
            BMIIndex bmiIndex = list.get(position);
            final TextView txtHeight = convertView.findViewById(R.id.txtHeight);
            final TextView txtWeight = convertView.findViewById(R.id.txtWeight);
            final TextView txtStatus = convertView.findViewById(R.id.txtStatus);
            final TextView txtDate = convertView.findViewById(R.id.txtDate);

            txtHeight.setText(bmiIndex.getHeight()+" m");
            txtWeight.setText(bmiIndex.getWeight()+" kg");
            txtStatus.setText(bmiIndex.getStatus()+"");
            txtDate.setText(bmiIndex.getCreated_date()+"");
        }
        return convertView;
    }
}

