package com.badao.quiz.function.splash.presenter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.badao.quiz.base.mvp.presenter.BasePresenter;

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {
    public SplashPresenter(Context context) {
        super(context);
    }

    @Override
    public void isLogin() {
        getView().configMainView();
    }


}