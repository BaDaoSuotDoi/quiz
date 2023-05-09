package com.badao.quiz.function.statistics.view;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.statistics.adapter.StatisticAdapter;
import com.badao.quiz.function.statistics.presenter.StatisticsContract;
import com.badao.quiz.function.statistics.presenter.StatisticsPresenter;
import com.badao.quiz.model.Statistic;

import java.util.List;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = StatisticsPresenter.class, layout = R.layout.fragment_record_statistics)
public class StatisticsFragment extends BaseAnnotatedFragment<StatisticsContract.View, StatisticsContract.Presenter> implements StatisticsContract.View{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        List<Statistic> statistics = getPresenter().getListStatistic();
        for(Statistic statistic: statistics){
            Log.e("Statistic", statistic.toString());
        }
        StatisticAdapter statisticAdapter = new StatisticAdapter(getActivity(), statistics);
        recyclerView.setAdapter(statisticAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
    }
}
