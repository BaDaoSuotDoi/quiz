package com.badao.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.model.RecordDestroySync;

import java.util.ArrayList;
import java.util.List;

public class RecordDestroySyncDB  extends  SQLiteHelper{
    public  static  final  String name = "record_destroy_sync";

    public RecordDestroySyncDB(@Nullable Context context) {
        super(context);
    }
    private static RecordDestroySyncDB recordDestroySyncDB;

    public static RecordDestroySyncDB  getInstance(@Nullable Context context){
        if(recordDestroySyncDB == null){
            recordDestroySyncDB = new RecordDestroySyncDB(context);
        }
        return  recordDestroySyncDB;
    }

    public RecordDestroySync exact(Cursor cursor){
        int id = cursor.getInt(0);
        int objectId = cursor.getInt(1);
        String tableName = cursor.getString(2);
        int parentId = cursor.getInt(3);
        String parentName = cursor.getString(4);
        return  new RecordDestroySync(id,objectId, tableName,parentId,parentName);
    }

    public long add(RecordDestroySync recordDestroySync){
        ContentValues values = new ContentValues();
        values.put("object_id", recordDestroySync.getObjectId());
        values.put("table_name", recordDestroySync.getTableName());
        values.put("parent_id", recordDestroySync.getParentId());
        values.put("parent_name", recordDestroySync.getParentName());
        long id = sqlWrite.insert(RecordDestroySyncDB.name, null, values);
        recordDestroySync.setId((int)id);
        return id ;
    }

    public List<RecordDestroySync> getAll(){
        List<RecordDestroySync> recordDestroySyncs = new ArrayList<>();
        Cursor cursor = sqlRead.rawQuery("Select * from record_destroy_sync", null);
        while(cursor!=null && cursor.moveToNext()){
            recordDestroySyncs.add(exact(cursor));
        }

        return  recordDestroySyncs;
    }

    public void destroy(int id){
        String[] arg = {id+""};
        sqlWrite.delete(RecordDestroySyncDB.name, "id=?", arg);
    }

    public void destroyAll(){
        sqlWrite.delete(RecordDestroySyncDB.name, null, null);
    }
}
