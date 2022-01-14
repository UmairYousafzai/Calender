package com.example.calender.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Event.class,parentColumns ="eventID",
        childColumns = "eventID",onDelete = CASCADE))
public class EventGuest {

    private int eventID;

    private int supplierID;

    @PrimaryKey(autoGenerate = true)
    private int eventGuestID;

    private String eventGuestType;
    private String guestName;

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public int getEventGuestID() {
        return eventGuestID;
    }

    public void setEventGuestID(int eventGuestID) {
        this.eventGuestID = eventGuestID;
    }

    public String getEventGuestType() {
        return eventGuestType;
    }

    public void setEventGuestType(String eventGuestType) {
        this.eventGuestType = eventGuestType;
    }
}
