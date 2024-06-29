package com.cleanup.todoc.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final Executor executor;

    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.executor = executor;
    }

    /**
     * Returns a LiveData object containing a list of all projects.
     * @return LiveData object with list of projects
     */
    public LiveData<List<Project>> getProjects(){
        return projectRepository.getProjects();
    }

    /**
     * Returns a LiveData object containing a list of all tasks.
     * @return LiveData object with list of tasks
     */
    public LiveData<List<Task>> getTasks() {
        return taskRepository.getTasks();
    }

    /**
     * Adds a new task to the repository.
     * @param projectId the ID of the project the task belongs to
     * @param name the name of the task
     */
    public void addTask(long projectId, String name) {
        executor.execute(() -> {
            Date date = new Date();
            long creationTimestamp = date.getTime();
            Task newTask = new Task(projectId, name, creationTimestamp);
            taskRepository.insertTask(newTask);
        });
    }

    /**
     * Deletes the specified task from the repository.
     * @param task the task to be deleted
     */
    public void deleteTask(Task task) {
        executor.execute(() -> {
            taskRepository.deleteTask(task);
        });
    }
}
