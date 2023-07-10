package com.badao.quiz.function.project.play;

import android.content.Context;

import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.function.project.play.presenter.ProjectPlayContract;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;

import java.util.List;

public abstract class ProjectPlayBaseFragment extends BaseAnnotatedFragment<ProjectPlayBaseContract.View, ProjectPlayBaseContract.Presenter>
        implements ProjectPlayBaseContract.View{
    private Project project;
    private int viewMode;

    public ProjectPlayBaseFragment() {
        this.project = getPresenter().getProject();
        this.viewMode = getPresenter().getViewMode();
        this.project.setQuestions(getPresenter().getQuestions(this.project.getId()));
    }

    public HistorySubmit submit(){
        return getPresenter().submit(this.project);
    }
    public Project getProject() {
        return project;
    }

    public List<Question> getQuestions() {
        return project.getQuestions();
    }

    public int getViewMode() {
        return viewMode;
    }

    public void setQuestions(List<Question> questions) {
        this.project.setQuestions(questions);
    }
}
