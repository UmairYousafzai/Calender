package com.example.calender.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.calender.R;
import com.example.calender.broadCastReceivers.AlarmBroadcastReceiver;

import static com.example.calender.App.ALARM_NOTIFICATION_CHANNEL_ID;

public class AlarmService extends Service {

    private static final String TITLE ="title" ;
//    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    public static final String ACTION_TURN_OFF_ALARM=  "alarm notification off";
    private Ringtone ringtone;
    @Override
    public void onCreate() {
        super.onCreate();

//        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
//        mediaPlayer.setLooping(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent notificationIntent = new Intent(this, RingActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);





        if (intent.getAction().equals(ACTION_TURN_OFF_ALARM))
        {
            ringtone.stop();
            stopForeground(true);
        }else if (intent.getAction().equals(AlarmBroadcastReceiver.ACTION_TURN_ON_ALARM))
        {
            showNotification(intent.getStringExtra(TITLE));
        }

        return START_STICKY;
    }

    private void showNotification(String title) {

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
         ringtone = RingtoneManager.getRingtone(this, notification);
        ringtone.play();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone.setLooping(true);
        }

        // vibration
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 300, 300, 300};
        v.vibrate(pattern, -1);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ALARM_NOTIFICATION_CHANNEL_ID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.ic_baseline_access_alarm_24);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_baseline_access_alarm_24);
        }

        Intent intent = new Intent(this,AlarmService.class);
        intent.setAction(ACTION_TURN_OFF_ALARM);

        PendingIntent turnOffAlarmIntent= PendingIntent.getService(this,100,intent,0);


        notificationBuilder.setContentTitle("FFC");
        notificationBuilder.setCategory(NotificationCompat.CATEGORY_REMINDER);
        notificationBuilder.setContentText("Ring Ring .. Ring Ring");
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Ring Ring .. Ring Ring"));
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

        notificationBuilder.addAction(new NotificationCompat.Action(0,"Turn off",turnOffAlarmIntent));









        startForeground(1, notificationBuilder.build());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
