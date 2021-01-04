package com.example.detailsapp.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {DetailsEntry.class},version = 1,exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DataBase_Name = "detailsList";
    private static AppDatabase sInstance;
    public static AppDatabase getInstance(Context context) {
        if(sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating database Instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DataBase_Name)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG, "getting the database instance");
        return sInstance;
    }
    public abstract DetailDao detailDao();
}
