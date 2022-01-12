package com.example.calender.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.calender.models.Event;
import com.example.calender.models.EventGuest;
import com.example.calender.models.Time;

@Database(entities = {Time.class, Event.class, EventGuest.class},version = 1,exportSchema = false)
public abstract class  DataBase extends RoomDatabase {

    private static  DataBase database;
    private static String DatabaseName = "CalendarDB";

    public synchronized  static  DataBase getInstance(Context context){
        if (database == null) {

            database = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, DatabaseName)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract MDao dao();

}
