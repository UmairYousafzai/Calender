package com.example.calender.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EventGuest {

    private int eventID;

    private int supplierID;

    @PrimaryKey(autoGenerate = true)
    private int eventGuestID;

    private String eventGuestType;

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
