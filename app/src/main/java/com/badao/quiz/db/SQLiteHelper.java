package com.badao.quiz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quiz.db";
    private static int DATABASE_VERSION = 9;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }
    protected SQLiteDatabase sqlRead = getReadableDatabase();
    protected SQLiteDatabase sqlWrite = getWritableDatabase();

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String projectDB = "CREATE TABLE IF NOT EXISTS projects (\n" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  name VARCHAR(255) NOT NULL,\n" +
                "  created_at VARCHAR(255) NOT NULL,\n" +
                "  last_updated VARCHAR(255) NOT NULL,\n" +
                "  is_random INTEGER NOT NULL,\n" +
                "  question_per_session INTEGER NOT NULL,\n" +
                "  duration INTEGER NOT NULL,\n" +
                "  mode INTEGER NOT NULL,\n"+
                "  is_sync INTEGER NOT NULL\n" +
                ");\n";

        String questionDB = "CREATE TABLE IF NOT EXISTS questions (\n" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  content TEXT NOT NULL,\n" +
                "  created_at VARCHAR(255) NOT NULL,\n" +
                "  last_updated VARCHAR(255) NOT NULL,\n" +
                "  type INTEGER NOT NULL,\n"+
                "  is_sync INTEGER NOT NULL,\n" +
                "  comment TEXT NOT NULL\n" +
                ");\n";

        String questionVersionDB = "CREATE TABLE IF NOT EXISTS question_versions (\n" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  version INTEGER NOT NULL,\n"+
                "  status INTEGER NOT NULL,\n"+
                "  project_id INTEGER NOT NULL, \n"+
                "  question_id INTEGER NOT NULL, \n"+
                "  is_sync INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (question_id) REFERENCES quesions(id) ON DELETE CASCADE\n,"  +
                "  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE\n"  +
                ");\n";

        String questionAnswerDB = "CREATE TABLE IF NOT EXISTS question_answers (\n" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  question_id INTEGER NOT NULL,\n"+
                "  content TEXT NOT NULL, \n"+
                "  type INTEGER NOT NULL,\n"+
                "  is_sync INTEGER NOT NULL,\n" +
                "  created_at VARCHAR(255) NOT NULL,\n" +
                "  last_updated VARCHAR(255) NOT NULL,\n" +
                "  FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE\n"  +
                ");\n";

        String  historySubmitDB = "CREATE TABLE IF NOT EXISTS history_submits (\n" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  project_id INTEGER NOT NULL,\n"+
                "  is_sync INTEGER NOT NULL,\n" +
                "  time_elapsed INTEGER NOT NULL,\n" +
                "  submitted_at VARCHAR(255) NOT NULL,\n" +
                "  correct_answer_number INTEGER NOT NULL,\n" +
                "  no_answer_number INTEGER NOT NULL,\n" +
                "  question_number INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE\n"  +
                ");\n";

        String  recordUserAnswerDB = "CREATE TABLE IF NOT EXISTS record_user_answers (\n" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  history_id INTEGER NOT NULL,\n"+
                "  question_id INTEGER NOT NULL,\n"+
                "  answer TEXT NOT NULL, \n"+
                "  status INTEGER NOT NULL,\n"+
                "  is_sync INTEGER NOT NULL,\n" +
                "  created_at VARCHAR(255) NOT NULL,\n" +
                "  last_updated VARCHAR(255) NOT NULL,\n" +
                "  FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,\n"  +
                "  FOREIGN KEY (history_id) REFERENCES history_submits(id) ON DELETE CASCADE\n"  +
                ");\n";

        sqLiteDatabase.execSQL(projectDB);
        sqLiteDatabase.execSQL(questionDB);
        sqLiteDatabase.execSQL(questionVersionDB);
        sqLiteDatabase.execSQL(questionAnswerDB);
        sqLiteDatabase.execSQL(historySubmitDB);
        sqLiteDatabase.execSQL(recordUserAnswerDB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1> 4){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS question_versions");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS history_submits");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS record_user_answers");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS question_answers");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS questions");
            onCreate(sqLiteDatabase);
        }

    }

}
