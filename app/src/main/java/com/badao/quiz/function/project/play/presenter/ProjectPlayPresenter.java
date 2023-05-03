package com.badao.quiz.function.project.play.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.db.QuestionDB;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.utils.Utils;

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
    public Project getProject() {
        int id = getStateBundle().getInt(AppConstants.PROJECT_ID);
        return ProjectDB.getInstance(getContext()).findByPk(id);
    }

    @Override
    public int getViewMode() {
        return getStateBundle().getInt(AppConstants.VIEW_MODE);
    }

    @Override
    public List<Question> getQuestions(int projectId) {
        return  QuestionDB.getInstance(getContext()).findByProjectId(projectId);
    }
}
