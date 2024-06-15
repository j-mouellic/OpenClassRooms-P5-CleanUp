package com.cleanup.todoc.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;

@androidx.room.Database(entities = {Project.class, Task.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract ProjectDao getProjectDao();
    public abstract TaskDao getTaskDao();
    private static Database instance;

    /**
     * Returns the singleton instance of the {@link Database}.
     * @param context The application context.
     */
    public static synchronized Database getInstance(Context context){
        if (instance == null){
            Log.i("DEBUG", "🔵🔵🔵 Get Instance");
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Database.class,
                    "app_db"
            )
                    /*.addCallback(Database.populateDb)*/
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    private static final RoomDatabase.Callback populateDb = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i("DEBUG", "🔵🔵🔵 Populate DB");
            long colorValue1 = 0xFFEADAD1;

            String colorHex1 = String.format("#%06X", (0xFFFFFF & colorValue1));

            db.execSQL("INSERT INTO projects (name, color) VALUES ('Projet Tartampion', '"+ colorHex1 +"')");
        }
    };
}
