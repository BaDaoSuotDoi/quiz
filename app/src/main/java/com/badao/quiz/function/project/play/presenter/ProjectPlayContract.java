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
        void updateMenuQuestion();
        void initMenuHeader();
        void observe();

    }

    public interface Presenter extends BaseContract.Presenter<ProjectPlayContract.View> {
        int getViewMode();
        Project getProject();
        List<Question> getQuestions(int projectId);
        void start();
        void stopTime();
        void submit(Project project);

        HistorySubmit getHistorySubmit();
        List<RecordUserAnswer> getUserAnswers();

    }
}
