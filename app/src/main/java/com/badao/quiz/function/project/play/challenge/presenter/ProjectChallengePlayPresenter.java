package com.badao.quiz.function.project.play.challenge.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.project.play.presenter.ProjectPlayContract;
import com.badao.quiz.utils.Utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ProjectChallengePlayPresenter extends BasePresenter<ProjectChallengePlayContract.View> implements ProjectChallengePlayContract.Presenter{
    public ProjectChallengePlayPresenter(Context context) {
        super(context);
    }
    private Disposable disposable;
    private int totalTime =  0;
    private int currentTime = 0;
    private int timerType = - AppConstants.TIMER_COUNTDOWN;

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
}
