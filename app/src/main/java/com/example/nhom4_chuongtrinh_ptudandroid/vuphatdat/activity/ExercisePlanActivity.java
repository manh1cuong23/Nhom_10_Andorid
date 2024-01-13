package com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.adapter.RecyclerDataAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.dao.ExercisePlanDAO;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model.ExercisePlan;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model.VerticalSpacingItemDecorator;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.validate.Validate;
public class ExercisePlanActivity extends AppCompatActivity implements RecyclerDataAdapter.OnExercisePlanListener {
    RecyclerView recyclerView;
    FloatingActionButton btnAdd;
    ExercisePlanDAO exercisePlanDao;
    List<ExercisePlan> exercisePlans;
    RecyclerDataAdapter recyclerDataAdapter;
    Toolbar toolbar;
    Calendar cal ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpd_activity_exercise_plan);
        getWidget();
        exercisePlanDao = new ExercisePlanDAO(this);
        exercisePlans = exercisePlanDao.getAllExercisePlan();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerDataAdapter = new RecyclerDataAdapter(exercisePlans, this);
        recyclerView.setAdapter(recyclerDataAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExercisePlanActivity.this);
                builder.setTitle("Create new exercise plan");
                View dialogView = getLayoutInflater().inflate(R.layout.vpd_add_new_exercise_plan, null);
                EditText exerName = dialogView.findViewById(R.id.editNames);
                EditText exerType = dialogView.findViewById(R.id.editTypes);
                TextView exerStart = dialogView.findViewById(R.id.txtStartTime);
                TextView exerFinish = dialogView.findViewById(R.id.txtFinishTime);
                EditText exerGoal = dialogView.findViewById(R.id.editGoal);
                ImageButton btnStart = dialogView.findViewById(R.id.btnStart);
                ImageButton btnFinish = dialogView.findViewById(R.id.btnFinish);
                getDefaultInfor(exerStart);
                getDefaultInfor(exerFinish);
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog(exerStart);
                    }
                });
                btnFinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog(exerFinish);
                    }
                });
                builder.setView(dialogView);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {;

                        String name = exerName.getText().toString();
                        String type = exerType.getText().toString();
                        String start = exerStart.getText().toString();
                        String finish = exerFinish.getText().toString();
                        String goal = exerGoal.getText().toString();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        int flag = 1;
                        ExercisePlan exercisePlan = new ExercisePlan(name, type, start, finish,0,"Incomplete", formatter.format(date),Integer.parseInt(goal) ,0);
                        if (Integer.parseInt(goal) <= 0 || !Validate.containDigits(name) || !Validate.containsSpecialCharacters(name)
                                || !Validate.containDigits(type) || !Validate.containsSpecialCharacters(type)
                                || !Validate.validateDateFormat(start) || !Validate.validateDateFormat(finish)
                                || !Validate.isDate1GreaterThanDate2(start,formatter.format(date))
                                || !Validate.isDate1GreaterThanDate2(finish, start)
                                || "".equals(name) || "".equals(type) || "".equals(start) || "".equals(finish) || "".equals(goal)
                        ) {

                            Toast.makeText(ExercisePlanActivity.this,"Not in the correct format", Toast.LENGTH_LONG).show();
                            flag = 0;
                        }
                        if(flag == 1){
                            exercisePlanDao.addExercisePlan(exercisePlan);
                            exercisePlans.clear();
                            exercisePlans.addAll(exercisePlanDao.getAllExercisePlan());
                            recyclerDataAdapter.notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                // Hiển thị AlertDialog
                builder.create().show();
            }
        });
    }
    public void getWidget(){
        btnAdd = findViewById(R.id.btnAdd);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSup){
            AlertDialog.Builder builder = new AlertDialog.Builder(ExercisePlanActivity.this);
            builder.setTitle("Support");
            View dialogView = getLayoutInflater().inflate(R.layout.vpd_support, null);
            builder.setView(dialogView);
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onExercisePlanClick(int position) {
        ExercisePlan exercisePlan= exercisePlanDao.getExercisePlan(exercisePlans.get(position).getId());

        AlertDialog.Builder builder = new AlertDialog.Builder(ExercisePlanActivity.this);
        builder.setTitle("Edit exercise: " + exercisePlan.getName() + " "+ exercisePlan.getScore());
        View dialogView2 = getLayoutInflater().inflate(R.layout.vpd_edit_exercise_plan, null);
        EditText exerName = dialogView2.findViewById(R.id.editNamess);
        EditText exerType = dialogView2.findViewById(R.id.editTypess);
        TextView exerStart = dialogView2.findViewById(R.id.txtStartTimes);
        TextView exerFinish = dialogView2.findViewById(R.id.txtFinishTimes);
        EditText exerGoal = dialogView2.findViewById(R.id.editGoalss);
        EditText exerScore = dialogView2.findViewById(R.id.editScoress);
        ImageButton btnStarts = dialogView2.findViewById(R.id.btnStarts);
        ImageButton btnFinishs = dialogView2.findViewById(R.id.btnFinishs);
        exerName.setText(exercisePlan.getName());
        exerType.setText(exercisePlan.getType());
        exerStart.setText(exercisePlan.getStartTime());
        exerFinish.setText(exercisePlan.getEndTime());
        exerGoal.setText(exercisePlan.getGoal()+"");
        exerScore.setText("0");
        btnStarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                showDatePickerDialog(exerStart);
            }
        });
        btnFinishs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                showDatePickerDialog(exerFinish);
            }
        });
        builder.setView(dialogView2);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String name = exerName.getText().toString();
                String type = exerType.getText().toString();
                String start = exerStart.getText().toString();
                String finish = exerFinish.getText().toString();
                String goal = exerGoal.getText().toString();
                String score = exerScore.getText().toString();

                double progress = 0.0;
                String status = "Incomplete";
                if(Integer.parseInt(score) > 0){
                    progress += ((Double.parseDouble(score) +(double)exercisePlan.getScore())/ Integer.parseInt(goal))*100;
                }
                if(progress >100){
                    progress = 100;
                }
                if(progress == 100){
                    status = "Complete";

                }
                exercisePlan.setName(name);
                exercisePlan.setType(type);
                exercisePlan.setStartTime(start);
                exercisePlan.setEndTime(finish);
                exercisePlan.setGoal(Integer.parseInt(goal));
                exercisePlan.setScore(Integer.parseInt(score) + exercisePlan.getScore());
                exercisePlan.setStatus(status);
                exercisePlan.setProgress((int) progress);
                exercisePlanDao.updateExercisePlan(exercisePlan);
                exercisePlans.clear();
                exercisePlans.addAll(exercisePlanDao.getAllExercisePlan());
                recyclerDataAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // Hiển thị AlertDialog
        builder.create().show();
    }

    @Override
    public boolean onLongExercisePlanClick(int position) {
        if (position > -1) {
            ExercisePlan exercisePlan= exercisePlanDao.getExercisePlan(exercisePlans.get(position).getId());
            exercisePlans.remove(position);
            recyclerDataAdapter.notifyDataSetChanged();
            exercisePlanDao.deleteExercisePlan(exercisePlan.getId());
            return true;
        }
        return false;
    }

    private void getDefaultInfor(TextView textView) {
        cal= Calendar.getInstance();
        SimpleDateFormat dft=null;
        dft=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        textView.setText(strDate);

    }
    public void showDatePickerDialog(TextView textView)
    {
        DatePickerDialog.OnDateSetListener callback=
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear,
                                          int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                        textView.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        //Lưu vết lại biến ngày hoàn thành
                        cal.set(year, monthOfYear, dayOfMonth);
                    }
                };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=textView.getText()+"";
        String strArrtmp[]=s.split("-");
        int ngay=Integer.parseInt(strArrtmp[2]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[0]);
        DatePickerDialog pic=new DatePickerDialog(
                ExercisePlanActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Select finish date");
        pic.show();
    }
    public void back(View v){
        finish();
    }
}
