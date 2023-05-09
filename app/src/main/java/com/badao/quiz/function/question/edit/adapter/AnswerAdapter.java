package com.badao.quiz.function.question.edit.adapter;


import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.badao.quiz.R;
import com.badao.quiz.base.adapter.BaseAdapter;
import com.badao.quiz.base.view_holder.BaseViewHolder;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.model.QuestionAnswer;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;

public class AnswerAdapter extends BaseAdapter<QuestionAnswer, AnswerAdapter.ViewHolder> {

    private List<QuestionAnswer> questionAnswers;
    private int mode;
    private boolean isChecked;
    public AnswerAdapter(Context context, List<QuestionAnswer> items, int mode, boolean isChecked) {
        super(context, items);
        this.questionAnswers = items;
        this.mode = mode;
        this.isChecked = isChecked;
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
        holder.edAnswer.setText(item.getContent());
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
            if(mode == AppConstants.ANSWER_SELECTION){
                imAcross.setVisibility(View.VISIBLE);
                cbAnswer.setVisibility(View.VISIBLE);
            }
            imAcross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Delete answer", getAdapterPosition()+"");
                    if(mode == AppConstants.ANSWER_SELECTION){
                        onItemClick(view, getAdapterPosition());
                    }
                }
            });

            if(isChecked){
                if(edAnswer.getText().toString().isEmpty()){
                    edAnswer.setHint("Empty! ");
                    edAnswer.setHintTextColor(Color.parseColor("#D30C12"));
                }
            }
            edAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    questionAnswers.get(getAdapterPosition()).setContent(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }
    }
}
