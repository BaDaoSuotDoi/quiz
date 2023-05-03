package com.badao.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.model.Question;

public class QuestionVersionDB  extends  SQLiteHelper{
    public  static  final  String name = "question_versions";
    private  Context context;
    public QuestionVersionDB(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public static QuestionVersionDB questionVersionDB;

    public static QuestionVersionDB  getInstance(@Nullable Context context){
        if(questionVersionDB == null){
            questionVersionDB = new QuestionVersionDB(context);
        }
        return  questionVersionDB;
    }


    public int disable(int projectId, int questionId){
        String[] args = {projectId+"",questionId+""};
        Cursor cursor = sqlWrite.rawQuery("select * from question_versions where project_id=? and question_id=?",args);
        int version = 0;
        if(cursor != null){
            version = cursor.getInt(1);
        }
        ContentValues values = new ContentValues();
        values.put("status", AppConstants.QUESTION_DISABLE);
        sqlWrite.update(QuestionVersionDB.name,values, "project_id=? and question_id=? ", args);
        return version;
    }

    public long create(int projectId, int questionId, int v){
        ContentValues version = new ContentValues();
        version.put("version", v);
        version.put("status", AppConstants.QUESTION_ACTIVE);
        version.put("project_id", projectId);
        version.put("question_id", questionId);
        version.put("is_sync", 0);
        return sqlWrite.insert(QuestionVersionDB.name,null, version);
    }
}
