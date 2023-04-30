package com.badao.quiz.function.project.question_edit.presenter;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.model.Project;

public class ProjectQuestionEditContract {
    public interface View extends BaseContract.View {
        void updateProjectName();
    }

    public interface Presenter extends BaseContract.Presenter<ProjectQuestionEditContract.View> {
        Project getProject();
    }
}
