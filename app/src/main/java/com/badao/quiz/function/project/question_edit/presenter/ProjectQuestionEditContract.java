package com.badao.quiz.function.project.question_edit.presenter;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;

import java.util.List;

public class ProjectQuestionEditContract {
    public interface View extends BaseContract.View {
        void updateProjectName();
        void updateListAnswer();
    }

    public interface Presenter extends BaseContract.Presenter<ProjectQuestionEditContract.View> {
        Project getProject();
        List<Question> getQuestions(int projectId);
        int getQuestionIndex();
    }
}
