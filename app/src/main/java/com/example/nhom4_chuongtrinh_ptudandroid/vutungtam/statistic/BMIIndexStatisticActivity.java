package com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.statistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.lequydo.model.BMIIndex;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.dao.StatisticDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.vutungtam.model.StatisticStatus;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BMIIndexStatisticActivity extends AppCompatActivity {
    private BarChart barChart;
    private PieChart pieChart;
    private StatisticDAO statisticDAO ;
    ImageButton btnShowDatePicker,btnBack;
    TextView txtStart, txtFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vtt_activity_bmiindex_statistic);
        mapping();
        statisticDAO = new StatisticDAO(this);
        createBarChart(null,null);
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
        barChart= findViewById(R.id.barChart);
        pieChart = findViewById(R.id.pieChart);
    }
    public void createBarChart(String startDateString,String finishDateString){
        ArrayList<BarEntry> BMIBarEntries = new ArrayList<>();
        ArrayList<BarEntry> weightBarEntries = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<BMIIndex> BMI_indexes = statisticDAO.getBMIData(startDateString,finishDateString);
        int i  = 1;
        for (BMIIndex data: BMI_indexes) {
            BMIBarEntries.add(new BarEntry(i,data.getResult()));
            weightBarEntries.add(new BarEntry(i,data.getWeight()));
            dates.add(data.getCreated_date());
        }
        BarDataSet barDataSet1 = new BarDataSet(BMIBarEntries,"BMI");
        barDataSet1.setColors(Color.RED);
        BarDataSet barDataSet2 = new BarDataSet(weightBarEntries,"Weigh");
        barDataSet2.setColors(Color.BLUE);
        BarData data = new BarData(barDataSet1,barDataSet2);
        barChart.setData(data);
        String[] days = convertDates(dates); // Chuyển đổi danh sách ngày thành mảng
        int numGroups = days.length; // Số lượng nhóm cột dựa trên số lượng ngày
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true); // Căn giữa các mục trên trục x
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);

        float groupSpace = 0.55f; // Khoảng trống giữa các nhóm cột
        float barSpace = 0.08f; // Khoảng trống giữa các cột trong cùng một nhóm
        float barWidth = 0.15f; // Chiều rộng của mỗi cột

        data.setBarWidth(barWidth);

        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*numGroups);
        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.groupBars(0, groupSpace, barSpace);
        barChart.setFitBars(true);
        barChart.invalidate();
    }
    public void createPieChart( String startDateString,String finishDateString){
        ArrayList<StatisticStatus> result = new ArrayList<>();
        result.addAll(statisticDAO.getBMIPercentStatus(startDateString,finishDateString));
        HashMap<String, Integer> labelColors = new HashMap<>();
        labelColors.put("Malnutrition", Color.BLUE);
        labelColors.put("Underweight", Color.YELLOW);
        labelColors.put("Normal", Color.GREEN);
        labelColors.put("Overweight", Color.MAGENTA);
        labelColors.put("Fat",Color.RED);
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
        pieChart.clear();
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
                    createBarChart(startDateString,finishDateString);
                    createPieChart(startDateString,finishDateString);

                } else {
                    Toast.makeText(this,"Invalid period",Toast.LENGTH_LONG).show();
                }

            }, startDateCalendar.get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH));
            endDatePickerDialog.show();

        }, startDateCalendar.get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH));
        startDatePickerDialog.show();
    }
    public static String[] convertDates(List<String> dates) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM");

        List<String> formattedDates = new ArrayList<>();

        for (String date : dates) {
            try {
                Date parsedDate = inputFormat.parse(date);
                String formattedDate = outputFormat.format(parsedDate);
                formattedDates.add(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return formattedDates.toArray(new String[0]);
    }
    public void back(View v){
        finish();
    }
}