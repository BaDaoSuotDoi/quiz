package com.badao.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.model.RecordDestroySync;
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

    public long createOnly(Question question){

        question.setCreatedAt(Utils.getTimeCurrent());
        question.setLastUpdated(Utils.getTimeCurrent());
        ContentValues values = new ContentValues();
        if(question.getId()!=0){
            values.put("id", question.getId());
        }
        values.put("content", question.getContent());
        values.put("created_at", question.getCreatedAt());
        values.put("last_updated", question.getLastUpdated());
        values.put("type", question.getType());
        values.put("is_sync", question.getIsSync()?1:0);
        values.put("comment", question.getComment());
        values.put("position", question.getPosition());
        values.put("status", question.getStatus());
        values.put("project_id", question.getProjectId());
        values.put("version",  question.getVersion());
        long id = sqlWrite.insert(QuestionDB.name, null, values);
        question.setId((int)id);
        return id;
    }

    public long create(Question question){

        question.setCreatedAt(Utils.getTimeCurrent());
        question.setLastUpdated(Utils.getTimeCurrent());
        ContentValues values = new ContentValues();
        if(question.getId()!=0){
            values.put("id", question.getId());
        }
        values.put("content", question.getContent());
        values.put("created_at", question.getCreatedAt());
        values.put("last_updated", question.getLastUpdated());
        values.put("type", question.getType());
        values.put("is_sync", 0);
        values.put("comment", question.getComment());
        values.put("position", question.getPosition());
        values.put("status", question.getStatus());
        values.put("project_id", question.getProjectId());
        values.put("version",  question.getVersion());
        long id = sqlWrite.insert(QuestionDB.name, null, values);
        question.setId((int)id);

        for(QuestionAnswer questionAnswer : question.getAnswers()){
            questionAnswer.setQuestionId(question.getId());
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

    public  List<Question> findBy(Map<String, String>keys){
        List<Question> questions = new ArrayList<>();
        List<String> args = new ArrayList<>();
        String q = "select * from questions where ";
        for(String key: keys.keySet()){
            q += key + " = ? ";
            args.add(keys.get(key));
        }
        Cursor cursor = sqlWrite.rawQuery(q,  args.toArray(new String[args.size()]) );
        while (cursor != null && cursor.moveToNext()){
            questions.add(exact(cursor));
        }
        return  questions;

    }

    public  List<Question> findByProjectId(int projectId){
        String[] args = {projectId+""};
        List<Question> questions = new ArrayList<>();
        Cursor cursor = sqlRead.rawQuery(
                "select q.id, q.content, q.created_at, q.last_updated, q.type,q.is_sync, q.comment,q.position,q.status, q.project_id, q.version " +
                        "from questions as q " +
                        "where q.project_id = ? and q.status = 1 order by q.position asc",
                args);
        while (cursor != null && cursor.moveToNext()){
            Question question = exact(cursor);
            question.setAnswers(QuestionAnswerDB.getInstance(context).findBy(question.getId()));
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
        int position = cursor.getInt(7);
        int status = cursor.getInt(8);
        int projectId = cursor.getInt(9);
        int version = cursor.getInt(10);

        return  new Question(id,content, createdAt, lastUpdated, type, isSync, comment,position,status, projectId, version);
    }

    public void destroy(int id){
        String[] args = new String[]{id+""};
        Question question = findByPk(id);
        if(question != null){
            RecordDestroySyncDB.getInstance(context).add(new RecordDestroySync(question.getId(), QuestionDB.name, question.getProjectId(), ProjectDB.name));
            RecordDestroySyncDB.getInstance(context).add(new RecordDestroySync(0, QuestionAnswerDB.name,question.getId(), QuestionDB.name));
            RecordDestroySyncDB.getInstance(context).add(new RecordDestroySync(0, RecordUserAnswerDB.name,question.getId(), QuestionDB.name));
            sqlWrite.delete(QuestionDB.name, "id=?",args);
        }
    }

    public void update(Question question, boolean wasDone, boolean isDelete){
        if(question.getId() <= 0){
            create(question);
        } else {
            Question questionExist = QuestionDB.getInstance(context).findByPk(question.getId());
            List<QuestionAnswer> questionAnswers = QuestionAnswerDB.getInstance(context).findBy(question.getId());
            List<QuestionAnswer> questionAnswersDeleted = new ArrayList<>();
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

            Log.e("questionAnswers", questionAnswers.toString());
            Log.e("question.getAnswers()", question.getAnswers().toString());
            for(QuestionAnswer questionAnswer: questionAnswers){
                boolean isAnswerAdd = true;
                for(QuestionAnswer answer: question.getAnswers()){
                    if(questionAnswer.getId() == answer.getId()){
                        isAnswerAdd = false;
                        if(!questionAnswer.getContent().equals(answer.getContent())){
                            isNewAnswer = true;
                        }
                    }
                }
                if(isAnswerAdd){
                    Log.e("DELETE","OK");
                    questionAnswersDeleted.add(questionAnswer);
                }
                if(isAnswerAdd || isNewAnswer){
                    isNewAnswer = true;
                }
            }

            boolean isNew = isNewContent || isNewAnswer ||isNewType;

            // question edited
            if((isNew && wasDone) || (wasDone && isDelete)){

                QuestionDB.getInstance(context).disable(question.getId());
                if(!isDelete){
                    question.upgrade();
                    create(question);
                }

                return;
            }

            if(isDelete){
                Log.e("Question delete", question.toString());
                destroy(question.getId());
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
                    String[]  arg = new String[]{question.getId()+""};
                    values.put("is_sync", 0+"");
                    sqlWrite.update(QuestionDB.name, values, "id=?",arg);
                }


                if(isNewAnswer){
                    Log.e("Run here", "update new answer");
                    for(QuestionAnswer answer: question.getAnswers()){
                        Log.e("Run here", "update new answer"+ answer.getId());
                        Map<String, String> v = new HashMap<>();
                        v.put("content", answer.getContent());
                        v.put("type", answer.getType()+"");
                        v.put("last_updated", Utils.getTimeCurrent());
                        v.put("is_sync", 0+"");
                        QuestionAnswerDB.getInstance(context).update(v, answer);
                    }

                    for(QuestionAnswer questionAnswer: questionAnswersDeleted){
                        Log.e("Delete ok",questionAnswer.toString());

                        QuestionAnswerDB.getInstance(context).destroy(questionAnswer);
                    }
                }
            }
        }
    }

    private void disable(int id) {
        String[] arg = {id+""};
        ContentValues values = new ContentValues();
        values.put("status", 0);
        sqlWrite.update(QuestionDB.name,values, "id=?", arg);
    }

    public List<Question> getQuestionSync(){
        List<Question> questions = new ArrayList<>();
        Cursor cursor = sqlRead.rawQuery("select * from questions where is_sync = 0", null);
        while (cursor != null && cursor.moveToNext()){
            Question question = exact(cursor);
            question.setIsSync(true);
            questions.add(question);
        }
        return  questions;
    }

    public void sync(int id){
        ContentValues values = new ContentValues();
        values.put("is_sync", 1);
        String[] arg = {id+""};
        sqlWrite.update(QuestionDB.name, values, "id=?",arg);

    }
}
