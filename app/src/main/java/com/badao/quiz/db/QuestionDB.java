package com.badao.quiz.db;

import android.content.Context;

import androidx.annotation.Nullable;

public class QuestionDB extends  SQLiteHelper{
    public QuestionDB(@Nullable Context context) {
        super(context);
    }

    static QuestionDB questionDB;

    static QuestionDB  getInstance(@Nullable Context context){
        if(questionDB == null){
            questionDB = new QuestionDB(context);
        }
        return  questionDB;
    }
}
