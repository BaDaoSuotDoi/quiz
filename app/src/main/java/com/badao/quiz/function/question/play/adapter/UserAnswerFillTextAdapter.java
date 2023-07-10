package com.badao.quiz.function.question.play.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
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

public class UserAnswerFillTextAdapter  extends BaseAdapter<QuestionAnswer, UserAnswerFillTextAdapter.ViewHolder> {
    private  RecordUserAnswer recordUserAnswer;

    public UserAnswerFillTextAdapter(Context context, List<QuestionAnswer> items, RecordUserAnswer recordUserAnswer) {
        super(context, items);
        this.recordUserAnswer = recordUserAnswer;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_user_answer_fill_text;
    }

    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(ViewHolder holder, QuestionAnswer item, int position) throws JSONException {
        holder.tvUserAnswer.setText(recordUserAnswer.getAnswer());
        if(recordUserAnswer.getStatus() == AppConstants.QUESTION_ANSWER_CORRECT){
            holder.tvUserAnswer.setTextColor(Color.parseColor("#23B81E"));
        }else if(recordUserAnswer.getStatus() == AppConstants.QUESTION_ANSWER_WRONG){
            holder.tvUserAnswer.setTextColor(Color.parseColor("#D30C12"));
            if(recordUserAnswer.getAnswer().isEmpty()){
                holder.tvUserAnswer.setText("Empty!");
            }
        }
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tvUserAnswer)
        TextView tvUserAnswer;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
