package com.example.mytodo;

import android.content.Context;
import androidx.room.Database; // Import Database
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1, exportSchema = false) // Added this annotation
public abstract class TaskDatabase extends RoomDatabase {

    private static volatile TaskDatabase instance; // Made instance volatile for thread safety

    public abstract TaskDao taskDao();

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            // Use context.getApplicationContext() to avoid potential memory leaks
            // if 'context' is not an Application context.
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration() // Handles schema changes by destroying and recreating the DB
                    // .allowMainThreadQueries() // IMPORTANT: Only for quick testing, remove for production
                    .build();
        }
        return instance;
    }
}