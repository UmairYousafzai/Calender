package com.example.calender.models;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.example.calender.broadCastReceivers.AlarmBroadcastReceiver;

import java.util.Calendar;

public class Alarm {

    private static final String RECURRING = "recurring";
    private static final String MONDAY = "monday";
    private static final String TUESDAY = "tuesday";
    private static final String WEDNESDAY = "wednesday";
    private static final String THURSDAY = "thursday";
    private static final String FRIDAY = "friday";
    private static final String SATURDAY = "saturday";
    private static final String SUNDAY = "sunday";
    private static final String TITLE = "title";
    private  int alarmId;
    private  int day ,month, year;

    private int hour, minute;
    private boolean started, recurring ,daily, monthly , yearly;
    private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday  ;
    private String title;
    private AlarmManager alarmManager ;


    public Alarm(int alarmId, int hour, int minute,
                 String title, boolean started, boolean recurring,
                 boolean monday, boolean tuesday, boolean wednesday,
                 boolean thursday, boolean friday, boolean saturday,
                 boolean sunday,boolean daily,boolean monthly,boolean yearly,
                 int day,int month, int year) {
        this.alarmId = alarmId;
        this.hour = hour;
        this.minute = minute;
        this.started = started;

        this.recurring = recurring;

        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.daily = daily;
        this.monthly = monthly;
        this.yearly = yearly;
        this.day = day;
        this.month = month;
        this.year = year;
        this.title = title;
    }

    public void schedule(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
       if (daily)
       {
           setDailyAlarm(context);
       }
       else if (monthly)
       {
           setMonthlyAlarm(context);
       }
       else if (yearly)
       {
           setYearlyAlarm(context);
       }
    }

    private String getRecurringDaysText() {
        String text="";
        if (monday)
        {
            text =text+MONDAY;
        }
        if (tuesday)
        {
            text =text+TUESDAY;
        }
        if (wednesday)
        {
            text =text+WEDNESDAY;
        }
        if (thursday)
        {
            text =text+THURSDAY;
        }
        if (friday)
        {
            text =text+FRIDAY;
        }
        if (saturday)
        {
            text =text+SATURDAY;
        }
        if (sunday)
        {
            text =text+SUNDAY;
        }
        return text;
    }

    public void setDailyAlarm(Context context)
    {

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(RECURRING, recurring);
        intent.putExtra(MONDAY, monday);
        intent.putExtra(TUESDAY, tuesday);
        intent.putExtra(WEDNESDAY, wednesday);
        intent.putExtra(THURSDAY, thursday);
        intent.putExtra(FRIDAY, friday);
        intent.putExtra(SATURDAY, saturday);
        intent.putExtra(SUNDAY, sunday);

        intent.putExtra(TITLE, title);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }


        if (!recurring) {
            String toastText = null;
            try {
                toastText = String.format("One Time Alarm %s scheduled for %s at %02d:%02d", title, getRecurringDaysText(), hour, minute, alarmId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        alarmPendingIntent
                );
            }
        } else {
            String toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", title, getRecurringDaysText(), hour, minute, alarmId);
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            final long RUN_DAILY = 24 * 60 * 60 * 1000;

            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent
            );
        }

        this.started = true;
    }


    public void setMonthlyAlarm(Context context)
    {

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(RECURRING, recurring);
        intent.putExtra(TITLE, title);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),day);

        // if alarm time has already passed, increment day by 1
        if (monthly)
        {
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            }
        }



        if (!recurring) {
            String toastText = null;
            try {
                toastText = String.format("One Time Alarm %s scheduled for %s at %02d:%02d", title, "Next Month", hour, minute, alarmId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        alarmPendingIntent
                );
            }
        } else {
            String toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", title, getRecurringDaysText(), hour, minute, alarmId);
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            final long milliSeconds =getTimeDifferenceInMilliSeconds(1);

            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    milliSeconds,
                    alarmPendingIntent
            );
        }

        this.started = true;
    }


    public void setYearlyAlarm(Context context)
    {

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(RECURRING, recurring);
        intent.putExtra(TITLE, title);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),day);

        // if alarm time has already passed, increment day by 1
        if (yearly)
        {
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
            }
        }



        if (!recurring) {
            String toastText = null;
            try {
                toastText = String.format("One Time Alarm %s scheduled for %s at %02d:%02d", title, "Next Month", hour, minute, alarmId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        alarmPendingIntent
                );
            }
        } else {
            String toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", title, getRecurringDaysText(), hour, minute, alarmId);
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            final long milliSeconds =getTimeDifferenceInMilliSeconds(2);

            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    milliSeconds,
                    alarmPendingIntent
            );
        }

        this.started = true;
    }

    public long getTimeDifferenceInMilliSeconds(int key)
    {

        Calendar currentCalendar= Calendar.getInstance();
        Calendar futureCalendar= Calendar.getInstance();

        long currentMilliSeconds=  currentCalendar.getTimeInMillis();
        long futureMilliSeconds=0;
        long finalMilliSeconds=0;

        if (key==1)
        {
            if (currentCalendar.after(day))
            {
                futureCalendar.set(currentCalendar.get(Calendar.YEAR),currentCalendar.get(Calendar.MONTH)+1,day);

                futureMilliSeconds = futureCalendar.getTimeInMillis();
                finalMilliSeconds= futureMilliSeconds -currentMilliSeconds;
            }
            else
            {
                futureCalendar.set(currentCalendar.get(Calendar.YEAR),currentCalendar.get(Calendar.MONTH),day);

                futureMilliSeconds = futureCalendar.getTimeInMillis();
                finalMilliSeconds= futureMilliSeconds -currentMilliSeconds;
            }

        }
        else if (key==2)
        {
            if (currentCalendar.after(month))
            {
                futureCalendar.set(currentCalendar.get(Calendar.YEAR)+1,month,day);

                futureMilliSeconds = futureCalendar.getTimeInMillis();
                finalMilliSeconds= futureMilliSeconds -currentMilliSeconds;
            }
            else
            {
                futureCalendar.set(currentCalendar.get(Calendar.YEAR),month,day);

                futureMilliSeconds = futureCalendar.getTimeInMillis();
                finalMilliSeconds= futureMilliSeconds -currentMilliSeconds;
            }

        }
        return finalMilliSeconds;
    }
}
