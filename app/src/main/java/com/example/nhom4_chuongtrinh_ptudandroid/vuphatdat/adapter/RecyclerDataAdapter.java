package com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.nhom4_chuongtrinh_ptudandroid.R;
import com.example.nhom4_chuongtrinh_ptudandroid.vuphatdat.model.ExercisePlan;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.DataViewHolder> {
    private List<ExercisePlan> exercisePlans;
    private OnExercisePlanListener onExercisePlanListener;

    public RecyclerDataAdapter(List<ExercisePlan> exercisePlans, OnExercisePlanListener onExercisePlanListener) {
        this.onExercisePlanListener = onExercisePlanListener;
        this.exercisePlans = exercisePlans;

    }

    @Override
    public int getItemCount(){
        return exercisePlans == null ? 0 : exercisePlans.size();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vpd_items_exercise_plan, parent, false);;
        return new DataViewHolder(itemView, onExercisePlanListener);
    }
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position){
        String name = exercisePlans.get(position).getName();
        holder.txtName.setText(name);
        String type = exercisePlans.get(position).getType();
        holder.txtType.setText(type);
        String goal = String.valueOf(exercisePlans.get(position).getGoal());
        holder.txtGoal.setText(goal);
        String score = String.valueOf(exercisePlans.get(position).getScore());
        holder.txtScore.setText(score);
        String progress = String.valueOf(exercisePlans.get(position).getProgress());
        holder.txtProgress.setText(progress+"%");
        String start = exercisePlans.get(position).getStartTime();
        holder.txtStart.setText(start);
        String finish = exercisePlans.get(position).getEndTime();
        holder.txtFinish.setText(finish);
        String status = exercisePlans.get(position).getStatus();
        holder.txtStatus.setText(status);
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,View.OnLongClickListener{
        private TextView txtName, txtType, txtProgress, txtStart, txtFinish, txtGoal, txtScore, txtStatus;
        OnExercisePlanListener offExercisePlanListener;
        public DataViewHolder(@NonNull View itemView, OnExercisePlanListener onExercisePlanListener) {
            super(itemView);

            txtName = itemView.findViewById(R.id.textName);
            txtType = itemView.findViewById(R.id.txtType);
            txtStart = itemView.findViewById(R.id.txtStart);
            txtProgress = itemView.findViewById(R.id.txtProgress);
            txtGoal = itemView.findViewById(R.id.txtGoal);
            txtFinish = itemView.findViewById(R.id.txtFinish);
            txtScore = itemView.findViewById(R.id.txtScore);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            offExercisePlanListener = onExercisePlanListener;
        }

        @Override
        public void onClick(View v) {
            offExercisePlanListener.onExercisePlanClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            //return offExercisePlanListener.onLongExercisePlanClick(getBindingAdapterPosition());
            return offExercisePlanListener.onLongExercisePlanClick(getAdapterPosition());
        }
    }
    public interface OnExercisePlanListener{
        void onExercisePlanClick(int position);
        boolean onLongExercisePlanClick(int position);
    }
}
