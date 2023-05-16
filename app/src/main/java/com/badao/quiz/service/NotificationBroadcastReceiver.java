package com.badao.quiz.service;


import static com.badao.quiz.service.ScheduledWorker.NOTIFICATION_MESSAGE;
import static com.badao.quiz.service.ScheduledWorker.NOTIFICATION_TITLE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("NotificationBroadcastReceiver1", "onReceive");
        if (intent != null) {
            Log.e("NotificationBroadcastReceiver", "onReceive");
            String title = intent.getStringExtra(NOTIFICATION_TITLE);
            String message = intent.getStringExtra(NOTIFICATION_MESSAGE);

            Data notificationData = new Data.Builder()
                    .putString(NOTIFICATION_TITLE, title)
                    .putString(NOTIFICATION_MESSAGE, message)
                    .build();

            OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(ScheduledWorker.class)
                    .setInputData(notificationData)
                    .build();

            WorkManager.getInstance().beginWith(work).enqueue();

        }
    }

}

