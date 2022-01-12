package com.example.calender.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.calender.models.Event;
import com.example.calender.models.EventGuest;
import com.example.calender.models.Time;

import java.util.List;

@Dao
public interface MDao {

    @Insert
     void insertTime(Time time);

    @Delete
    void deleteTime(Time time);

    @Query("select *from Time")
    LiveData<List<Time>> getTimes();


  @Insert
     void insertEvent(Event event);

    @Delete
    void deleteEvent(Event event);

    @Query("select *from Event")
    LiveData<List<Event>> getEvents();
  @Insert
     void insertEventGuest(EventGuest eventGuest);

    @Delete
    void deleteEventGuest(EventGuest eventGuest);

    @Query("select *from EventGuest")
    LiveData<List<EventGuest>> getEventGuest();



}
