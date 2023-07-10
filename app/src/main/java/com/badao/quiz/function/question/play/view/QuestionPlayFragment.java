package com.badao.quiz.function.question.play.view;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.question.dialog.AlterLeavePlayDialog;
import com.badao.quiz.function.question.edit.presenter.QuestionEditPresenter;
import com.badao.quiz.function.question.play.QuestionPlayBaseFragment;
import com.badao.quiz.function.question.play.QuestionPlayBasePresenter;
import com.badao.quiz.function.question.play.adapter.AnswerFillTextAdapter;
import com.badao.quiz.function.question.play.adapter.AnswerSelectionAdapter;
import com.badao.quiz.function.question.play.adapter.SolutionFillTextAdapter;
import com.badao.quiz.function.question.play.adapter.SolutionSelectionAdapter;
import com.badao.quiz.function.question.play.adapter.UserAnswerFillTextAdapter;
import com.badao.quiz.function.question.play.adapter.UserSelectionAdapter;
import com.badao.quiz.function.question.play.presenter.QuestionPlayContract;
import com.badao.quiz.function.question.play.presenter.QuestionPlayPresenter;
import com.badao.quiz.model.Question;

import butterknife.BindView;

@ViewInflate(presenter = QuestionPlayBasePresenter.class, layout = R.layout.fragment_question_play)
public class QuestionPlayFragment extends QuestionPlayBaseFragment {

    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.rcAnswer)
    RecyclerView rcAnswer;

    @BindView(R.id.rcSolution)
    RecyclerView rcSolution;
    @BindView(R.id.llSolution)
    LinearLayout llSolution;

    private  int position;
    private Question question;

    public QuestionPlayFragment(Question question, int position, int viewMode) {
        super(question, position, viewMode);
        this.position = position;
        this.question = question;
    }

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        updateContent();
        initMode();
        initAlterLeavePlayDialog();
        if(getViewMode() == AppConstants.PROJECT_SHOW_ANSWER){
            if(!question.getComment().isEmpty()){
                updateComment();
            }
            updateSolution(true);
        }
    }

    @Override
    public void initMode() {
        if(llSolution == null){
            return;
        }

        if(getViewMode() == AppConstants.PROJECT_PLAY){
            if(question.getType() == AppConstants.QUESTION_NORMAL_TYPE){
                AnswerFillTextAdapter answerFillTextAdapter = new AnswerFillTextAdapter(getActivity(), question.getAnswers(),question.getUserAnswers(), new AnswerFillTextAdapter.AnswerFillListener() {
                    @Override
                    public void onAnswerChange(String content) {
                        getViewModel().getMlUserChangeAnswer().postValue(
                                new MainActivityVM.Payload(AppConstants.USER_CHANGE_ANSWER, new QuestionUserAnswer(question.getPosition(), content)));
                    }
                });
                rcAnswer.setAdapter(answerFillTextAdapter);
                rcAnswer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
            }else{
                Log.e("QUESTION_SELECTION_TYPE","OK");
                AnswerSelectionAdapter answerSelectionAdapter = new AnswerSelectionAdapter(getContext(), question.getAnswers(), question.getUserAnswers(), new AnswerSelectionAdapter.IListener() {
                    @Override
                    public void onSelect(boolean isSelect) {
                        getViewModel().getMlUserChangeAnswer().postValue(
                                new MainActivityVM.Payload(AppConstants.USER_CHANGE_ANSWER, new QuestionUserAnswer(question.getPosition(), isSelect ? "ok": "")));
                    }
                });
                rcAnswer.setAdapter(answerSelectionAdapter);
                rcAnswer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
            }
        }else if(getViewMode() == AppConstants.PROJECT_SHOW_ANSWER){
            Log.e("PROJECT_SHOW_ANSWER", "OK");
            if(question.getType() == AppConstants.QUESTION_NORMAL_TYPE){
                UserAnswerFillTextAdapter userAnswerFillTextAdapter = new UserAnswerFillTextAdapter(getActivity(), question.getAnswers(), question.getUserAnswers());
                rcAnswer.setAdapter(userAnswerFillTextAdapter);
                rcAnswer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));

                updateSolution(true);
                SolutionFillTextAdapter solutionFillTextAdapter = new SolutionFillTextAdapter(getContext(), question.getAnswers());
                rcSolution.setAdapter(solutionFillTextAdapter);
                rcSolution.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
            }else{
                UserSelectionAdapter userSelectionAdapter = new UserSelectionAdapter(getActivity(), question.getAnswers(), question.getUserAnswers());
                rcAnswer.setAdapter(userSelectionAdapter);
                rcAnswer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));

                updateSolution(true);
                SolutionSelectionAdapter solutionSelectionAdapter = new SolutionSelectionAdapter(getContext(), question.getAnswers());
                rcSolution.setAdapter(solutionSelectionAdapter);
                rcSolution.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
            }
        }
    }

    public void updateContent() {
        tvContent.setText(question.getContent());
    }

    public void updateComment() {
        ViewGroup.LayoutParams layoutParams = this.tvComment.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.tvComment.setLayoutParams(layoutParams);
        this.tvComment.setText(question.getComment());
    }

    public void updateSolution(boolean isShow) {
        if(isShow){
            llSolution.setVisibility(View.VISIBLE);
        }else{
            llSolution.setVisibility(View.INVISIBLE);
        }
    }



}
