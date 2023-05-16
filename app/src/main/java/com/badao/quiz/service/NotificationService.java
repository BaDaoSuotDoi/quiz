package com.badao.quiz.service;

import static com.badao.quiz.service.ScheduledWorker.NOTIFICATION_MESSAGE;
import static com.badao.quiz.service.ScheduledWorker.NOTIFICATION_TITLE;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.badao.quiz.R;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.model.Project;
import com.badao.quiz.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class NotificationService extends Service {

    String TAG = "Timers" ;
    private  AlarmManager alarmMgr;
    public void scheduleAlarm(String scheduledTimeString,int requestCode, String title, String message) {
        scheduledTimeString = scheduledTimeString.substring(9);
        Log.e("scheduledTimeString", scheduledTimeString);
        Intent intent = new Intent(getApplicationContext(), NotificationBroadcastReceiver.class);
        intent.putExtra(NOTIFICATION_TITLE, title);
        intent.putExtra(NOTIFICATION_MESSAGE, message);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date scheduledTime;
        try {
            scheduledTime = sdf.parse(scheduledTimeString);
        } catch (ParseException e) {
            scheduledTime = null;
        }

        if (scheduledTime != null) {
            alarmMgr.set(
                    AlarmManager.RTC_WAKEUP,
                    scheduledTime.getTime(),
                    alarmIntent
            );
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return  new NotificationBinder();
    }

    @Override
    public int onStartCommand (Intent intent , int flags , int startId) {
        Log. e ( TAG , "onStartCommand" ) ;
        super .onStartCommand(intent , flags , startId) ;
        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        scheduleAlarm("2023-05-15 11:58:00", 0,"Hello", "OK");

        return START_STICKY ;
    }
    @Override
    public void onCreate () {
        Log. e ( TAG , "onCreate" ) ;
    }
    @Override
    public void onDestroy () {
        Log. e ( TAG , "onDestroy" ) ;
        super.onDestroy() ;
    }

    public class NotificationBinder extends Binder {
        public NotificationService getService() {
            return NotificationService.this;
        }
    }
}
