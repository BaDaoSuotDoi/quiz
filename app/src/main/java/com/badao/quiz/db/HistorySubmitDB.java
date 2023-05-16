package com.badao.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.RecordUserAnswer;

import java.util.ArrayList;
import java.util.List;

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

    public HistorySubmit findByPk(int id){
        String[] arg = new String[]{id+""};
        Cursor cursor = sqlRead.rawQuery(
                "select * from history_submits where id = ?", arg);
        while (cursor != null && cursor.moveToNext()){
           return exact(cursor);
        }
        return null;
    }

    public List<HistorySubmit> findBy(){
        List<HistorySubmit>  historySubmits = new ArrayList<>();
        Cursor cursor = sqlRead.rawQuery(
                "select hs.id, hs.project_id, hs.is_sync, hs.time_elapsed, hs.submitted_at, hs.correct_answer_number,hs.no_answer_number,hs.question_number,p.name " +
                        "from history_submits as hs, projects as p " +
                        "where hs.project_id = p.id order by hs.id desc", null);
        while (cursor != null && cursor.moveToNext()){
            HistorySubmit historySubmit = exact(cursor);
            historySubmit.setProject(new Project(cursor.getString(8)));
            historySubmits.add(historySubmit);
        }
        return historySubmits;
    }

    public long create(HistorySubmit historySubmit){
        Log.e("Create History", historySubmit.toString());
        ContentValues values = new ContentValues();
        values.put("project_id", historySubmit.getProjectId());
        values.put("is_sync", historySubmit.isSync()? 1 : 0);
        values.put("time_elapsed", historySubmit.getTimeElapsed());
        values.put("submitted_at", historySubmit.getSubmittedAt() );
        values.put("correct_answer_number", historySubmit.getCorrectAnswerNumber());
        values.put("no_answer_number", historySubmit.getNoAnswerNumber());
        values.put("question_number", historySubmit.getQuestionNumber());
        long id = sqlWrite.insert(HistorySubmitDB.name, null, values);
        historySubmit.setId((int)id);
        return id ;
    }

    public HistorySubmit exact(Cursor cursor){
        int id = cursor.getInt(0);
        int projectId = cursor.getInt(1);
        boolean isSync = cursor.getInt(2) == 1;
        int timeElapsed = cursor.getInt(3);
        String submittedAt = cursor.getString(4);
        int correctAnswerNumber = cursor.getInt(5);
        int noAnswerNumber = cursor.getInt(6);
        int questionNumber = cursor.getInt(7);

        return  new HistorySubmit(id, projectId, isSync, timeElapsed, submittedAt, correctAnswerNumber, noAnswerNumber, questionNumber);
    }

    public List<HistorySubmit> getSync(){
        List<HistorySubmit> historySubmits = new ArrayList<>();
        Cursor cursor = sqlRead.rawQuery("select * from history_submits where is_sync = 0", null);
        while (cursor != null && cursor.moveToNext()){
            HistorySubmit historySubmit = exact(cursor);
            historySubmit.setSync(true);
            historySubmits.add(historySubmit);
        }
        return  historySubmits;
    }

    public void sync(int id){
        ContentValues values = new ContentValues();
        values.put("is_sync", 1);
        String[] arg = {id+""};
        sqlWrite.update(HistorySubmitDB.name, values, "id=?",arg);
    }


}
