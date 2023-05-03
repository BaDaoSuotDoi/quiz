package com.badao.quiz.function.project.detail.presenter;

import android.content.Context;
import android.util.Pair;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.model.Project;
import com.badao.quiz.utils.BundleBuilder;

public class ProjectDetailPresenter extends BasePresenter<ProjectDetailContract.View> implements ProjectDetailContract.Presenter{
    public ProjectDetailPresenter(Context context) {
        super(context);
    }

    @Override
    public int getNumberQuestion() {
        int id = getStateBundle().getInt(AppConstants.PROJECT_ID);
        return ProjectDB.getInstance(getContext()).getNumberQuestion(id);
    }

    @Override
    public Project getProject() {
        int id = getStateBundle().getInt(AppConstants.PROJECT_ID);
        return ProjectDB.getInstance(getContext()).findByPk(id);
    }
}
