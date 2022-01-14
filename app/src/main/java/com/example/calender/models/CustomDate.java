package com.example.calender.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomDate implements Parcelable {
    
    private int day;
    private int year;
    private int month;
    private boolean isCurrentDate;

    public CustomDate(int day, int year, int month) {
        this.day = day;
        this.year = year;
        this.month = month;
    }


    protected CustomDate(Parcel in) {
        day = in.readInt();
        year = in.readInt();
        month = in.readInt();
        isCurrentDate = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(day);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeByte((byte) (isCurrentDate ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomDate> CREATOR = new Creator<CustomDate>() {
        @Override
        public CustomDate createFromParcel(Parcel in) {
            return new CustomDate(in);
        }

        @Override
        public CustomDate[] newArray(int size) {
            return new CustomDate[size];
        }
    };

    public boolean isCurrentDate() {
        return isCurrentDate;
    }

    public void setCurrentDate(boolean currentDate) {
        isCurrentDate = currentDate;
    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }


    public void setMonth(int month) {
        this.month = month;
    }

    public String getDateSting()
    {
        return day+"-"+month+"-"+year;
    }
}
