package com.cleanup.todoc.model.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.dao.TaskDao;

import java.util.List;

public class TaskRepository {
    private static TaskRepository instance;
    private final TaskDao dao;

    private TaskRepository(TaskDao dao) {
        this.dao = dao;
    }

    public static TaskRepository getInstance(TaskDao dao) {
        if (instance == null) {
            instance = new TaskRepository(dao);
        }
        return instance;
    }

    public void insertTask(Task task) {
        dao.insertTasks(task);
    }

    public void deleteTask(Task task) {
        dao.deleteTask(task);
    }

    public LiveData<List<Task>> getTasks() {
        return this.dao.getTasks();
    }
}
