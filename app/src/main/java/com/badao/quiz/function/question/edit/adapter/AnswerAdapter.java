package com.badao.quiz.function.question.edit.adapter;


import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        if(mode == AppConstants.QUESTION_SELECTION_TYPE){
            String content = item.getContent();
            String display = content.substring(2);
            if(content.startsWith(AppConstants.TOKEN_TRUE_SELECT_ANSWER)){
                holder.cbAnswer.setChecked(true);
            }else{
                holder.cbAnswer.setChecked(false);
            }
            holder.edAnswer.setText(display);
        }else{
            holder.edAnswer.setText(item.getContent());
        }
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
            if(mode == AppConstants.QUESTION_SELECTION_TYPE){
                imAcross.setVisibility(View.VISIBLE);
                cbAnswer.setVisibility(View.VISIBLE);
            }

            imAcross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Delete answer", getAdapterPosition()+"//"+mode);
                    if(mode == AppConstants.QUESTION_SELECTION_TYPE){
                        onItemClick(view, getAdapterPosition());
                    }
                }
            });

            // check when save question
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
                    QuestionAnswer questionAnswer = questionAnswers.get(getAdapterPosition());
                    String content = questionAnswer.getContent();
                    if(questionAnswer.getType() == AppConstants.QUESTION_SELECTION_TYPE){
                        if(content.startsWith(AppConstants.TOKEN_TRUE_SELECT_ANSWER)){
                            questionAnswer.setContent(AppConstants.TOKEN_TRUE_SELECT_ANSWER + charSequence.toString());
                        }else{
                            questionAnswer.setContent(AppConstants.TOKEN_FALSE_SELECT_ANSWER + charSequence.toString());
                        }
                    }else{
                        questionAnswer.setContent( charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            cbAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuestionAnswer questionAnswer = questionAnswers.get(getAdapterPosition());
                    String content = questionAnswer.getContent();
                    Log.e("Before", content);
                    if(content.startsWith(AppConstants.TOKEN_TRUE_SELECT_ANSWER)){
                        content = content.replace(AppConstants.TOKEN_TRUE_SELECT_ANSWER, AppConstants.TOKEN_FALSE_SELECT_ANSWER);
                    }else{
                        content = content.replace(AppConstants.TOKEN_FALSE_SELECT_ANSWER, AppConstants.TOKEN_TRUE_SELECT_ANSWER);
                    }
                    Log.e("After", content);
                    questionAnswer.setContent(content);
                }
            });

        }
    }
}
