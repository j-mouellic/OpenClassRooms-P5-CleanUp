package com.cleanup.todoc.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.model.Database;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A factory class for creating instances of ViewModels.
 * This class implements the ViewModelProvider.Factory interface to provide
 * ViewModels with their required dependencies.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Executor executor;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepostory;

    /**
     * Constructs a ViewModelFactory.
     *
     * @param projectRepository the repository for project data
     * @param taskRepository the repository for task data
     * @param executor the executor for background tasks
     */
    public ViewModelFactory(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        this.projectRepository = projectRepository;
        this.taskRepostory = taskRepository;
        this.executor = executor;
    }

    /**
     * Creates a new instance of the given {@code modelClass}.
     *
     * @param modelClass a {@code Class} whose instance is requested
     * @param <T>        The type parameter for the ViewModel
     * @return a newly created ViewModel
     * @throws IllegalArgumentException if the {@code modelClass} is unknown to this factory
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)){
            return (T) new TaskViewModel(taskRepostory, projectRepository, executor);
        }
        throw new IllegalArgumentException("Classe de ViewModel inconnue : " + modelClass.getName());
    }
}
