package com.badao.quiz.function.question.play.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.badao.quiz.R;
import com.badao.quiz.base.adapter.BaseAdapter;
import com.badao.quiz.base.view_holder.BaseViewHolder;
import com.badao.quiz.model.QuestionAnswer;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;

public class SolutionFillTextAdapter extends BaseAdapter<QuestionAnswer, SolutionFillTextAdapter.ViewHolder> {

    public SolutionFillTextAdapter(Context context, List<QuestionAnswer> items) {
        super(context, items);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_solution_fill_text;
    }

    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(ViewHolder holder, QuestionAnswer item, int position) throws JSONException {
        holder.tvContent.setText(item.getContent());
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tvContent)
        TextView tvContent;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
