package com.badao.quiz.function.question.play.vocabulary.view;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.question.play.QuestionPlayBaseFragment;
import com.badao.quiz.function.question.play.view.QuestionPlayFragment;
import com.badao.quiz.function.question.play.vocabulary.presenter.QuestionVocabularyPlayContract;
import com.badao.quiz.function.question.play.vocabulary.presenter.QuestionVocabularyPlayPresenter;
import com.badao.quiz.model.Question;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;

@ViewInflate(presenter = QuestionVocabularyPlayPresenter.class, layout = R.layout.fragment_question_vocabulary_play)
public class QuestionVocabularyPlayFragment extends QuestionPlayBaseFragment {
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.edAnswer)
    EditText edAnswer;
    @BindView(R.id.tvAnswer)
    TextView tvAnswer;
    @BindView(R.id.tvSolution)
    TextView tvSolution;
    @BindView(R.id.llSolution)
    LinearLayout llSolution;
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.llComment)
    LinearLayout llComment;
    private int position;

    public QuestionVocabularyPlayFragment(Question question, int position, int viewMode) {
        super(question, position, viewMode);
        this.position = position;
    }

    @Override
    public void initMode() {
        Log.e("position",this.position+"...."+ getViewMode());
        if(getViewMode() == AppConstants.PROJECT_SHOW_ANSWER){
           if(tvAnswer != null){
               ViewGroup.LayoutParams layoutParams =  tvAnswer.getLayoutParams();
               layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
               layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
               tvAnswer.setLayoutParams(layoutParams);
               if(getQuestion().getUserAnswers().getStatus() == AppConstants.QUESTION_ANSWER_CORRECT){
                   tvAnswer.setTextColor(Color.parseColor("#23B81E"));
               }else{
                   tvAnswer.setTextColor(Color.parseColor("#D30C12"));
               }
               tvAnswer.setText(getQuestion().getUserAnswers().getAnswer());
           }

           if(edAnswer != null){
               ViewGroup.LayoutParams layoutParams2 =  edAnswer.getLayoutParams();
               layoutParams2.width = 0;
               layoutParams2.height = 0;
               edAnswer.setLayoutParams(layoutParams2);
           }
           if(llSolution != null){
               llSolution.setVisibility(View.VISIBLE);
               tvSolution.setText(getQuestion().getAnswers().get(0).getContent());
           }

           if(llComment != null){
               llComment.setVisibility(View.VISIBLE);
               tvComment.setText(getQuestion().getComment());
           }
        }

        if(getViewMode() == AppConstants.PROJECT_PLAY){
           if(tvAnswer != null){
               ViewGroup.LayoutParams layoutParams =  tvAnswer.getLayoutParams();
               layoutParams.width = 0;
               layoutParams.height = 0;
               tvAnswer.setLayoutParams(layoutParams);
               tvAnswer.setText(getQuestion().getContent());
           }

           if(edAnswer != null){
               ViewGroup.LayoutParams layoutParams2 =  edAnswer.getLayoutParams();
               layoutParams2.width =  ViewGroup.LayoutParams.MATCH_PARENT;
               layoutParams2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
               edAnswer.setLayoutParams(layoutParams2);
           }
        }
    }


    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        initMode();
        Log.e("initMode", this.position+"");
        initAlterLeavePlayDialog();
        tvContent.setText(getQuestion().getContent());
        edAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getQuestion().getUserAnswers().setAnswer(charSequence.toString());
                getViewModel().getMlUserChangeAnswer().postValue(
                        new MainActivityVM.Payload(AppConstants.USER_CHANGE_ANSWER, new QuestionUserAnswer(getQuestion().getPosition(),  charSequence.toString())));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}
