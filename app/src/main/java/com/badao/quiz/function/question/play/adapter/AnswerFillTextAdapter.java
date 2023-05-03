package com.badao.quiz.function.question.play.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        if(viewMode == AppConstants.PROJECT_SHOW_ANSWER){
            Log.e("Reload data", recordUserAnswer.getAnswer());
            holder.tvContent.setText(recordUserAnswer.getAnswer());
            holder.showSolution();
        }else{
            holder.showAnswer();
        }
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

        }

        public void showSolution(){
            tvContent.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));
            edContent.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
        }

        public void showAnswer(){
            edContent.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));
            tvContent.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
        }
    }
}
