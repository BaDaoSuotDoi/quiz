package com.badao.quiz.function.statistics.presenter;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.model.Statistic;

import java.util.List;

public class StatisticsContract {
    public interface View extends BaseContract.View {
    }

    public interface Presenter extends BaseContract.Presenter<StatisticsContract.View> {
        List<Statistic> getListStatistic();
    }
}
