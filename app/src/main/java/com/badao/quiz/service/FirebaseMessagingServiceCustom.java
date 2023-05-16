package com.badao.quiz.service;

import static com.badao.quiz.service.ScheduledWorker.NOTIFICATION_MESSAGE;
import static com.badao.quiz.service.ScheduledWorker.NOTIFICATION_TITLE;
import static com.badao.quiz.utils.Utils.isTimeAutomatic;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.badao.quiz.utils.NotificationUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FirebaseMessagingServiceCustom extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData() != null && !remoteMessage.getData().isEmpty()) {
            // Get Message details
            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("message");

            // Check that 'Automatic Date and Time' settings are turned ON.
            // If it's not turned on, Return
            if (!isTimeAutomatic(getApplicationContext())) {
                return;
            }

            // Check whether notification is scheduled or not
            String isScheduledStr = remoteMessage.getData().get("isScheduled");
            Boolean isScheduled = Boolean.valueOf(isScheduledStr);
            if (isScheduled != null && isScheduled) {
                // This is Scheduled Notification, Schedule it
                String scheduledTime = remoteMessage.getData().get("scheduledTime");
                scheduleAlarm(scheduledTime, title, message);
            } else {
                // This is not scheduled notification, show it now
                showNotification(title, message);
            }
        }
    }

    private void scheduleAlarm(String scheduledTimeString, String title, String message) {
        AlarmManager alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), NotificationBroadcastReceiver.class);
        intent.putExtra(NOTIFICATION_TITLE, title);
        intent.putExtra(NOTIFICATION_MESSAGE, message);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        // Parse Schedule time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date scheduledTime;
        try {
            scheduledTime = sdf.parse(scheduledTimeString);
        } catch (ParseException e) {
            scheduledTime = null;
        }

        if (scheduledTime != null) {
            // With set(), it'll set non repeating one time alarm.
            alarmMgr.set(
                    AlarmManager.RTC_WAKEUP,
                    scheduledTime.getTime(),
                    alarmIntent
            );

        }
    }

    private void showNotification(String title, String message) {
        NotificationUtil notificationUtil = new NotificationUtil(getApplicationContext());
        notificationUtil.showNotification(title, message);
    }

}

