package com.badao.quiz.function.question.play.adapter;

import android.content.Context;
import android.graphics.Color;
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
    private List<QuestionAnswer> questionAnswers;

    private AnswerFillListener answerFillListener;
    public AnswerFillTextAdapter(Context context, List<QuestionAnswer> items, AnswerFillListener answerFillListener) {
        super(context, items);
        this.questionAnswers = items;
        this.answerFillListener = answerFillListener;
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

    }

    public interface AnswerFillListener {
        void onAnswerChange(String content);
    }
    public class ViewHolder extends BaseViewHolder{
        @BindView(R.id.edContent)
        EditText edContent;

        public ViewHolder(View view) {
            super(view);
            edContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String content = charSequence.toString();
                    answerFillListener.onAnswerChange(content);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }
    }
}
