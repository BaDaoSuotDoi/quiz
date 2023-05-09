package com.badao.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import com.badao.quiz.model.Project;
import com.badao.quiz.model.RecordUserAnswer;
import com.badao.quiz.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordUserAnswerDB extends SQLiteHelper{
    public static final  String name = "record_user_answers";
    public RecordUserAnswerDB(@Nullable Context context) {
        super(context);
    }

    static RecordUserAnswerDB recordUserAnswerDB;

    public static RecordUserAnswerDB  getInstance(@Nullable Context context){
        if(recordUserAnswerDB == null){
            recordUserAnswerDB = new RecordUserAnswerDB(context);
        }
        return  recordUserAnswerDB;
    }

    public long create(RecordUserAnswer recordUserAnswer){
        ContentValues values = new ContentValues();
        recordUserAnswer.setCreatedAt(Utils.getTimeCurrent());
        recordUserAnswer.setLastUpdated(Utils.getTimeCurrent());
        values.put("history_id", recordUserAnswer.getHistoryId());
        values.put("question_id", recordUserAnswer.getQuestionId());
        values.put("answer", recordUserAnswer.getAnswer());
        values.put("status", recordUserAnswer.getStatus() );
        values.put("is_sync", recordUserAnswer.isSync()? 1:0);
        values.put("created_at", recordUserAnswer.getCreatedAt());
        values.put("last_updated", recordUserAnswer.getLastUpdated());
        long id = sqlWrite.insert(RecordUserAnswerDB.name, null, values);
        Log.e(" RecordUserAnswerDB ID", id+"");
        recordUserAnswer.setID((int)id);
        return id ;
    }
    public List<RecordUserAnswer> findBy(Map<String, String>keys){
        List<RecordUserAnswer> records = new ArrayList<>();
        List<String> args = new ArrayList<>();
        String q = "select * from record_user_answers where ";
        for(String key: keys.keySet()){
            q += key + " = ? ";
            args.add(keys.get(key));
        }
        Cursor cursor = sqlRead.rawQuery(q, args.toArray(new String[args.size()]));
        while (cursor != null && cursor.moveToNext()){
            records.add(exact(cursor));
        }
        return  records;
    }

    public List<RecordUserAnswer> findBy(List<String>questionIds){
        List<RecordUserAnswer> records = new ArrayList<>();
        String q = "select * from record_user_answers where question_id in ";
        q += "(" + String.join(",", questionIds) + ")";
        Cursor cursor = sqlRead.rawQuery(q, null);
        while (cursor != null && cursor.moveToNext()){
            records.add(exact(cursor));
        }
        return  records;
    }
    public RecordUserAnswer exact(Cursor cursor){
        int id = cursor.getInt(0);
        int historyId = cursor.getInt(1);
        int questionId = cursor.getInt(2);
        String answer = cursor.getString(3);
        int status = cursor.getInt(4);
        boolean isSync = cursor.getInt(5) == 1;
        String createdAt = cursor.getString(6);
        String lastUpdated = cursor.getString(7);

        return  new RecordUserAnswer(id,historyId,questionId, answer, status, isSync, createdAt, lastUpdated);
    }
}
