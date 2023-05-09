package com.badao.quiz.function.history.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badao.quiz.R;
import com.badao.quiz.base.adapter.BaseAdapter;
import com.badao.quiz.base.view_holder.BaseViewHolder;
import com.badao.quiz.function.question.edit.adapter.AnswerAdapter;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.utils.Utils;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;

public class HistoryAdapter extends BaseAdapter<HistorySubmit, HistoryAdapter.ViewHolder> {
    public HistoryAdapter(Context context, List<HistorySubmit> items) {
        super(context, items);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_project_history;
    }

    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void bindView(ViewHolder holder, HistorySubmit item, int position) throws JSONException {
        int numberCorrect = item.getCorrectAnswerNumber();
        int numberNoAnswer = item.getNoAnswerNumber();
        int numberWrong = item.getQuestionNumber() - numberCorrect - numberNoAnswer;
        holder.tvProjectName.setText(item.getProject().getName());
        holder.tvPercent.setText("30%");
        holder.tvSubmittedAt.setText(item.getSubmittedAt());
        holder.tvTimeElapsed.setText(Utils.displayTime(item.getTimeElapsed()));
        holder.tvNumberCorrect.setText(String.valueOf(numberCorrect));
        holder.tvNumberWrong.setText(String.valueOf(numberWrong));
        holder.tvNumberNoAnswer.setText(String.valueOf(numberNoAnswer));
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener{
        @BindView(R.id.rlProjectHistory)
        RelativeLayout rlProjectHistory;
        @BindView(R.id.tvProjectName)
        TextView tvProjectName;
        @BindView(R.id.tvPercent)
        TextView tvPercent;
        @BindView(R.id.tvSubmittedAt)
        TextView tvSubmittedAt;
        @BindView(R.id.tvTimeElapsed)
        TextView tvTimeElapsed;
        @BindView(R.id.tvNumberCorrect)
        TextView tvNumberCorrect;
        @BindView(R.id.tvNumberWrong)
        TextView tvNumberWrong;
        @BindView(R.id.tvNumberNoAnswer)
        TextView tvNumberNoAnswer;
        public ViewHolder(View view) {
            super(view);
            rlProjectHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick(view, getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {

        }

    }
}
