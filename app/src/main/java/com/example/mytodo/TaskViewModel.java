package com.example.mytodo;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;

    // Constructor that AndroidViewModelFactory (default factory for AndroidViewModel) can use.
    public TaskViewModel(@NonNull Application application) {
        super(application);
        // Initialize the repository here, passing the application context.
        // This assumes TaskRepository has a constructor that takes an Application.
        repository = new TaskRepository(application);
        // Initialize allTasks by getting them from the repository.
        allTasks = repository.getAllTasks();
    }

    public void insert(Task task) {

        repository.insert(task);
    }

    // Method to update a task (you'll likely need this)
    public void update(Task task) {

        repository.update(task);
    }

    // Method to delete a task (you'll likely need this)
    public void delete(Task task) {

        repository.delete(task);
    }

    // Method to get all tasks as LiveData
    public LiveData<List<Task>> getAllTasks() {

        return allTasks;
    }

    // Optional: If you need to delete all tasks
    // public void deleteAllTasks() {
    //     repository.deleteAllTasks();
    // }
}