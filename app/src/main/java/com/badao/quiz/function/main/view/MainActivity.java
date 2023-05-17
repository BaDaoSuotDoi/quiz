package com.badao.quiz.function.main.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseMvpActivity;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.HistorySubmitDB;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.db.QuestionAnswerDB;
import com.badao.quiz.db.QuestionDB;
import com.badao.quiz.db.RecordUserAnswerDB;
import com.badao.quiz.function.home.dialog.DeleteProjectDialog;
import com.badao.quiz.function.home.dialog.WarningSyncDialog;
import com.badao.quiz.function.main.dialog.SyncDialog;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.main.presenter.MainActivityContract;
import com.badao.quiz.function.main.presenter.MainActivityPresenter;
import com.badao.quiz.helper.NavHelper;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.model.RecordUserAnswer;
import com.badao.quiz.service.NotificationService;
import com.badao.quiz.utils.Utils;
import com.badao.quiz.viewmode.BaseVMF;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity  extends BaseMvpActivity<MainActivityContract.Presenter>
        implements MainActivityContract.View{
    public NavHelper navHelper;
    private MainActivityVM mMainActivityModel;

    private NotificationService.NotificationBinder notificationBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            notificationBinder = (NotificationService.NotificationBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            notificationBinder = null;
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initNavHelper() {
        navHelper = new NavHelper(this, R.id.navHost);
        Log.e("Init navHelper", "navHelper");
    }


    private void initViewModel() {
        mMainActivityModel = new ViewModelProvider(this, BaseVMF.getInstance()).get(MainActivityVM.class);
    }

    private void doObserve() {

        mMainActivityModel.getEventShowKeyBoard().observe(this, isShow -> {
           toggleKeyboard(isShow);
        });

        mMainActivityModel.getMlScheduleNotificationProject().observe(this, payload -> {
            if(AppConstants.SCHEDULE_NOTIFICATION == payload.getAction()){
                Project project = (Project) payload.getValue();
                if(notificationBinder != null){
                    notificationBinder.getService().scheduleAlarm(project.getSchedule(),
                                project.getId(), project.getName(), String.format("Funny Practice '%s' now!", project.getName()));
                }
            }
        });

        mMainActivityModel.getEventSyncData().observe(this, isSync->{
            if(isSync){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    String email = user.getEmail();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(email.substring(0, email.length() - 10));
                    mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {

                                List<Project> projects = new ArrayList<>();
                                List<HistorySubmit> historySubmits = new ArrayList<>();
                                List<Question> questions = new ArrayList<>();
                                List<QuestionAnswer> questionAnswers = new ArrayList<>();
                                List<RecordUserAnswer> recordUserAnswers = new ArrayList<>();

                                try {
                                    if(task.getResult().getValue() == null){
                                        return;
                                    }
                                    Gson gson = new Gson();
                                    String json = gson.toJson(task.getResult().getValue());
                                    JSONObject object = new JSONObject(json);
                                    Iterator<String> keys = object.keys();
                                    while (keys.hasNext()){
                                        String key = keys.next();
                                        if(key.equals(ProjectDB.name)){
                                            JSONObject jsonProjects = object.getJSONObject(key);
                                            Iterator<String> projectIds = jsonProjects.keys();
                                            while (projectIds.hasNext()){
                                                String id = projectIds.next();
                                                Project project = gson.fromJson(jsonProjects.get(id).toString(), Project.class);
                                                projects.add(project);
                                            }
                                        }else if(key.equals(HistorySubmitDB.name)){
                                            JSONObject jsonProjectHistorySubmits= object.getJSONObject(key);
                                            Iterator<String> projectIds = jsonProjectHistorySubmits.keys();
                                            while (projectIds.hasNext()){
                                                String projectId = projectIds.next();
                                                JSONObject jsonHistorySubmits = jsonProjectHistorySubmits.getJSONObject(projectId);
                                                Iterator<String> historyIds = jsonHistorySubmits.keys();

                                                while (historyIds.hasNext()){
                                                    String historyId = historyIds.next();
                                                    HistorySubmit historySubmit = gson.fromJson(jsonHistorySubmits.get(historyId).toString(), HistorySubmit.class);
                                                    historySubmits.add(historySubmit);
                                                }

                                            }
                                        }else if(key.equals(QuestionDB.name)){
                                            JSONObject jsonProjectQuestion= object.getJSONObject(key);
                                            Iterator<String> projectIds = jsonProjectQuestion.keys();
                                            while (projectIds.hasNext()){
                                                String projectId = projectIds.next();
                                                JSONObject jsonQuestions = jsonProjectQuestion.getJSONObject(projectId);
                                                Iterator<String> questionsIds = jsonQuestions.keys();

                                                while (questionsIds.hasNext()){
                                                    String questionId = questionsIds.next();
                                                    Question question = gson.fromJson(jsonQuestions.get(questionId).toString(), Question.class);
                                                    questions.add(question);
                                                }
                                            }
                                        }else if(key.equals(QuestionAnswerDB.name)){
                                            JSONObject jsonQuestionQuestionAnswer= object.getJSONObject(key);
                                            Iterator<String> questionQuestionAnswerIds = jsonQuestionQuestionAnswer.keys();
                                            while (questionQuestionAnswerIds.hasNext()){
                                                String projectQuestionAnswerId = questionQuestionAnswerIds.next();
                                                JSONObject jsonQuestionAnswers = jsonQuestionQuestionAnswer.getJSONObject(projectQuestionAnswerId);
                                                Iterator<String> questionAnswerIds = jsonQuestionAnswers.keys();

                                                while (questionAnswerIds.hasNext()){
                                                    String questionAnswerId = questionAnswerIds.next();
                                                    QuestionAnswer questionAnswer = gson.fromJson(jsonQuestionAnswers.get(questionAnswerId).toString(), QuestionAnswer.class);
                                                    questionAnswers.add(questionAnswer);
                                                }

                                            }
                                        }else if(key.equals(RecordUserAnswerDB.name)){
                                            JSONObject jsonQuestionRecordUserAnswers = object.getJSONObject(key);
                                            Iterator<String> questionRecordUserAnswerIds = jsonQuestionRecordUserAnswers.keys();
                                            while (questionRecordUserAnswerIds.hasNext()){
                                                String questionRecordUserAnswerId = questionRecordUserAnswerIds.next();
                                                JSONObject jsonRecordUserAnswers = jsonQuestionRecordUserAnswers.getJSONObject(questionRecordUserAnswerId);
                                                Iterator<String>recordUserAnswerIds = jsonRecordUserAnswers.keys();

                                                while (recordUserAnswerIds.hasNext()){
                                                    String recordUserAnswerId = recordUserAnswerIds.next();
                                                    RecordUserAnswer recordUserAnswer = gson.fromJson(jsonRecordUserAnswers.get(recordUserAnswerId).toString(), RecordUserAnswer.class);
                                                    recordUserAnswers.add(recordUserAnswer);
                                                }

                                            }
                                        }

                                    }

                                    for(Project project: projects){
                                        Log.e("Project", project.toString());
                                        ProjectDB.getInstance(getApplicationContext()).create(project);
                                    }

                                    for(Question question: questions){
                                        Log.e("Question", question.toString());

                                        QuestionDB.getInstance(getApplicationContext()).createOnly(question);
                                    }

                                    for(HistorySubmit historySubmit: historySubmits){
                                        Log.e("historySubmit", historySubmit.toString());

                                        HistorySubmitDB.getInstance(getApplicationContext()).create(historySubmit);
                                    }

                                    for(QuestionAnswer questionAnswer: questionAnswers){
                                        Log.e("questionAnswer", questionAnswer.toString());
                                        QuestionAnswerDB.getInstance(getApplicationContext()).create(questionAnswer);
                                    }

                                    for(RecordUserAnswer recordUserAnswer: recordUserAnswers){
                                        Log.e("recordUserAnswer", recordUserAnswer.toString());

                                        RecordUserAnswerDB.getInstance(getApplicationContext()).create(recordUserAnswer);
                                    }
                                    mMainActivityModel.getEventReload().postValue(true);
//                                    mMainActivityModel.getEventSyncData().postValue(false);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void initViews(boolean isRefreshData) {
        initViewModel();
        initNavHelper();
        doObserve();
        startService( new Intent( this, NotificationService. class )) ;
        Intent intent = new Intent(this, NotificationService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected MainActivityContract.Presenter createPresenterInstance() {
        return new MainActivityPresenter(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

}