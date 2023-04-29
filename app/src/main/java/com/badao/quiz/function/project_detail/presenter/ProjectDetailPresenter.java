package com.badao.quiz.function.project_detail.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.function.login.presenter.LoginContract;
import com.badao.quiz.model.Project;

public class ProjectDetailPresenter extends BasePresenter<ProjectDetailContract.View> implements ProjectDetailContract.Presenter{
    public ProjectDetailPresenter(Context context) {
        super(context);
    }

    @Override
    public Project getProject() {
        int id = getStateBundle().getInt(AppConstants.PROJECT_ID);
        return ProjectDB.getInstance(getContext()).findByPk(id);
    }
}
