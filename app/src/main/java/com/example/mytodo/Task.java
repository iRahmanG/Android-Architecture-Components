package com.example.mytodo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private boolean isCompleted;

    // No-argument constructor (Good practice for Room, though not always strictly required)
    public Task() {
    }

    // Constructor to create a new task with a title
    public Task(String title) { // Changed parameter name to 'title' for clarity, or keep 'taskText'
        this.title = title;     // Corrected: Use the constructor parameter
        this.isCompleted = false;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    // Setters
    public void setId(int id) {

        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) { // Parameter name changed for consistency

        isCompleted = completed;
    }
}