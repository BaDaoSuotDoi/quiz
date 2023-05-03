package com.badao.quiz.function.question.play.view;

import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
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
    @BindView(R.id.rcAnswer)
    RecyclerView rcAnswer;

    @BindView(R.id.rcSolution)
    RecyclerView rcSolution;

    private int viewMode;
    private  int position;
    private Question question;

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
    }

    @Override
    public void updateListAnswer() {
        AnswerFillTextAdapter adapter = new AnswerFillTextAdapter(getActivity(), question.getAnswers(),question.getUserAnswers(),this.viewMode);
        Log.e("Update adapter", "Ok");
        rcAnswer.setAdapter(adapter);
        rcAnswer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void updateContent() {
        tvContent.setText(question.getContent());
    }
}
