package com.badao.quiz.function.history.view;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.history.adapter.HistoryAdapter;
import com.badao.quiz.function.history.presenter.HistoryContract;
import com.badao.quiz.function.history.presenter.HistoryPresenter;
import com.badao.quiz.function.record.presenter.RecordPresenter;
import com.badao.quiz.model.HistorySubmit;

import java.util.List;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = HistoryPresenter.class, layout = R.layout.fragment_record_history)
public class HistoryFragment extends BaseAnnotatedFragment<HistoryContract.View, HistoryContract.Presenter> implements HistoryContract.View{
    @BindView(R.id.rcProjectHistory)
    RecyclerView rcProjectHistory;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        List<HistorySubmit> historySubmits = getPresenter().getListHistorySubmit();
        for(HistorySubmit historySubmit: historySubmits){
            Log.e("History", historySubmit.toString());
        }
        HistoryAdapter adapter = new HistoryAdapter(getContext(), historySubmits);
        rcProjectHistory.setAdapter(adapter);
        rcProjectHistory.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
    }
}
