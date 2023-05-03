package com.badao.quiz.function.statistics.view;

import android.annotation.SuppressLint;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.record.presenter.RecordPresenter;
import com.badao.quiz.function.statistics.presenter.StatisticsContract;
import com.badao.quiz.function.statistics.presenter.StatisticsPresenter;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = StatisticsPresenter.class, layout = R.layout.fragment_record_statistics)
public class StatisticsFragment extends BaseAnnotatedFragment<StatisticsContract.View, StatisticsContract.Presenter> implements StatisticsContract.View{
}
