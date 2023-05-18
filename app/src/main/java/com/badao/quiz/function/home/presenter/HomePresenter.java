package com.badao.quiz.function.home.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.db.HistorySubmitDB;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.db.QuestionAnswerDB;
import com.badao.quiz.db.QuestionDB;
import com.badao.quiz.db.RecordDestroySyncDB;
import com.badao.quiz.db.RecordUserAnswerDB;
import com.badao.quiz.function.main.dialog.SyncDialog;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.model.RecordDestroySync;
import com.badao.quiz.model.RecordUserAnswer;
import com.badao.quiz.utils.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{

    public HomePresenter(Context context) {
        super(context);
    }
    @Override
    public void initProjects() {
        List<Project> projects = ProjectDB.getInstance(getContext()).findAll();
        getView().refreshView();
        for(Project project: projects){
            Log.e("Project Add", project.toString());
            getView().addProject(project);
        }

    }

    @Override
    public void sync() {
        DatabaseReference mDatabase = getView().getMDatabase();
        if(mDatabase == null){
            return;
        }
        Log.e("Run here", "SYnc");
        List<Project> projects = ProjectDB.getInstance(getContext()).getProjectSync();
        for(Project project: projects){
            project.setIsSync(true);
            Log.e("project sync",project.toString());
            mDatabase.child(ProjectDB.name).child(Utils.generateKey(ProjectDB.name, project.getId()))
                    .setValue(project).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Map<String, String> keys = new HashMap<>();
                    keys.put("is_sync",1+"");
                    ProjectDB.getInstance(getContext()).update(keys,project.getId());
                    if(!ProjectDB.getInstance(getContext()).checkIsSync()){
                        getView().syncSuccess();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Sync failure", e.toString());
                }
            });
        }

        List<Question> questions = QuestionDB.getInstance(getContext()).getQuestionSync();
        for(Question question: questions){
            question.setIsSync(true);
            Log.e("question sync",question.toString());
            mDatabase.child(QuestionDB.name).child(Utils.generateKey(ProjectDB.name, question.getProjectId()))
                    .child(Utils.generateKey(QuestionDB.name, question.getId())).setValue(question).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Map<String, String> keys = new HashMap<>();
                    keys.put("is_sync",1+"");
                    QuestionDB.getInstance(getContext()).sync(question.getId());
                    if(!ProjectDB.getInstance(getContext()).checkIsSync()){
                        getView().syncSuccess();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Sync failure", e.toString());
                }
            });
        }

        List<QuestionAnswer> questionAnswers = QuestionAnswerDB.getInstance(getContext()).getSync();
        for(QuestionAnswer questionAnswer: questionAnswers){
            questionAnswer.setIsSync(true);
            Log.e("questionAnswers sync",questionAnswer.toString());
            mDatabase.child(QuestionAnswerDB.name).child(Utils.generateKey(QuestionDB.name, questionAnswer.getQuestionId()))
                    .child(Utils.generateKey(QuestionAnswerDB.name, questionAnswer.getId()))
                    .setValue(questionAnswer).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Map<String, String> keys = new HashMap<>();
                    keys.put("is_sync",1+"");
                    QuestionAnswerDB.getInstance(getContext()).sync(questionAnswer.getId());
                    if(!ProjectDB.getInstance(getContext()).checkIsSync()){
                        getView().syncSuccess();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Sync failure", e.toString());
                }
            });
        }


        List<HistorySubmit> historySubmits = HistorySubmitDB.getInstance(getContext()).getSync();
        for(HistorySubmit historySubmit: historySubmits){
            historySubmit.setIsSync(true);
            Log.e("historySubmit sync",historySubmit.toString());
            mDatabase.child(HistorySubmitDB.name).child(Utils.generateKey(ProjectDB.name, historySubmit.getProjectId()))
                    .child(Utils.generateKey(HistorySubmitDB.name, historySubmit.getId())).setValue(historySubmit).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    HistorySubmitDB.getInstance(getContext()).sync(historySubmit.getId());
                    if(!ProjectDB.getInstance(getContext()).checkIsSync()){
                        getView().syncSuccess();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Sync failure", e.toString());
                }
            });
        }

        List<RecordUserAnswer> recordUserAnswers = RecordUserAnswerDB.getInstance(getContext()).getSync();
        for(RecordUserAnswer recordUserAnswer: recordUserAnswers){
            recordUserAnswer.setIsSync(true);
            Log.e("recordUserAnswer sync",recordUserAnswer.toString());
            mDatabase.child(RecordUserAnswerDB.name).child(Utils.generateKey(QuestionDB.name, recordUserAnswer.getQuestionId()))
                    .child(Utils.generateKey(RecordUserAnswerDB.name, recordUserAnswer.getId()))
                    .setValue(recordUserAnswer).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    RecordUserAnswerDB.getInstance(getContext()).sync(recordUserAnswer.getId());
                    if(!ProjectDB.getInstance(getContext()).checkIsSync()){
                        getView().syncSuccess();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Sync failure", e.toString());
                }
            });
        }

        // update sync delete
        List<RecordDestroySync> recordDestroySyncs = RecordDestroySyncDB.getInstance(getContext()).getAll();
        for(RecordDestroySync recordDestroySync: recordDestroySyncs){
            Log.e("Sync RecordDestroySync", recordDestroySync.toString());
            DatabaseReference dr = null;
            if(recordDestroySync.getTableName().equals(ProjectDB.name)){
                dr = mDatabase.child(recordDestroySync.getTableName())
                        .child(Utils.generateKey(recordDestroySync.getTableName(), recordDestroySync.getObjectId()));
            }else{
                if(recordDestroySync.getParentId() > 0){
                    Log.e("Run here", "OK");
                    dr = mDatabase.child(recordDestroySync.getTableName())
                            .child(Utils.generateKey(recordDestroySync.getParentName(), recordDestroySync.getParentId()));
                }

                if(recordDestroySync.getObjectId() > 0){
                    if(dr!= null){
                        dr = dr.child(Utils.generateKey(recordDestroySync.getTableName(), recordDestroySync.getObjectId()));
                    }
                }
            }

            if(dr != null){
                dr.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        RecordDestroySyncDB.getInstance(getContext()).destroy(recordDestroySync.getId());
                        if(!ProjectDB.getInstance(getContext()).checkIsSync()){
                            getView().syncSuccess();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Sync failure", e.toString());

                    }
                });
            }
        }
    }

    @Override
    public void firebaseListener() {
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.w("Firebase", "Fetching FCM registration token failed:" + task.getException());
//                        return;
//                    }
//                    // Get new FCM registration token
//                    String token = task.getResult();
//                    Log.w("Firebase", token);
//                });
//        FirebaseMessaging.getInstance().subscribeToTopic("discount-offers")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.e("Firebase","Subscribed! You will get all discount offers notifications");
//                        if (!task.isSuccessful()) {
//                            Log.e("Firebase","Failed! Try again.");
//                        }
//                    }
//                });
    }

//    @Override
//    public void sendMessage() {
//        // Set up the FCM API URL
//        String fcmApiUrl = "https://fcm.googleapis.com/v1/projects/quiz-296b6/messages:send";
//
//// Construct the JSON payload
//        JSONObject payload = new JSONObject();
//        try {
//            // Set the target topic and priority
//            payload.put("topic", "discount-offers");
//            payload.put("priority", "high");
//
//            // Set the data fields
//            JSONObject data = new JSONObject();
//            data.put("title", "TITLE_HERE");
//            data.put("message", "MESSAGE_HERE");
//            data.put("isScheduled", "true");
//            data.put("scheduledTime", "2019-12-13 09:41:00");
//
//            // Add the data fields to the payload
//            payload.put("data", data);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//// Send the request to the FCM API
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        StringRequest request = new StringRequest(
//                Request.Method.POST,
//                fcmApiUrl,
//                response -> {
//                    // Handle successful response
//                    Log.d("Firebase", "FCM request sent successfully.");
//                },
//                error -> {
//                    // Handle error response
//                    Log.e("Firebase", "Failed to send FCM request.", error);
//                }
//        ) {
//            @Override
//            public Map<String, String> getHeaders() {
//                // Set the request headers
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
//                headers.put("Authorization", "Bearer ya29.a0AWY7CkmJtt58kNY8OVYq0rYwsFB7Dqf3KayFk_I9KoQQXK7nyPLPd9UjtvIrjjDkDvxYkCUR6KKeTXGY57Oer845BaIxoPLMmI4MnLNwq4LJpc6MA4N5u2GyXCWocArs7hKjVUcZlTA1NOqHVU1eu39yzXWhaCgYKAdMSARISFQG1tDrp1AnPC38g095mfCNFnQRV_g0163");
//                return headers;
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                // Set the request body
//                return payload.toString().getBytes();
//            }
//        };
//        queue.add(request);
//
//    }
}
