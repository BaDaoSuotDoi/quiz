package com.badao.quiz.function.question.play.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class AnswerFillTextAdapter extends BaseAdapter<QuestionAnswer, AnswerFillTextAdapter.ViewHolder> {
    private int viewMode;
    private List<QuestionAnswer> questionAnswers;

    private RecordUserAnswer recordUserAnswer;

    public AnswerFillTextAdapter(Context context, List<QuestionAnswer> items,RecordUserAnswer recordUserAnswer, int viewMode) {
        super(context, items);
        this.questionAnswers = items;
        this.recordUserAnswer = recordUserAnswer;
        this.viewMode = viewMode;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_answer_fill_text;
    }

    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(ViewHolder holder, QuestionAnswer item, int position) throws JSONException {
        holder.tvContent.setText(item.getContent());
    }

    public void setViewMode(int viewMode) {
        this.viewMode = viewMode;
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder{
        @BindView(R.id.edContent)
        EditText edContent;

        @BindView(R.id.tvContent)
        TextView tvContent;

        public ViewHolder(View view) {
            super(view);
            if(viewMode == AppConstants.PROJECT_PLAY){
                edContent.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                tvContent.setLayoutParams(new LinearLayout.LayoutParams(0,0));

                edContent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        recordUserAnswer.setAnswer(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }else{
                tvContent.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                edContent.setLayoutParams(new ViewGroup.LayoutParams(0,0));
            }

        }

    }
}
