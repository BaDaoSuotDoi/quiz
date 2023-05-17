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
import com.badao.quiz.model.Statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectDB  extends  SQLiteHelper{
    public  static  final  String name = "projects";
    private  Context context;
    public ProjectDB(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public static ProjectDB projectDB;

    public static ProjectDB  getInstance(@Nullable Context context){
        if(projectDB == null){
            projectDB = new ProjectDB(context);
        }
        return  projectDB;
    }

    public Project create(Project project){
        ContentValues values = new ContentValues();
        if(project.getId()!=0){
            values.put("id", project.getId());
        }
        values.put("name", project.getName());
        values.put("created_at", project.getCreatedAt());
        values.put("last_updated", project.getLastUpdated());
        values.put("is_random", project.getIsRandom() ? 1 : 0);
        values.put("question_per_session", project.getQuestionPerSession());
        values.put("duration", project.getDuration());
        values.put("mode", project.getMode());
        values.put("is_sync", project.getIsSync() ? 1: 0);
        values.put("schedule", project.getSchedule());
        long id = sqlWrite.insert(ProjectDB.name, null, values);
        project.setId((int)id);
        return project ;
    }

    public Project findByPk(int id){
        String[] args = {id+""};
        Cursor cursor = sqlRead.rawQuery("select * from projects where id = ?", args);
        while (cursor != null && cursor.moveToNext()){
            return exact(cursor);
        }
        return  null;
    }


    public int getNumberQuestion(int id){
        String[] args = {id+""};
        Cursor cursor = sqlRead.rawQuery("select count(*) from questions where project_id = ?  and status = 1", args);
        if(cursor!= null && cursor.moveToNext()){
            return  cursor.getInt(0);
        }
        return 0;
    }
    public List<Project> findBy( Map<String,String> query){
        List<Project> projects = new ArrayList<>();
        if(query != null){
            int limit = Integer.parseInt(query.get("limit"));
        }

        return  projects;
    }

    public List<Project> findAll(){
        List<Project> projects = new ArrayList<>();
        String[] args = {};
        Cursor cursor = sqlRead.rawQuery("select * from projects", args);
        while (cursor != null && cursor.moveToNext()){
            projects.add(exact(cursor));
        }
        return  projects;
    }

    public List<Project> findByName(String name){
        List<Project> projects = new ArrayList<>();
        String[] args = {"%"+name+"%"};
        Cursor cursor = sqlRead.rawQuery("select * from projects where name like ?", args);
        while (cursor != null && cursor.moveToNext()){
            projects.add(exact(cursor));
        }
        return  projects;
    }

    public Project exact(Cursor cursor){
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String createdAt = cursor.getString(2);
        String lastUpdated = cursor.getString(3);
        Boolean isRandom = cursor.getInt(4) == 1? true: false;
        int questionPerSession = cursor.getInt(5);
        int duration = cursor.getInt(6);
        int mode = cursor.getInt(7);
        Boolean isSync = cursor.getInt(8) == 1? true: false;
        return  new Project(id,name, createdAt,lastUpdated,isRandom,questionPerSession,duration,mode,isSync );
    }

    public void destroyAll(){
        sqlWrite.delete(ProjectDB.name, null, null);
    }
    public void destroy(Project project){
        Log.e("Project delete", "Ok "+ project.getId());
        Project project1 = findByPk(project.getId());
        if(project1 != null){
            Map<String, String> keys = new HashMap<>();
            keys.put("project_id", project1.getId()+"");

            RecordDestroySyncDB.getInstance(context).add(new RecordDestroySync(project.getId(),ProjectDB.name, 0, ""));
            RecordDestroySyncDB.getInstance(context).add(new RecordDestroySync(0,HistorySubmitDB.name,project.getId(), ProjectDB.name));
            List<Question> questions = QuestionDB.getInstance(context).findBy(keys);

            for(Question question: questions){
                RecordDestroySyncDB.getInstance(context).add(new RecordDestroySync(0, QuestionDB.name, question.getProjectId(), ProjectDB.name));
                RecordDestroySyncDB.getInstance(context).add(new RecordDestroySync(0, QuestionAnswerDB.name,question.getId(), QuestionDB.name));
                RecordDestroySyncDB.getInstance(context).add(new RecordDestroySync(0, RecordUserAnswerDB.name,question.getId(), QuestionDB.name));
            }
            String[] args = {project.getId()+""};
            sqlWrite.delete("projects", "id = ?", args);
        }

    }

    public void update(Map<String, String> keys, int id){
        String[] args = {id+""};
        ContentValues values = new ContentValues();
        boolean isSync = false;
        for(String key: keys.keySet()){
            if(key.equals("is_sync")){
                isSync = true;
            }
            if(key.equals("is_random") || key.equals("question_per_session")){
                values.put(key, Integer.parseInt(keys.get(key)));
            }else{
                values.put(key, keys.get(key));
            }
        }
        if(!isSync){
            values.put("is_sync", 0);
        }
        sqlWrite.update("projects",values,"id = ?",args);
    }

    public List<Statistic> statisticAllProject(){
        List<Statistic> statistics = new ArrayList<>();
        Cursor cursor = sqlRead.rawQuery("SELECT projects.id AS project_id, \n" +
                "       projects.name AS project_name,\n" +
                "       COUNT(DISTINCT history_submits.id) AS num_of_submissions,\n" +
                "       COUNT(record_user_answers.id) AS total_num_of_answers, \n" +
                "       SUM(CASE WHEN record_user_answers.status=1 THEN 1 ELSE 0 END) AS num_of_correct_answers\n" +
                "FROM projects\n" +
                "LEFT JOIN history_submits ON projects.id = history_submits.project_id\n" +
                "LEFT JOIN record_user_answers ON history_submits.id = record_user_answers.history_id\n" +
                "GROUP BY projects.id;\n", null);

        while (cursor!= null && cursor.moveToNext()){
            String projectName = cursor.getString(1);
            int numberPlayed = cursor.getInt(2);
            int numberCorrect = cursor.getInt(4);
            int numberAnswer = cursor.getInt(3);
            statistics.add(new Statistic(projectName,numberPlayed,numberCorrect,numberAnswer ));
        }
        return statistics;

    }

    public List<Project> getProjectSync(){
        List<Project> projects = new ArrayList<>();
        Cursor cursor = sqlRead.rawQuery("select * from projects where is_sync = 0", null);
        while (cursor != null && cursor.moveToNext()){
            Project project = exact(cursor);
            project.setIsSync(true);
            projects.add(exact(cursor));
        }
        return  projects;
    }

    public boolean checkIsSync(){
        Cursor cursor = sqlRead.rawQuery("SELECT 'projects' AS table_name FROM projects WHERE is_sync = 0\n" +
                "UNION\n" +
                "SELECT 'questions' AS table_name FROM questions WHERE is_sync = 0\n" +
                "UNION\n" +
                "SELECT 'question_answers' AS table_name FROM question_answers WHERE is_sync = 0\n" +
                "UNION\n" +
                "SELECT 'history_submits' AS table_name FROM history_submits WHERE is_sync = 0\n" +
                "UNION\n" +
                "SELECT 'record_user_answers' AS table_name FROM record_user_answers WHERE is_sync = 0;\n", null);


        while (cursor!= null && cursor.moveToNext()){
            Log.e("AAA",cursor.getString(0));
            return true;
        }

        Cursor cursor1 = sqlRead.rawQuery("SELECT count(*) from record_destroy_sync", null);
        while (cursor1 != null && cursor1.moveToNext()){
            Log.e("Count",cursor1.getString(0));
            return cursor1.getInt(0) > 0;
        }

        return false;
    }
}
