package com.cleanup.todoc.di;

import android.content.Context;

import com.cleanup.todoc.model.Database;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;
import com.cleanup.todoc.viewmodel.ViewModelFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Provides dependency injection for the application.
 * This class is responsible for creating and providing instances of various
 * components needed throughout the application.
 */
public class Injection {

    /**
     * Provides an instance of ViewModelFactory.
     *
     * @param context the context used to create the necessary dependencies
     * @return an instance of ViewModelFactory
     */
    public static ViewModelFactory provideViewModelFactory(Context context) {
        ProjectRepository projectSource = provideProjectSource(context);
        TaskRepository taskSource = provideTaskSource(context);

        Executor executor = provideExecutor();

        return new ViewModelFactory(projectSource, taskSource, executor);
    }

    /**
     * Provides a single-threaded Executor.
     *
     * @return an instance of Executor
     */
    private static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * Provides an instance of TaskRepository.
     *
     * @param context the context used to create the database instance
     * @return an instance of TaskRepository
     */
    private static TaskRepository provideTaskSource(Context context) {
        Database db = Database.getInstance(context);
        return TaskRepository.getInstance(db.getTaskDao());
    }


    /**
     * Provides an instance of ProjectRepository.
     *
     * @param context the context used to create the database instance
     * @return an instance of ProjectRepository
     */
    private static ProjectRepository provideProjectSource(Context context) {
        Database db = Database.getInstance(context);
        return ProjectRepository.getInstance(db.getProjectDao());
    }
}
