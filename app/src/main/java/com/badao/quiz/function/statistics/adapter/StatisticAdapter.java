package com.badao.quiz.function.statistics.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.badao.quiz.R;
import com.badao.quiz.base.adapter.BaseAdapter;
import com.badao.quiz.base.view_holder.BaseViewHolder;
import com.badao.quiz.function.history.adapter.HistoryAdapter;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Statistic;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;

public class StatisticAdapter extends BaseAdapter<Statistic, StatisticAdapter.ViewHolder> {

    public StatisticAdapter(Context context, List<Statistic> items) {
        super(context, items);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_project_statistics;
    }

    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(ViewHolder holder, Statistic item, int position) throws JSONException {
        holder.tvProjectName.setText(item.getProjectName());
        holder.tvNumberPlayed.setText("Played "+ item.getNumberPlayed());
        holder.tvNumberCorrect.setText(item.getNumberCorrect()+"/"+item.getNumberAnswer());
        if(item.getNumberAnswer() > 0){
            holder.skProgress.setProgress((int)(item.getNumberCorrect() * 100)/ item.getNumberAnswer());
        }else{
            holder.skProgress.setProgress(0);
        }
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tvProjectName)
        TextView tvProjectName;
        @BindView(R.id.tvNumberPlayed)
        TextView tvNumberPlayed;
        @BindView(R.id.tvNumberCorrect)
        TextView tvNumberCorrect;
        @BindView(R.id.skProgress)
        ProgressBar skProgress;

        public ViewHolder(View view) {
            super(view);
        }

    }
}
