package com.badao.quiz.function.project.play.presenter;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;

import java.util.List;

public class ProjectPlayContract {
    public interface View extends BaseContract.View {
        void updateTime(String time);
        void updateQuestionPlay();
    }

    public interface Presenter extends BaseContract.Presenter<ProjectPlayContract.View> {
        int getViewMode();
        Project getProject();
        List<Question> getQuestions(int projectId);
        void start();
        void stopTime();

    }
}
