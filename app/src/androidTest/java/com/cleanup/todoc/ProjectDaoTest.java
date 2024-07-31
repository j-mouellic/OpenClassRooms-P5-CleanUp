package com.cleanup.todoc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.model.Database;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import java.util.List;


@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {
    @Rule
    // Rule to execute tasks synchronously
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    // Get reference to the ToDoc database
    private Database database;
    // Get the list of static initial projects from the model Project class
    private final Project[] projects = Project.getAllProjects();

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
    public void insertAndGetProjects() throws InterruptedException {
        List<Project> projects = LiveDataTestUtils.getOrAwaitValue(database.getProjectDao().getProjects());

        assertTrue(projects.isEmpty());

        database.getProjectDao().insertProjects(this.projects);

        projects =  LiveDataTestUtils.getOrAwaitValue(database.getProjectDao().getProjects());

        assertFalse(projects.isEmpty());
    }

    @After
    public void closeDatabase(){
        database.close();
    }

}
