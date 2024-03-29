package com.badao.quiz.function.project.play.presenter;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.RecordUserAnswer;

import java.util.List;

public class ProjectPlayContract {
    public interface View extends BaseContract.View {
        void updateTime(String time);
        void updateQuestionPlay();
        void setViewMode(int viewMode, int idx);
        void changeViewSubmit(HistorySubmit historySubmit);
    }

    public interface Presenter extends BaseContract.Presenter<ProjectPlayContract.View> {
        int getViewMode();
        Project getProject();
        List<Question> getQuestions(int projectId);
        void setTimeStart(int time);
        void setTimeType(int type);
        void start();
        void stopTime();
        HistorySubmit submit(Project project);
        int checkAnswerQuestion(Question question);
        HistorySubmit getHistorySubmit();
        List<RecordUserAnswer> getUserAnswers();

    }
}
