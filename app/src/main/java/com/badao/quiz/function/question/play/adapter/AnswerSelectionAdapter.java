package com.badao.quiz.function.question.play.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.badao.quiz.R;
import com.badao.quiz.base.adapter.BaseAdapter;
import com.badao.quiz.base.view_holder.BaseViewHolder;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.model.QuestionAnswer;
import com.badao.quiz.model.RecordUserAnswer;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class AnswerSelectionAdapter extends BaseAdapter<QuestionAnswer, AnswerSelectionAdapter.ViewHolder> {
    private RecordUserAnswer recordUserAnswer;
    private List<QuestionAnswer> questionAnswers;
    private IListener iListener;
    public AnswerSelectionAdapter(Context context, List<QuestionAnswer> items, RecordUserAnswer recordUserAnswer,IListener iListener) {
        super(context, items);
        this.recordUserAnswer = recordUserAnswer;
        this.questionAnswers = items;
        this.iListener = iListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_answer_selection;
    }

    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(ViewHolder holder, QuestionAnswer item, int position) throws JSONException {
        String content = item.getContent();
        String display = content.substring(2);
        holder.tvAnswer.setText(display);
    }

    public interface IListener{
        void onSelect(boolean isSelect);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.cbAnswer)
        CheckBox cbAnswer;
        @BindView(R.id.tvAnswer)
        TextView tvAnswer;

        public ViewHolder(View view) {
            super(view);
            cbAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUserAnswer();
                }
            });

            tvAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUserAnswer();
                }
            });
        }
        void updateUserAnswer(){
            String content = recordUserAnswer.getAnswer();
            String[] items = content.split(AppConstants.TOKEN_SPLIT_ANSWER_USER_SELECTION);
            QuestionAnswer questionAnswer = questionAnswers.get(getAdapterPosition());
            ArrayList<String> newAnswers = new ArrayList<>();
            boolean isAnswered = false;
            for(int i=0; i< items.length ; i++){
                if(items[i].equals(String.valueOf(questionAnswer.getId()))){
                    isAnswered = true;
                }else if(!items[i].isEmpty()){
                    newAnswers.add(items[i]);
                }
            }
            if(!isAnswered){
                newAnswers.add(String.valueOf(questionAnswer.getId()));
            }
            iListener.onSelect(newAnswers.size() != 0);
            recordUserAnswer.setAnswer(String.join(AppConstants.TOKEN_SPLIT_ANSWER_USER_SELECTION, newAnswers));
        }
    }

}
