package com.example.calender;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String ALARM_NOTIFICATION_CHANNEL_ID=  "alarm notification";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channelAlarm = new NotificationChannel(
                    ALARM_NOTIFICATION_CHANNEL_ID,
                    "Alarm Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            channelAlarm.setDescription("This Notification trigger alarm");

            NotificationManager mNotificationManager= getSystemService(NotificationManager.class);
            mNotificationManager.createNotificationChannel(channelAlarm);

        }

    }
}
