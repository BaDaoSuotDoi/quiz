package com.badao.quiz.function.question.play;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.function.question.play.presenter.QuestionPlayContract;

public class QuestionPlayBasePresenter extends BasePresenter<QuestionPlayBaseContract.View> implements QuestionPlayBaseContract.Presenter{
    public QuestionPlayBasePresenter(Context context) {
        super(context);
    }
}
