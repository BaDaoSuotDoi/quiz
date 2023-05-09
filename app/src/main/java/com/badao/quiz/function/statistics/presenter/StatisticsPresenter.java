package com.badao.quiz.function.statistics.presenter;

import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.model.Statistic;

import java.util.List;

public class StatisticsPresenter extends BasePresenter<StatisticsContract.View> implements StatisticsContract.Presenter{
    public StatisticsPresenter(Context context) {
        super(context);
    }

    @Override
    public List<Statistic> getListStatistic() {
        return ProjectDB.getInstance(getContext()).statisticAllProject();
    }
}
