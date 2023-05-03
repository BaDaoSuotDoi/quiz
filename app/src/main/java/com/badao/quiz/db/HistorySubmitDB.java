package com.badao.quiz.db;

import android.content.Context;

import androidx.annotation.Nullable;

public class HistorySubmitDB extends  SQLiteHelper{
    public static final  String name = "history_submits";
    public static HistorySubmitDB historySubmitDB;
    public HistorySubmitDB(@Nullable Context context) {
        super(context);
    }

    public static HistorySubmitDB  getInstance(@Nullable Context context){
        if(historySubmitDB == null){
            historySubmitDB = new HistorySubmitDB(context);
        }
        return  historySubmitDB;
    }
}
