package com.badao.quiz.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.badao.quiz.utils.NotificationUtil;

public class ScheduledWorker extends Worker {
    private static final String TAG = "ScheduledWorker";
    public static final String NOTIFICATION_TITLE = "notification_title";
    public static final String NOTIFICATION_MESSAGE = "notification_message";

    public ScheduledWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Work START");

        String title = getInputData().getString(NOTIFICATION_TITLE);
        String message = getInputData().getString(NOTIFICATION_MESSAGE);

        new NotificationUtil(getApplicationContext()).showNotification(title, message);


        Log.d(TAG, "Work DONE");
        return Result.success();
    }
}
