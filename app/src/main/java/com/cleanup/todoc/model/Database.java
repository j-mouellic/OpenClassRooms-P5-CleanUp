package com.cleanup.todoc.model;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;


@androidx.room.Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    // --- DAO ---
    public abstract ProjectDao getProjectDao();
    public abstract TaskDao getTaskDao();


    // --- SINGLETON ---
    private static volatile Database instance;

    /**
     * Returns the singleton instance of the {@link Database}.
     * @param context The application context.
     */
    public static synchronized Database getInstance(Context context){
        if (instance == null){
            Log.i("DEBUG", "🟢🟢🟢 getInstance ");
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Database.class,
                    "app_db"
            ).addCallback(prepopulateDataBase())
                    .allowMainThreadQueries() //- autorise accès thread principal pour les tests
                    .build();
        }
        Log.i("DEBUG", "🟢🟢🟢 return ");
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
                Log.i("DEBUG", "🟢🟢🟢 Prepopulate ");

                Project[] projects = Project.getAllProjects();

                for (Project project : projects){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", project.getId());
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());

                    Log.i("DEBUG", "🟢🟢🟢 Inserting project: " + project.getName());

                    // OnConflictStrategy.IGNORE - Use to ignore insert if contentvalues is already in DB
                    db.insert("projects", OnConflictStrategy.IGNORE, contentValues);
                }
            }
        };
    }
}
