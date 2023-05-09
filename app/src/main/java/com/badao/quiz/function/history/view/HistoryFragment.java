package com.badao.quiz.function.history.view;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badao.quiz.R;
import com.badao.quiz.base.adapter.BaseAdapter;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.history.adapter.HistoryAdapter;
import com.badao.quiz.function.history.presenter.HistoryContract;
import com.badao.quiz.function.history.presenter.HistoryPresenter;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.utils.BundleBuilder;

import java.util.List;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = HistoryPresenter.class, layout = R.layout.fragment_record_history)
public class HistoryFragment extends BaseAnnotatedFragment<HistoryContract.View, HistoryContract.Presenter> implements HistoryContract.View{
    @BindView(R.id.rcProjectHistory)
    RecyclerView rcProjectHistory;

    private List<HistorySubmit> historySubmits;
    private HistoryAdapter adapter;
    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        historySubmits = getPresenter().getListHistorySubmit();
        for(HistorySubmit historySubmit: historySubmits){
            Log.e("History", historySubmit.toString());
        }
        adapter = new HistoryAdapter(getContext(), historySubmits);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                navigateProjectResult(historySubmits.get(position));
            }
        });

        rcProjectHistory.setAdapter(adapter);
        rcProjectHistory.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void navigateProjectResult(HistorySubmit historySubmit) {
        navigate(R.id.projectPlayFragment, BundleBuilder.bundleOf(
                Pair.create(AppConstants.PROJECT_ID, historySubmit.getProjectId()),
                Pair.create(AppConstants.VIEW_MODE, AppConstants.PROJECT_SHOW_ANSWER),
                Pair.create(AppConstants.HISTORY_PROJECT_ID, historySubmit.getID())
        ), AnimationType.FROM_RIGHT_TO_LEFT);
    }

}
