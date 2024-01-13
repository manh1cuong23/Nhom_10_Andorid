package com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.nguyencongtiep.model.QualitySleep;

import java.util.List;

public class QualitySleepAdapter extends ArrayAdapter<QualitySleep> {
    private Context context;

    public QualitySleepAdapter(Context context, List<QualitySleep> qualitySleepList) {
        super(context, 0, qualitySleepList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra xem convertView có được sử dụng lại hay không, nếu không thì inflate layout mới
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.nct_custom_list_sleep, parent, false);
        }

        // Lấy đối tượng QualitySleep từ danh sách
        QualitySleep qualitySleep = getItem(position);

        // Ánh xạ các View trong layout custom_list_item.xml
        TextView statusTextView = convertView.findViewById(R.id.tvStatus);
        TextView startTextView = convertView.findViewById(R.id.tvStart);
        TextView finishTextView = convertView.findViewById(R.id.tvFinish);
        TextView dateTextView = convertView.findViewById(R.id.tvCreateD);

        // Thiết lập giá trị cho các TextView
        statusTextView.setText("Status: " + qualitySleep.getStatus());
        startTextView.setText("Start: " + qualitySleep.getStart_sleep() + " ");
        finishTextView.setText("Finish:" + qualitySleep.getFinish_sleep() +" ");
        dateTextView.setText("Date: " + qualitySleep.getCrated_date());

        return convertView;
    }
}
