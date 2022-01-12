package com.example.calender.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Time {

    private int eventID;
    @PrimaryKey(autoGenerate = true)
    private int timeID;

    private String date;
    private String time;

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getTimeID() {
        return timeID;
    }

    public void setTimeID(int timeID) {
        this.timeID = timeID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
