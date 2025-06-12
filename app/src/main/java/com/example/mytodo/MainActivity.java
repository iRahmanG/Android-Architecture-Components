package com.example.mytodo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodo.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.topAppBar);

        binding.recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter();
        binding.recyclerViewTasks.setAdapter(taskAdapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.setTasks(tasks);
            }
        });

        // Listener for task checked changes
        taskAdapter.setOnTaskCheckedChangeListener(new TaskAdapter.OnTaskCheckedChangeListener() {
            @Override
            public void onTaskChecked(Task task, boolean isChecked) {
                // It's often safer to create a new Task object or clone it for updates,
                // especially if the Task object from the adapter might be a different instance
                // than what LiveData/Room expects or if it's heavily managed by the adapter.
                // However, for simple cases, modifying the passed task and ensuring its ID is correct works.
                Task taskToUpdate = new Task(task.getTitle()); // Keep original title or update if needed
                taskToUpdate.setId(task.getId());              // IMPORTANT: Set the ID for Room to find the correct row
                taskToUpdate.setCompleted(isChecked);          // Set new completed status
                taskViewModel.update(taskToUpdate);
            }
        });

        // Listener for item clicks (for editing task title)
        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                showUpdateTaskDialog(task); // Call method to show update dialog
            }
        });

        binding.btnAddTask.setOnClickListener(v -> showAddTaskDialog());

        // Setup ItemTouchHelper for swipe-to-delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // Not used for swipe-only
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                Task taskToDelete = taskAdapter.getTaskAt(position);

                if (taskToDelete != null) {
                    taskViewModel.delete(taskToDelete);
                    Snackbar.make(binding.recyclerViewTasks, "Task deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", v -> taskViewModel.insert(taskToDelete))
                            .show();
                }
            }
        }).attachToRecyclerView(binding.recyclerViewTasks);


        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);
        final EditText editTextTaskDialog = dialogView.findViewById(R.id.editTextTask);

        builder.setView(dialogView)
                .setTitle("Add New Task")
                .setPositiveButton("Add", (dialog, which) -> {
                    String taskText = editTextTaskDialog.getText().toString().trim();
                    if (!taskText.isEmpty()) {
                        Task newTask = new Task(taskText);
                        taskViewModel.insert(newTask);
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    // Method to show dialog for updating an existing task
    private void showUpdateTaskDialog(final Task taskToUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null); // Reuse the same dialog layout
        final EditText editTextTaskDialog = dialogView.findViewById(R.id.editTextTask);

        editTextTaskDialog.setText(taskToUpdate.getTitle()); // Pre-fill with current title

        builder.setView(dialogView)
                .setTitle("Update Task")
                .setPositiveButton("Update", (dialog, which) -> {
                    String updatedTaskText = editTextTaskDialog.getText().toString().trim();
                    if (!updatedTaskText.isEmpty()) {
                        // Create a new Task object for the update to ensure correct state
                        Task updatedTask = new Task(updatedTaskText);
                        updatedTask.setId(taskToUpdate.getId()); // IMPORTANT: Keep the original ID
                        updatedTask.setCompleted(taskToUpdate.isCompleted()); // Keep original completed status
                        taskViewModel.update(updatedTask);
                    } else {
                        Toast.makeText(MainActivity.this, "Task title cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}