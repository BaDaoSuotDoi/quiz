package com.badao.quiz.function.question.play.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.function.question.edit.presenter.QuestionEditContract;

public class QuestionPlayPresenter extends BasePresenter<QuestionPlayContract.View> implements QuestionPlayContract.Presenter{
    public QuestionPlayPresenter(Context context) {
        super(context);
    }
}
