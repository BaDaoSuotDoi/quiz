package com.badao.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.model.RecordUserAnswer;
import com.badao.quiz.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionDB extends  SQLiteHelper{
    public  static  final  String name = "questions";
    Context context;
    public QuestionDB(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    static QuestionDB questionDB;

    public static QuestionDB  getInstance(@Nullable Context context){
        if(questionDB == null){
            questionDB = new QuestionDB(context);
        }
        return  questionDB;
    }

    public long create(Question question, int projectId, int version){

        question.setCreatedAt(Utils.getTimeCurrent());
        question.setLastUpdated(Utils.getTimeCurrent());
        ContentValues values = new ContentValues();
        values.put("content", question.getContent());
        values.put("created_at", question.getCreatedAt());
        values.put("last_updated", question.getLastUpdated());
        values.put("type", question.getType());
        values.put("is_sync", 0);
        values.put("comment", question.getComment());
        long id = sqlWrite.insert(QuestionDB.name, null, values);
        question.setID((int)id);

        QuestionVersionDB.getInstance(this.context).create(projectId, question.getID(), version);

        for(QuestionAnswer questionAnswer : question.getAnswers()){
            if(questionAnswer.getQuestionId() <= 0){
                questionAnswer.setQuestionId(question.getID());
            }
            Log.e("Question answer created", questionAnswer.toString());
            QuestionAnswerDB.getInstance(context).create(questionAnswer);
        }
        return id;
    }

    public Question findByPk(int id){
        String[] args = {id+""};
        Cursor cursor = sqlRead.rawQuery("select * from questions where id = ?", args);
        while (cursor != null && cursor.moveToNext()){
            return exact(cursor);
        }
        return  null;
    }

    public  List<Question> findByProjectId(int projectId){
        String[] args = {projectId+""};
        List<Question> questions = new ArrayList<>();
        Cursor cursor = sqlRead.rawQuery(
                "select q.id, q.content, q.created_at, q.last_updated, q.type,q.is_sync, q.comment " +
                        "from questions as q, question_versions as qv " +
                        "where q.id = qv.question_id and qv.project_id = ? and qv.status = 1",
                args);
        while (cursor != null && cursor.moveToNext()){
            Question question = exact(cursor);
            question.setAnswers(QuestionAnswerDB.getInstance(context).findBy(question.getID()));
            questions.add(question);
        }
        return  questions;
    }
    public Question exact(Cursor cursor){
        int id = cursor.getInt(0);
        String content = cursor.getString(1);
        String createdAt = cursor.getString(2);
        String lastUpdated = cursor.getString(3);
        int type = cursor.getInt(4);
        boolean isSync = cursor.getInt(5) == 1;
        String comment = cursor.getString(6);

        return  new Question(id,content, createdAt, lastUpdated, type, isSync, comment);
    }

    public void destroy(int id){
        String[] args = new String[]{id+""};
        sqlWrite.delete(QuestionDB.name, "id=?",args);
    }

    public void update(Question question, int projectId, boolean wasDone, boolean isDelete){
        if(question.getID() <= 0){
            create(question, projectId, 1);
        } else {
            Question questionExist = QuestionDB.getInstance(context).findByPk(question.getID());
            List<QuestionAnswer> questionAnswers = QuestionAnswerDB.getInstance(context).findBy(question.getID());
            boolean isNewContent = false;
            boolean isNewAnswer = question.getAnswers().size() != questionAnswers.size();
            boolean isNewType = false;
            boolean isNewComment = false;
            if(questionExist != null &&!question.getContent().equals(questionExist.getContent())){
                isNewContent = true;
            }

            if(questionExist != null && question.getType() != questionExist.getType()){
                isNewType = true;
            }

            if(!isNewAnswer){
                for(QuestionAnswer questionAnswer: questionAnswers){
                    boolean isAnswerAdd = true;
                    for(QuestionAnswer answer: question.getAnswers()){
                        if(questionAnswer.getID() == answer.getID()){
                            isAnswerAdd = false;
                            if(!questionAnswer.getContent().equals(answer.getContent())){
                                isNewAnswer = true;
                                break;
                            }
                        }
                    }
                    if(isAnswerAdd || isNewAnswer){
                        isNewAnswer = true;
                        break;
                    }
                }
            }
            boolean isNew = isNewContent || isNewAnswer ||isNewType ||isDelete;

            if(isNew && wasDone){
                //update question all version
                int version = QuestionVersionDB.getInstance(context).disable(projectId, question.getID());
                // copy to new question
                create(question, projectId,  version + 1);
                return;
            }

            if(isDelete){
                Log.e("Question delete", question.toString());
                destroy(question.getID());
                return;
            }
            if(questionExist != null && !question.getComment().equals(questionExist.getComment())){
                isNewComment = true;
            }
            isNew = isNew || isNewComment;
            if(isNew){
                ContentValues values = new ContentValues();
                if(isNewContent){
                    values.put("content",question.getContent());
                }
                if(isNewType){
                    values.put("type",question.getType());
                }
                if(isNewComment){
                    values.put("comment",question.getComment());
                }
                if(values.size() != 0){
                    String[]  arg = new String[]{question.getID()+""};
                    sqlWrite.update(QuestionDB.name, values, "id=?",arg);
                }


                if(isNewAnswer){
                    Log.e("Run here", "update new answer");
                    for(QuestionAnswer answer: question.getAnswers()){
                        Log.e("Run here", "update new answer"+ answer.getID());
                        Map<String, String> v = new HashMap<>();
                        v.put("content", answer.getContent());
                        v.put("type", answer.getType()+"");
                        v.put("last_updated", Utils.getTimeCurrent());
                        QuestionAnswerDB.getInstance(context).update(v, answer);
                    }
                }
            }
        }
    }
}
