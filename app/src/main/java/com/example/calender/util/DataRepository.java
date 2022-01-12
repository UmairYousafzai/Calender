package com.example.calender.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.room.Database;

import com.example.calender.database.DataBase;
import com.example.calender.database.MDao;
import com.example.calender.models.Event;
import com.example.calender.models.EventGuest;
import com.example.calender.models.Time;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataRepository {

    private MDao mDao;
    private LiveData<List<Event>> allEvents;
    private LiveData<List<EventGuest>> allEventGuest;
    private LiveData<List<Time>> allTimes;
    private Executor executor= Executors.newSingleThreadExecutor();
    private Handler handler= new Handler(Looper.getMainLooper());


    public DataRepository(Context context) {

        mDao=DataBase.getInstance(context).dao();
        allEventGuest= mDao.getEventGuest();
        allEvents= mDao.getEvents();
        allTimes= mDao.getTimes();

    }

    public void insertEvent(Event event) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.insertEvent(event);
            }
        });
    }

    public void insertEventGuest(EventGuest eventGuest) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.insertEventGuest(eventGuest);
            }
        });
    }

    public void insertTime(Time time) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.insertTime(time);
            }
        });
    }

    public void deleteTime(Time time) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.deleteTime(time);
            }
        });
    }

    public void deleteEvent(Event event) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.deleteEvent(event);
            }
        });
    }

    public void deleteEventGuest(EventGuest eventGuest) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.deleteEventGuest(eventGuest);
            }
        });
    }

    public LiveData<List<Event>> getEvents()
    {
        return allEvents;
    }
  public LiveData<List<EventGuest>> getEventGuests()
    {
        return allEventGuest;
    }
  public LiveData<List<Time>> getTimes()
    {
        return allTimes;
    }


}
