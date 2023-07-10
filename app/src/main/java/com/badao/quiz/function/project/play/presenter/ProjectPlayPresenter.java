package com.badao.quiz.function.project.play.presenter;

import android.content.Context;
import android.util.Log;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.HistorySubmitDB;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.db.QuestionDB;
import com.badao.quiz.db.RecordUserAnswerDB;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.model.RecordUserAnswer;
import com.badao.quiz.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ProjectPlayPresenter extends BasePresenter<ProjectPlayContract.View> implements ProjectPlayContract.Presenter{
    private Disposable disposable;
    private int totalTime =  0;
    private int currentTime = 0;
    private int timerType = - AppConstants.TIMER_COUNTDOWN;
    private Project project;
    public ProjectPlayPresenter(Context context) {
        super(context);
    }

    @Override
    public void start() {
        stopTime();
        disposable = Observable.interval(1, 1, TimeUnit.SECONDS)
                .map(t -> {
                    totalTime += 1;
                    currentTime += (timerType == AppConstants.TIMER_COUNTDOWN ? -1 : 1);
                    return currentTime;
                })
                .takeUntil(time -> (time == 0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> {
                    getView().updateTime(Utils.displayTime(t));
                    if (t <= 0) {
                        getView().changeViewSubmit( submit(project));
                        getView().setViewMode(AppConstants.PROJECT_SHOW_ANSWER, -1);
                        stopTime();
                    }
                }, Throwable::printStackTrace);
    }

    @Override
    public void stopTime() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public HistorySubmit submit(Project project) {
        stopTime();
        List<Question> questions = project.getQuestions();
        int questionNumber = questions.size();
        int correctAnswerNumber = 0;
        int noAnswerNumber = 0;

        for(Question question: questions){
           int status = checkAnswerQuestion(question);
           if(status == 0){
               noAnswerNumber++;
           }else if(status == AppConstants.QUESTION_ANSWER_CORRECT){
               correctAnswerNumber++;
           }
        }

        Log.e("Submit", "correctAnswerNumber: "+correctAnswerNumber+"//noAnswerNumber:"+noAnswerNumber );
        HistorySubmit historySubmit = new HistorySubmit(project.getId(),totalTime, Utils.getTimeCurrent(),correctAnswerNumber,noAnswerNumber,questionNumber );
        HistorySubmitDB.getInstance(getContext()).create(historySubmit);
        for(Question question: questions){
            question.getUserAnswers().setHistoryId(historySubmit.getId());
            RecordUserAnswer recordUserAnswer = question.getUserAnswers();
            RecordUserAnswerDB.getInstance(getContext()).create(recordUserAnswer);
        }
        return historySubmit;
    }

    @Override
    public int checkAnswerQuestion(Question question) {
        Log.e("getUserAnswers", question.getUserAnswers().toString());
        String answer = question.getUserAnswers().getAnswer();
        if(answer.isEmpty()){
            return 0;
        }

        if(question.getType() == AppConstants.QUESTION_NORMAL_TYPE){
            QuestionAnswer questionAnswer =  question.getAnswers().get(0);
            if(questionAnswer.getContent().equals(answer)){
                question.getUserAnswers().setStatus(AppConstants.QUESTION_ANSWER_CORRECT);
                return AppConstants.QUESTION_ANSWER_CORRECT;
            }else{
                question.getUserAnswers().setStatus(AppConstants.QUESTION_ANSWER_WRONG);
                return AppConstants.QUESTION_ANSWER_WRONG;
            }
        }else if(question.getType() == AppConstants.QUESTION_SELECTION_TYPE){
            Log.e("Start here", answer);

            String[] elements = Arrays.stream(answer.split(AppConstants.TOKEN_SPLIT_ANSWER_USER_SELECTION)).filter(
                    e -> !e.isEmpty()
            ).toArray(String[]::new);
            int questionCorrectNumber = 0;
            boolean isAnswerCorrect = true;
            for( QuestionAnswer questionAnswer : question.getAnswers()){
                if(questionAnswer.getContent().startsWith(AppConstants.TOKEN_TRUE_SELECT_ANSWER)){
                    questionCorrectNumber++;
                    boolean isCorrect = false;
                    for (String element : elements) {
                        if (!element.isEmpty()) {
                            int id = Integer.parseInt(element);
                            if (questionAnswer.getId() == id) {
                                isCorrect = true;
                            }
                        }
                    }
                    if(!isCorrect){
                        isAnswerCorrect = false;
                    }
                }
                Log.e("questionAnswer", questionAnswer.toString());
            }

            // user select all answer correct
            if(isAnswerCorrect){
                question.getUserAnswers().setStatus(AppConstants.QUESTION_ANSWER_CORRECT);
            }
            // when user selection all answer correct and some answer wrong
            if(questionCorrectNumber != elements.length ){
                Log.e("Fault here", "no match number choose "+elements.length);
                question.getUserAnswers().setStatus(AppConstants.QUESTION_ANSWER_WRONG);
                return AppConstants.QUESTION_ANSWER_WRONG;
            }else if(elements.length == 0){
                question.getUserAnswers().setStatus(AppConstants.QUESTION_ANSWER_CORRECT);
                return AppConstants.QUESTION_ANSWER_CORRECT;
            }

        }else if(question.getType() == AppConstants.QUESTION_VOCABULARY_TYPE){
            Log.e("QUESTION_VOCABULARY_TYPE", answer);
            if(question.getAnswers().get(0).getContent().equals(answer)){
                question.getUserAnswers().setStatus(AppConstants.QUESTION_ANSWER_CORRECT);
                return AppConstants.QUESTION_ANSWER_CORRECT;
            }else if(answer.isEmpty()){
                return 0;
            }else{
                question.getUserAnswers().setStatus(AppConstants.QUESTION_ANSWER_WRONG);
                return AppConstants.QUESTION_ANSWER_WRONG;
            }
        }
        return 0;
    }

    @Override
    public HistorySubmit getHistorySubmit() {
        if(getStateBundle().containsKey(AppConstants.HISTORY_PROJECT_ID)){
            int id = getStateBundle().getInt(AppConstants.HISTORY_PROJECT_ID);
            return  HistorySubmitDB.getInstance(getContext()).findByPk(id);
        }
        return null;
    }

    @Override
    public Project getProject() {
        int id = getStateBundle().getInt(AppConstants.PROJECT_ID);
        project = ProjectDB.getInstance(getContext()).findByPk(id);
        return project;
    }

    @Override
    public int getViewMode() {
        return getStateBundle().getInt(AppConstants.VIEW_MODE);
    }

    @Override
    public List<Question> getQuestions(int projectId) {
        return  QuestionDB.getInstance(getContext()).findByProjectId(projectId);
    }

    @Override
    public List<RecordUserAnswer> getUserAnswers() {
        if(getStateBundle().containsKey(AppConstants.HISTORY_PROJECT_ID)){
            int id = getStateBundle().getInt(AppConstants.HISTORY_PROJECT_ID);
            return  RecordUserAnswerDB.getInstance(getContext()).findBy(new HashMap<String, String>(){{
                put("history_id", ""+id);
            }});
        }
        return null;
    }

    @Override
    public void setTimeStart(int time) {
        this.currentTime = time;
    }
    @Override
    public void setTimeType(int type) {
        this.timerType = type;
    }
}
