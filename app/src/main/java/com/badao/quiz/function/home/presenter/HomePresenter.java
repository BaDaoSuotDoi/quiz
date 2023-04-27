package com.badao.quiz.function.home.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;


public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{

    public HomePresenter(Context context) {
        super(context);
    }
}
