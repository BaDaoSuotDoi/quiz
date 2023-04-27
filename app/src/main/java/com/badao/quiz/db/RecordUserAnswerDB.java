package com.badao.quiz.db;

import android.content.Context;

import androidx.annotation.Nullable;

public class RecordUserAnswerDB extends SQLiteHelper{
    public RecordUserAnswerDB(@Nullable Context context) {
        super(context);
    }

    static RecordUserAnswerDB recordUserAnswerDB;

    static RecordUserAnswerDB  getInstance(@Nullable Context context){
        if(recordUserAnswerDB == null){
            recordUserAnswerDB = new RecordUserAnswerDB(context);
        }
        return  recordUserAnswerDB;
    }
}
