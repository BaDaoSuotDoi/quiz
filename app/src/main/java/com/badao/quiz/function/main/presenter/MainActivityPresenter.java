package com.badao.quiz.function.main.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.badao.quiz.base.mvp.presenter.BasePresenter;

public class MainActivityPresenter extends BasePresenter<MainActivityContract.View>
        implements MainActivityContract.Presenter {

    public MainActivityPresenter(Context context) {
        super(context);

    }


}
