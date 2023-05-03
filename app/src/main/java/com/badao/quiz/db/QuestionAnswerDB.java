package com.badao.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.badao.quiz.model.Project;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionAnswerDB extends  SQLiteHelper{
    public  static  final  String name = "question_answers";
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

    public List<QuestionAnswer> findBy(int questionId){
        List<QuestionAnswer> answers = new ArrayList<>();
        String[] args = new String[]{questionId+""};
        Cursor cursor = sqlWrite.rawQuery("select * from question_answers where question_id = ?", args );
        while (cursor != null && cursor.moveToNext()){
            answers.add(exact(cursor));
        }
        return  answers;
    }

    public QuestionAnswer exact(Cursor cursor){
        int id = cursor.getInt(0);
        int questionId = cursor.getInt(1);
        String content = cursor.getString(2);
        int type = cursor.getInt(3);
        boolean isSync = cursor.getInt(4) == 1;
        String createdAt = cursor.getString(5);
        String lastUpdated = cursor.getString(6);
        return  new QuestionAnswer(id,questionId, content,type,isSync,createdAt,lastUpdated);
    }
    public long create(QuestionAnswer questionAnswer){
        questionAnswer.setCreatedAt(Utils.getTimeCurrent());
        questionAnswer.setLastUpdated(Utils.getTimeCurrent());
        ContentValues values = new ContentValues();
        values.put("question_id", questionAnswer.getQuestionId());
        values.put("content", questionAnswer.getContent());
        values.put("type", questionAnswer.getType());
        values.put("is_sync", questionAnswer.isSync() ? 1: 0);
        values.put("created_at", questionAnswer.getCreatedAt());
        values.put("last_updated", questionAnswer.getLastUpdated());
        long id = sqlWrite.insert(QuestionAnswerDB.name, null, values);
        questionAnswer.setID((int)id);
        return id ;
    }

    public void update(Map<String, String>values, QuestionAnswer answer){
        if(answer.getID() == 0){
            create(answer);
        }else{
            String[] arg = new String[]{answer.getID()+""};
            ContentValues v = new ContentValues();
            for(String key: values.keySet()){
                v.put(key, values.get(key));
            }
            sqlWrite.update(QuestionAnswerDB.name, v, "id=?",arg );
        }
    }
}
