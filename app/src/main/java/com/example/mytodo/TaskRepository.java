package com.example.mytodo;

import android.app.Application;
import android.os.AsyncTask;
// import android.provider.ContactsContract; // Unused import

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public void insert(Task task) {

        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    public void update(Task task) {

        new UpdateTaskAsyncTask(taskDao).execute(task);
    }

    public void delete(Task task) {

        new DeleteTaskAsyncTask(taskDao).execute(task);
    }

    // Optional: If you need to delete all tasks
    // public void deleteAllTasks() {
    //     new DeleteAllTasksAsyncTask(taskDao).execute();
    // }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    // AsyncTask for Insert operation
    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    // AsyncTask for Update operation
    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private UpdateTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

    // AsyncTask for Delete operation
    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }

    // Optional: AsyncTask for Delete All Tasks operation
    // private static class DeleteAllTasksAsyncTask extends AsyncTask<Void, Void, Void> {
    //     private TaskDao taskDao;
    //     private DeleteAllTasksAsyncTask(TaskDao taskDao) { this.taskDao = taskDao; }
    //     @Override
    //     protected Void doInBackground(Void... voids) {
    //         taskDao.deleteAllTasks(); // Requires deleteAllTasks() in TaskDao
    //         return null;
    //     }
    // }
}