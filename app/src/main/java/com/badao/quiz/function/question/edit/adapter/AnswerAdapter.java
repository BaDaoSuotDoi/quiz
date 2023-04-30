package com.badao.quiz.function.question.edit.adapter;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.badao.quiz.R;
import com.badao.quiz.base.adapter.BaseAdapter;
import com.badao.quiz.base.view_holder.BaseViewHolder;
import com.badao.quiz.model.QuestionAnswer;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;

public class AnswerAdapter extends BaseAdapter<QuestionAnswer, AnswerAdapter.ViewHolder> {

    public AnswerAdapter(Context context, List<QuestionAnswer> items) {
        super(context, items);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_question_answer;
    }

    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(ViewHolder holder, QuestionAnswer item, int position) throws JSONException {

    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.cbAnswer)
        CheckBox cbAnswer;
        @BindView(R.id.edAnswer)
        EditText edAnswer;
        @BindView(R.id.imAcross)
        ImageView imAcross;

        public ViewHolder(View view) {
            super(view);
            imAcross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Delete answer", getAdapterPosition()+"");
                    onItemClick(view, getAdapterPosition());
                }
            });
        }
    }
}
