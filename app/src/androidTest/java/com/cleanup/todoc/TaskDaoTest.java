package com.cleanup.todoc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.model.Database;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    @Rule
    // Rule to execute tasks synchronously
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    // Get reference to the ToDoc database
    private Database database;
    // Get the list of static initial projects from the model Project class
    private final Project[] projects = Project.getAllProjects();
    private final Task[] tasks = {
            new Task(projects[0].getId(), "Passer l'aspirateur", 1424424),
            new Task(projects[1].getId(), "Tondre la pelouse", 4201101),
            new Task(projects[2].getId(), "Tailler les haies", 7831144),
            new Task(projects[0].getId(), "Ranger le linge", 1032154),
    };

    @Before
    public void initDatabase(){
        database = Room.inMemoryDatabaseBuilder(
                        InstrumentationRegistry.getContext(),
                        Database.class
                )
                .allowMainThreadQueries() //- autorise accès thread principal pour les tests
                .build();
    }

    @Test
    public void insertGetAndDeleteTasks() throws InterruptedException {

        database.getProjectDao().insertProjects(this.projects);

        List<Task> tasks = LiveDataTestUtils.getOrAwaitValue(database.getTaskDao().getTasks());

        assertTrue(tasks.isEmpty());

        database.getTaskDao().insertTasks(this.tasks);

        tasks =  LiveDataTestUtils.getOrAwaitValue(database.getTaskDao().getTasks());

        assertFalse(tasks.isEmpty());

        for (Task task : tasks){
            database.getTaskDao().deleteTask(task);
        }

        tasks =  LiveDataTestUtils.getOrAwaitValue(database.getTaskDao().getTasks());

        assertTrue(tasks.isEmpty());
    }

    @Test
    public void checkIfProjectIdMatchForeignKey() throws InterruptedException {

        List<Task> tasks =  LiveDataTestUtils.getOrAwaitValue(database.getTaskDao().getTasks());

        Set<Long> projectIds = new HashSet<>();
        for (Project project : projects) {
            projectIds.add(project.getId());
        }

        for (Task task : tasks){
            assertTrue(projectIds.contains(task.getProjectId()));
        }
    }



    @After
    public void closeDatabase(){
        database.close();
    }
}
