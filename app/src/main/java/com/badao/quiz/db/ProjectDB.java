package com.badao.quiz.db;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.badao.quiz.model.Project;

public class ProjectDB  extends  SQLiteHelper{
    public  static  final  String name = "projects";
    public ProjectDB(@Nullable Context context) {
        super(context);
    }
    public static ProjectDB projectDB;

    public static ProjectDB  getInstance(@Nullable Context context){
        if(projectDB == null){
            projectDB = new ProjectDB(context);
        }
        return  projectDB;
    }

    public long create(Project project){
        ContentValues values = new ContentValues();
        values.put("name", project.getName());
        values.put("created_at", project.getCreatedAt());
        values.put("last_updated", project.getLastUpdated());
        values.put("is_random", project.isRandom() ? 1 : 0);
        values.put("question_per_session", project.getQuestionPerSession());
        values.put("duration", project.getDuration());
        values.put("mode", project.getMode());
        values.put("is_sync", project.isSync() ? 1: 0);
        return sqlWrite.insert(ProjectDB.name, null, values);
    }

}
