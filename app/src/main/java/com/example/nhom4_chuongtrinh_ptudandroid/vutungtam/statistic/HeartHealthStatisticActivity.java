package com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.statistic;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.dao.StatisticDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model.StatisticStatus;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model.HeartHealth;
public class HeartHealthStatisticActivity extends AppCompatActivity {

    private LineChart lineChart;
    private PieChart pieChart;
    private StatisticDAO statisticDAO ;
    ImageButton btnShowDatePicker,btnBack;
    TextView txtStart, txtFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vtt_activity_heart_health_statistic);
        mapping();
        statisticDAO = new StatisticDAO(this);
        createLineChart(null,null);
        createPieChart(null,null);
        btnShowDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }
    public void mapping(){
        txtStart = findViewById(R.id.txtStart);
        txtFinish = findViewById(R.id.txtFinish);
        btnShowDatePicker = findViewById(R.id.showDatePicker);
        lineChart = findViewById(R.id.lineChart);
        pieChart = findViewById(R.id.pieChart);
    }
    public void createLineChart(String startDateString,String finishDateString){
        List<Entry> beatEntries = new ArrayList<>();
        List<Entry> pressureEntries = new ArrayList<>();
        ArrayList<HeartHealth> heartHealthsArr = new ArrayList<>();
        heartHealthsArr.addAll(statisticDAO.getHeartHealthData(startDateString,finishDateString));
        int i = 1;
        for (HeartHealth data: heartHealthsArr) {
            beatEntries.add(new Entry(i,data.getHeartbeat()));
            pressureEntries.add(new Entry(i,data.getHeart_pressure()));
            i++;
        }
        if (heartHealthsArr.isEmpty()) {
            beatEntries.add(new Entry(0, 0));
            pressureEntries.add(new Entry(0, 0));
        }
        LineDataSet dataSet = new LineDataSet(beatEntries, "Heart beat");
        LineDataSet dataSet2 = new LineDataSet(pressureEntries, "Heart pressure");
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSet.setColor(Color.BLUE);
        dataSet2.setColor(Color.RED);
        dataSets.add(dataSet);
        dataSets.add(dataSet2);
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        Description description = new Description();
        description.setText("Heart Health Line Chart");
        description.setTextSize(12f);
        lineChart.setDescription(description);
        description.setPosition(800, 770);
        lineChart.invalidate();
    }
    public void createPieChart( String startDateString,String finishDateString){
        ArrayList<StatisticStatus> result = new ArrayList<>();
        result.addAll(statisticDAO.getHeartHealthPercentStatus(startDateString,finishDateString));
        HashMap<String, Integer> labelColors = new HashMap<>();
        labelColors.put("Low", Color.BLUE);
        labelColors.put("Normal", Color.GREEN);
        labelColors.put("High", Color.YELLOW);
        labelColors.put("Hypertension",Color.RED);
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<PieEntry> entries = new ArrayList<>();

        if(result.isEmpty()){
            Toast.makeText(this, "No Data Available", Toast.LENGTH_SHORT).show();
        }
        else{
            for (StatisticStatus data: result) {
                entries.add(new PieEntry(data.getPercent(),data.getStatus()));
                labels.add(data.getStatus());
                if (labelColors.containsKey(data.getStatus())) {
                    colors.add(labelColors.get(data.getStatus()));
                } else {
                    colors.add(Color.GRAY);
                }
            }
        }
        // Tạo PieDataSet cho dữ liệu
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);
        // Tạo PieData từ PieDataSet
        PieData data = new PieData(dataSet);

        // Cài đặt các thuộc tính cho PieChart
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.animateY(1000);
        pieChart.getLegend().setEnabled(false);
        dataSet.setDrawValues(false);
        // Đặt dữ liệu vào PieChart
        pieChart.setData(data);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }
    public void showDatePicker(){
        Calendar startDateCalendar = Calendar.getInstance();
        Calendar endDateCalendar = Calendar.getInstance();
        DatePickerDialog startDatePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            startDateCalendar.set(year, month, dayOfMonth);
            DatePickerDialog endDatePickerDialog = new DatePickerDialog(this, (view2, year2, month2, dayOfMonth2) -> {
                endDateCalendar.set(year2, month2, dayOfMonth2);

                if (endDateCalendar.getTimeInMillis() >= startDateCalendar.getTimeInMillis()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String startDateString = dateFormat.format(startDateCalendar.getTime());
                    String finishDateString = dateFormat.format(endDateCalendar.getTime());
                    txtStart.setText(startDateString);
                    txtFinish.setText(finishDateString);
                    createLineChart(startDateString,finishDateString);
                    createPieChart(startDateString,finishDateString);
                } else {
                    Toast.makeText(this,"Invalid period",Toast.LENGTH_LONG).show();
                }

            }, startDateCalendar.get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH));
            endDatePickerDialog.show();

        }, startDateCalendar.get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH));
        startDatePickerDialog.show();
    }
    public void back(View v){
        finish();
    }
}