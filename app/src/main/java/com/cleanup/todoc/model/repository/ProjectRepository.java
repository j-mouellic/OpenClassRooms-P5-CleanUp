package com.cleanup.todoc.model.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Database;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectRepository {

    private static ProjectRepository instance;
    private final ProjectDao dao;

    private ProjectRepository(ProjectDao dao) {
        this.dao = dao;
    }

    public static ProjectRepository getInstance(ProjectDao dao) {
        if (instance == null) {
            instance = new ProjectRepository(dao);
        }
        return instance;
    }

    public LiveData<List<Project>> getProjects() {
        return dao.getProjects();
    }
}
