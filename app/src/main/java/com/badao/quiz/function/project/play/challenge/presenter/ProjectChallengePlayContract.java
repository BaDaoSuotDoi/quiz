package com.badao.quiz.function.project.play.challenge.presenter;

import com.badao.quiz.base.contract.BaseContract;
public class ProjectChallengePlayContract {
    public interface View extends BaseContract.View {
        void updateTime(String time);
    }

    public interface Presenter extends BaseContract.Presenter<ProjectChallengePlayContract.View> {
        void start();
        void stopTime();
    }
}
