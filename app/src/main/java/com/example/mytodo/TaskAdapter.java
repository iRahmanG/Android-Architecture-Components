package com.example.mytodo;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodo.databinding.ItemTaskBinding;  // Correct import based on item_task.xml
// Task import is already there from your previous code

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList = new ArrayList<>();
    private OnTaskCheckedChangeListener listener;
    private OnItemClickListener itemClickListener;

    public interface OnTaskCheckedChangeListener {
        void onTaskChecked(Task task, boolean isChecked);
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }
    public void setOnTaskCheckedChangeListener(OnTaskCheckedChangeListener listener) {
        this.listener = listener;
    }

    public void setTasks(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged(); // Consider using DiffUtil for better performance
    }
    // setter for OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public void seTask(List<Task> task) {
        this.taskList = task;
        notifyDataSetChanged();
    }

    // Method to get task at a specific position
    public Task getTaskAt(int position) {
        if (position >= 0 && position < taskList.size()) {
            return taskList.get(position);
        }
        return null; // Or throw an exception
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTaskBinding binding = ItemTaskBinding.inflate(inflater, parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = taskList.get(position);

        holder.binding.textViewTaskTitle.setText(currentTask.getTitle());

        // Avoid triggering listener when recycling views or initially setting checked state
        holder.binding.checkBoxTask.setOnCheckedChangeListener(null);
        holder.binding.checkBoxTask.setChecked(currentTask.isCompleted());

        if (currentTask.isCompleted()) {
            holder.binding.textViewTaskTitle.setPaintFlags(
                    holder.binding.textViewTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.binding.textViewTaskTitle.setPaintFlags(
                    holder.binding.textViewTaskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // Set listener for checkbox
        holder.binding.checkBoxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                // Update the task's completed status immediately for UI responsiveness
                // The actual database update will happen via the listener in MainActivity
                currentTask.setCompleted(isChecked); // Update local item state
                listener.onTaskChecked(currentTask, isChecked); // Notify MainActivity to update DB
                // Optionally, re-apply paint flags here if you want instant visual feedback
                // before the list is re-submitted by LiveData
                if (isChecked) {
                    holder.binding.textViewTaskTitle.setPaintFlags(
                            holder.binding.textViewTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.binding.textViewTaskTitle.setPaintFlags(
                            holder.binding.textViewTaskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
        // Set click listener for the whole item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(currentTask);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        ItemTaskBinding binding;

        public TaskViewHolder(ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}