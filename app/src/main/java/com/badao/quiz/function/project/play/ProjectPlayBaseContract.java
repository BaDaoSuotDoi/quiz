package com.badao.quiz.function.project.play;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.function.project.play.presenter.ProjectPlayContract;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.RecordUserAnswer;

import java.util.List;

public class ProjectPlayBaseContract {
    public interface View extends BaseContract.View {
        void updateTime(String time);
        void updateQuestionPlay();
        void setViewMode(int viewMode);
        void setModeMenuShowAnswer();
        void changeViewSubmit(HistorySubmit historySubmit);
    }

    public interface Presenter extends BaseContract.Presenter<ProjectPlayBaseContract.View> {
        int getViewMode();
        Project getProject();
        List<Question> getQuestions(int projectId);
        void setTimeStart(int time);
        void setTimeType(int type);
        void start();
        void stopTime();
        HistorySubmit submit(Project project);

        HistorySubmit getHistorySubmit();
        List<RecordUserAnswer> getUserAnswers();

    }
}
