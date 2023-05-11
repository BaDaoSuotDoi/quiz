package com.badao.quiz.function.question.play.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badao.quiz.R;
import com.badao.quiz.base.adapter.BaseAdapter;
import com.badao.quiz.base.view_holder.BaseViewHolder;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.model.RecordUserAnswer;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;

public class SolutionSelectionAdapter extends BaseAdapter<QuestionAnswer, SolutionSelectionAdapter.ViewHolder> {
    private List<QuestionAnswer> questionAnswers;
    public SolutionSelectionAdapter(Context context, List<QuestionAnswer> items) {
        super(context, items);
        this.questionAnswers = items;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_user_selection;
    }

    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(ViewHolder holder, QuestionAnswer item, int position) throws JSONException {
        String display = item.getContent().substring(2);
        holder.tvAnswer.setText(display);
        if(item.getContent().startsWith(AppConstants.TOKEN_TRUE_SELECT_ANSWER)){
            holder.imCheck.setImageResource(R.drawable.ic_checked);
        }
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.imCheck)
        ImageView imCheck;

        @BindView(R.id.tvAnswer)
        TextView tvAnswer;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
