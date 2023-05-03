package com.badao.quiz.function.question.edit.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditContract;
import com.badao.quiz.model.Project;

public class QuestionEditPresenter extends BasePresenter<QuestionEditContract.View> implements QuestionEditContract.Presenter{

    public QuestionEditPresenter(Context context) {
        super(context);
    }


}
