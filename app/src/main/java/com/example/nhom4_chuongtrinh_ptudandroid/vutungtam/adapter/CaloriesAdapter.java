package com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model.Calories;

import java.util.List;

public class CaloriesAdapter extends ArrayAdapter<Calories> {
    private Context context;


    public CaloriesAdapter(Context context, List<Calories> caloriesList) {
        super(context, 0, caloriesList);
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.vtt_calories_list_item, parent, false);
        }
        Calories calories = getItem(position);
        final TextView txtCalo = convertView.findViewById(R.id.txtCalo);
        final TextView txtStatus = convertView.findViewById(R.id.txtStatus);
        final TextView txtCreated_date = convertView.findViewById(R.id.txtCreated_date);
        txtCalo.setText((calories.getCalories_intake() - calories.getCalories_burned()) + "");
        txtStatus.setText(calories.getStatus());
        txtCreated_date.setText(calories.getCreated_date());
        return convertView;
    }
}

