package com.badao.quiz.function.question.play.view;

import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.question.edit.presenter.QuestionEditPresenter;
import com.badao.quiz.function.question.play.adapter.AnswerFillTextAdapter;
import com.badao.quiz.function.question.play.presenter.QuestionPlayContract;
import com.badao.quiz.model.Question;

import butterknife.BindView;

@ViewInflate(presenter = QuestionEditPresenter.class, layout = R.layout.fragment_question_play)
public class QuestionPlayFragment extends BaseAnnotatedFragment<QuestionPlayContract.View, QuestionPlayContract.Presenter>
        implements QuestionPlayContract.View{

    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.rcAnswer)
    RecyclerView rcAnswer;

    @BindView(R.id.rcSolution)
    RecyclerView rcSolution;

    private int viewMode;
    private  int position;
    private Question question;
    private AnswerFillTextAdapter adapter;

    public QuestionPlayFragment(int position, Question question, int viewMode){
        this.position = position;
        this.question = question;
        this.viewMode = viewMode;
    }

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        updateContent();
        updateListAnswer();
        Log.e("ViewMode question play", this.viewMode+"");
        if(this.viewMode == AppConstants.PROJECT_SHOW_ANSWER){
            updateComment();
        }
    }

    @Override
    public void updateListAnswer() {
        adapter = new AnswerFillTextAdapter(getActivity(), question.getAnswers(), question.getUserAnswers(), this.viewMode, new AnswerFillTextAdapter.AnswerFillListener() {
            @Override
            public void onAnswerChange(String content) {

                getViewModel().getMlUserChangeAnswer().postValue(
                        new MainActivityVM.Payload(AppConstants.USER_CHANGE_ANSWER, new QuestionUserAnswer(question.getPosition(), !content.isEmpty())));
            }
        });
        rcAnswer.setAdapter(adapter);
        rcAnswer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void updateContent() {
        tvContent.setText(question.getContent());
    }

    @Override
    public void updateComment() {
        Log.e("Element", "CC"+question.getComment());
        this.tvComment.setText(question.getComment());
    }

    public void setViewMode(int viewMode){
        this.viewMode = viewMode;
        if(this.viewMode == AppConstants.PROJECT_SHOW_ANSWER && this.tvComment != null){
            updateComment();
            Log.e("ViewMode 2", this.viewMode+"");
        }
        if(adapter != null){
            adapter.setViewMode(viewMode);
        }
    }

    public class QuestionUserAnswer{
        private int questionPosition;
        private boolean isAnswer;

        public QuestionUserAnswer(int questionPosition, boolean isAnswer) {
            this.questionPosition = questionPosition;
            this.isAnswer = isAnswer;
        }

        public int getQuestionPosition() {
            return questionPosition;
        }

        public void setQuestionPosition(int questionPosition) {
            this.questionPosition = questionPosition;
        }

        public boolean isAnswer() {
            return isAnswer;
        }

        public void setAnswer(boolean answer) {
            isAnswer = answer;
        }
    }
}
