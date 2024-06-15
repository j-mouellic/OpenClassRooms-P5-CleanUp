package com.cleanup.todoc.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
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
    @Insert
    void insertProject(Project project);

    /**
     * Deletes a project from the database.
     * @param project The project to be deleted.
     */
    @Delete
    void deleteProject(Project project);

    /**
     * Updates a project in the database.
     * @param project The project to be updated.
     */
    @Update
    void updateProject(Project project);

    /**
     * Retrieves all projects from the database.
     * @return A list of all projects.
     */
    @Query("SELECT * FROM projects")
    List<Project> getProjects();
}
