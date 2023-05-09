package com.badao.quiz.function.project.question_edit.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.db.QuestionDB;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;

import java.util.List;

public class ProjectQuestionEditPresenter extends BasePresenter<ProjectQuestionEditContract.View> implements ProjectQuestionEditContract.Presenter{
    public ProjectQuestionEditPresenter(Context context) {
        super(context);
    }
    private int questionIndex = 0;
    @Override
    public Project getProject() {
        int id = getStateBundle().getInt(AppConstants.PROJECT_ID);
        return ProjectDB.getInstance(getContext()).findByPk(id);
    }

    @Override
    public List<Question> getQuestions(int projectId) {
        return  QuestionDB.getInstance(getContext()).findByProjectId(projectId);
    }

    @Override
    public int getQuestionIndex() {
        return questionIndex++;
    }
    @Override
    public void setQuestionIndex(int index) {
        this.questionIndex = index;
    }

}
