package com.badao.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import com.badao.quiz.model.Project;
import com.badao.quiz.model.Statistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public Project create(Project project){
        ContentValues values = new ContentValues();
        values.put("name", project.getName());
        values.put("created_at", project.getCreatedAt());
        values.put("last_updated", project.getLastUpdated());
        values.put("is_random", project.isRandom() ? 1 : 0);
        values.put("question_per_session", project.getQuestionPerSession());
        values.put("duration", project.getDuration());
        values.put("mode", project.getMode());
        values.put("is_sync", project.isSync() ? 1: 0);
        long id = sqlWrite.insert(ProjectDB.name, null, values);
        project.setID((int)id);
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
        Cursor cursor = sqlRead.rawQuery("select count(*) from question_versions where project_id = ?  and status = 1", args);
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

    public void destroy(Project project){
        Log.e("Project delete", "Ok "+ project.getID());
        String[] args = {project.getID()+""};
        sqlWrite.delete("projects", "id = ?", args);
    }

    public void update(Map<String, String> keys, int id){
        String[] args = {id+""};
        ContentValues values = new ContentValues();
        for(String key: keys.keySet()){
            if(key.equals("is_random") ||  key.equals("is_sync") || key.equals("question_per_session")){
                values.put(key, Integer.parseInt(keys.get(key)));
            }else{
                values.put(key, keys.get(key));
            }
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
}
