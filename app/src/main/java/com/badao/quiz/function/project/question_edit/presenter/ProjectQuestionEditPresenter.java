package com.badao.quiz.function.project.question_edit.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.model.Project;

public class ProjectQuestionEditPresenter extends BasePresenter<ProjectQuestionEditContract.View> implements ProjectQuestionEditContract.Presenter{
    public ProjectQuestionEditPresenter(Context context) {
        super(context);
    }

    @Override
    public Project getProject() {
        int id = getStateBundle().getInt(AppConstants.PROJECT_ID);
        return ProjectDB.getInstance(getContext()).findByPk(id);
    }
}
