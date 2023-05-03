package com.badao.quiz.function.record.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.function.project.play.presenter.ProjectPlayContract;

public class RecordPresenter extends BasePresenter<RecordContract.View> implements RecordContract.Presenter{

    public RecordPresenter(Context context) {
        super(context);
    }
}
