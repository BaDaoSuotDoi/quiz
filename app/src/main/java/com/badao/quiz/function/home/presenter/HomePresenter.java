package com.badao.quiz.function.home.presenter;

import android.content.Context;
import android.util.Log;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.model.Project;

import java.util.List;


public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{

    public HomePresenter(Context context) {
        super(context);
    }

    @Override
    public void initProjects() {
        List<Project> projects = ProjectDB.getInstance(getContext()).findAll();
        getView().refreshView();
        for(Project project: projects){
            Log.e("Project Add", project.toString());
            getView().addProject(project);
        }

    }
}
