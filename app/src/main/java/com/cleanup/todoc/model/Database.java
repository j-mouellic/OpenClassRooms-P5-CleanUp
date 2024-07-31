package com.cleanup.todoc.model;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;

import java.util.concurrent.atomic.AtomicBoolean;


@androidx.room.Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    // --- DAO ---
    public abstract ProjectDao getProjectDao();
    public abstract TaskDao getTaskDao();
    private static AtomicBoolean isRunningTest;


    // --- SINGLETON ---
    private static volatile Database instance;

    /**
     * Returns the singleton instance of the {@link Database}.
     * @param context The application context.
     */
    public static synchronized Database getInstance(Context context){
        if (instance == null){
            if (isRunningTest()){
                instance = Room.inMemoryDatabaseBuilder(
                                context.getApplicationContext(),
                                Database.class
                        ).addCallback(prepopulateDataBase())
                        .allowMainThreadQueries() //- autorise accès thread principal pour les tests
                        .build();
            }else{
                instance = Room.databaseBuilder(
                                context.getApplicationContext(),
                                Database.class,
                                "app_db"
                        ).addCallback(prepopulateDataBase())
                        .build();
            }
        }
        return instance;
    }


    /**
     * Returns a RoomDatabase.Callback object used to prepopulate the database with initial data.
     * This callback will be triggered when the database is created for the first time.
     * It inserts three predefined projects into the database asynchronously using a single-thread executor.
     *
     * @return A RoomDatabase.Callback object for prepopulating the database.
     */
    public static Callback prepopulateDataBase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Project[] projects = Project.getAllProjects();

                for (Project project : projects){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", project.getId());
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());

                    // OnConflictStrategy.IGNORE - Use to ignore insert if contentvalues is already in DB
                    db.insert("projects", OnConflictStrategy.IGNORE, contentValues);
                }
            }
        };
    }

    public static synchronized boolean isRunningTest() {
        // do some caching to avoid checking every time
        if (null == isRunningTest) {
            boolean istest;

            try {
                // androidx only
                Class.forName ("androidx.test.espresso.Espresso");
                istest = true;
            } catch (ClassNotFoundException e) {
                istest = false;
            }

            isRunningTest = new AtomicBoolean(istest);
        }

        return isRunningTest.get();
    }
}
