package com.cleanup.todoc.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.model.repository.TaskRepostory;
import com.cleanup.todoc.viewmodel.TaskViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)){

            TaskRepostory repository = TaskRepostory.getInstance();
            return (T) new TaskViewModel(repository);
        }
        throw new IllegalArgumentException("Classe de ViewModel inconnue : " + modelClass.getName());
    }
}
