package com.badao.quiz.function.statistics.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;

public class StatisticsPresenter extends BasePresenter<StatisticsContract.View> implements StatisticsContract.Presenter{
    public StatisticsPresenter(Context context) {
        super(context);
    }
}
