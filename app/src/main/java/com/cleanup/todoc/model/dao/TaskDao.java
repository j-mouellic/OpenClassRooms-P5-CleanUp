package com.cleanup.todoc.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    /**
     * Inserts a new task into the database.
     * @param task The task to be inserted.
     */
    @Insert
    void insertTask(Task task);

    /**
     * Deletes a task from the database.
     * @param task The task to be deleted.
     */
    @Delete
    void deleteTask(Task task);

    /**
     * Updates a task in the database.
     * @param task The task to be updated.
     */
    @Update
    void updateTask(Task task);

    /**
     * Retrieves all tasks from the database.
     * @return A list of all tasks.
     */
    @Query("SELECT * FROM tasks")
    List<Task> getTasks();
}
