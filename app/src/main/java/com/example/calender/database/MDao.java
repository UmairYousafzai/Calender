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
     void insertTime(List<Time> time);

    @Delete
    void deleteTime(Time time);

    @Query("select *from Time")
    LiveData<List<Time>> getTimes();


  @Insert
     void insertEvent(Event event);

    @Delete
    void deleteEvent(Event event);

    @Query("SELECT * FROM Event WHERE eventID=(SELECT max(eventID) FROM event)")
    Event getLastEvent();

    @Query("SELECT * FROM Event WHERE eventID=(select eventID from Time where date= :date)")
    LiveData<Event> getEventByDate(String date);

    @Query("select *from Event")
    LiveData<List<Event>> getEvents();
  @Insert
     void insertEventGuest(List<EventGuest> eventGuest);

    @Delete
    void deleteEventGuest(EventGuest eventGuest);

    @Query("select *from EventGuest")
    LiveData<List<EventGuest>> getEventGuest();



}
