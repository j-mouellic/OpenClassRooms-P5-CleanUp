package com.cleanup.todoc.di;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.model.Database;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;
import com.cleanup.todoc.viewmodel.TaskViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// 1. Executor

// 2. Repository

// 3. Dao

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Executor executor;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepostory;
    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance(Context context){
        if (factory == null){
            synchronized (ViewModelFactory.class){
                if (factory == null){
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }

    private ViewModelFactory(Context context) {
        Database db = Database.getInstance(context);
        this.projectRepository = ProjectRepository.getInstance(db.getProjectDao());
        this.taskRepostory = TaskRepository.getInstance(db.getTaskDao());
        this.executor = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)){
            return (T) new TaskViewModel(taskRepostory, projectRepository, executor);
        }
        throw new IllegalArgumentException("Classe de ViewModel inconnue : " + modelClass.getName());
    }
}
