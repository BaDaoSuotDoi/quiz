package com.badao.quiz.utils;

import android.content.Context;
import android.util.Log;

import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static int getStatusbarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
}