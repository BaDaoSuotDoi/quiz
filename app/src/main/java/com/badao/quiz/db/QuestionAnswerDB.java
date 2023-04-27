package com.badao.quiz.db;

import android.content.Context;

import androidx.annotation.Nullable;

public class QuestionAnswerDB extends  SQLiteHelper{

    public QuestionAnswerDB(@Nullable Context context) {
        super(context);
    }

    static QuestionAnswerDB questionAnswerDB;

    static QuestionAnswerDB  getInstance(@Nullable Context context){
        if(questionAnswerDB == null){
            questionAnswerDB = new QuestionAnswerDB(context);
        }
        return  questionAnswerDB;
    }
}
