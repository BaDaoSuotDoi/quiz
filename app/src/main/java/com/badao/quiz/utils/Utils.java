package com.badao.quiz.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Utils {

    public static int getStatusbarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    public static String getTimeCurrent(){
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = formatter.format(now);
        return  formattedDate;
    }

    public static String checkValid(Question question){
        Log.e("check valid question", question.toString());
        if(question.getContent().isEmpty()){
            return "Empty content!";
        }

        if(question.getType() == 0){
            return "Invalid type";
        }

        for(QuestionAnswer answer: question.getAnswers()){
            if(answer.getContent().isEmpty() && answer.getType() == AppConstants.QUESTION_NORMAL_TYPE ){
                return "Answer question empty";
            }

            if(answer.getContent().length() < 3 && answer.getType() == AppConstants.QUESTION_SELECTION_TYPE ){
                return "Answer question empty";
            }
        }

        return "";
    }

    public static String displaySchedule(String date){
        String time = date.substring(9);
        String[] times = date.substring(0, 8).split("#");
        int monthCycle = Integer.parseInt(times[0]);
        int weekCycle = Integer.parseInt(times[1]);
        int dayCycle = Integer.parseInt(times[2]);
        if(monthCycle == 1){
            return "Every month "+ time;
        }
        if(weekCycle == 1){
            return "Every week "+ time;
        }
        if(dayCycle == 1){
            return "Every day "+ time;
        }
        return time;
    }
    public static String displayTime(int t){
        String time = "";
        int seconds = t % 60;
        if(seconds < 10){
            time += "0"+seconds;
        }else{
            time += seconds;
        }
        int remand = (t - seconds)/60;
        if(remand < 10){
            time = "0"+remand+":" + time;
        }else{
            time = remand+":"+ time;
        }
        return time;
    }

    public static int getTimeSetup(String t){
        if(t.equals("Endless")){
            return -1;
        }
        String[] time = t.split(" ");
        return Integer.parseInt(time[0]);
    }

    public static List<Integer> generateRandomIntList(int range, int num) {
        List<Integer> myList;
        if (range < num) {
            num = range;
        }

        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < num) {
            set.add(random.nextInt(range));
        }

        myList = new ArrayList<>(set);
        Collections.shuffle(myList);

        return myList;
    }

    public static boolean isTimeAutomatic(Context context) {
        return Settings.Global.getInt(
                context.getContentResolver(),
                Settings.Global.AUTO_TIME,
                0
        ) == 1;
    }

    public static long getTimeDiffCurrent(String date){
        // 00#00#00
        String time = date.substring(9);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            Duration duration = Duration.between( LocalDateTime.now(),dateTime);
            long millisecondsDiff = duration.toMillis();
            return  millisecondsDiff;
        }
        return -1;
    }


    public static long getTimeCycle(String date){
        String[] times = date.substring(0, 8).split("#");
        int monthCycle = Integer.parseInt(times[0]);
        int weekCycle = Integer.parseInt(times[1]);
        int dayCycle = Integer.parseInt(times[2]);
        if(monthCycle == 1){
            return 1000 * 30 * 24 * 3600;
        }
        if(weekCycle == 1){
            return 1000 * 7 * 24 * 3600;
        }
        if(dayCycle == 1){
            return 1000  * 24 * 3600;
        }

        return 0;
    }


    public static String getTime(String date){
        String[] items = date.split(" ");
        return  items[2].substring(0, 5);
    }

    public static String getDate(String date){
        String[] items = date.split(" ");
        return  items[1];
    }

    public static String generateKey(String tableName, int id){
        return String.format("%s_%d", tableName, id);
    }
}