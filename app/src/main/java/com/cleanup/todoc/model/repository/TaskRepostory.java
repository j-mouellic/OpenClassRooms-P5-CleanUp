package com.cleanup.todoc.model.repository;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;

import com.cleanup.todoc.model.Database;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.dao.TaskDao;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepostory {
    private static TaskRepostory instance;
    private final TaskDao dao;
    private final ExecutorService executor;

    private TaskRepostory(Context context){
        Database db = Database.getInstance(context);
        dao = db.getTaskDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public static TaskRepostory getInstance(Context context){
        if (instance == null){
            instance = new TaskRepostory(context);
        }
        return instance;
    }

    public void insertTask(Task task){
        executor.execute(()->{
            dao.insertTask(task);
        });
    }

    public void updateTask(Task task){
        executor.execute(()->{
            dao.updateTask(task);
        });
    }

    public void deleteTask(Task task){
        executor.execute(()->{
            dao.deleteTask(task);
        });
    }

    public List<Task> getTasks(){
        try {
            return executor.submit(dao::getTasks).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
