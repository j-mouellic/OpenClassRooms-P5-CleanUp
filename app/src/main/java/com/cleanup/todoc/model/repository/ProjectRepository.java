package com.cleanup.todoc.model.repository;

import android.app.Application;
import android.content.Context;

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
    private final ExecutorService executor;

    private ProjectRepository(Context context){
        Database db = Database.getInstance(context);
        dao = db.getProjectDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public static ProjectRepository getInstance(Context context){
        if (instance == null){
            instance = new ProjectRepository(context);
        }
        return instance;
    }

    public void insertProject(Project project){
        executor.execute(()->{
            dao.insertProject(project);
        });
    }

    public void updateProject(Project project){
        executor.execute(()->{
            dao.updateProject(project);
        });
    }

    public void deleteProject(Project project){
        executor.execute(()->{
            dao.deleteProject(project);
        });
    }

    public List<Project> getProjects(){
        try {
            return executor.submit(dao::getProjects).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
