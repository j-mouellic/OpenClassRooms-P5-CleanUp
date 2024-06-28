package com.cleanup.todoc.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    /**
     * Inserts a new project into the database.
     * @param project The project to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProjects(Project... project);


    /**
     * Retrieves all projects from the database.
     * @return list of all projects.
     */
    @Query("SELECT * FROM projects")
    LiveData<List<Project>> getProjects();
}
