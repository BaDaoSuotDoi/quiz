package com.badao.quiz.function.project.play.challenge.view;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.viewpager2.widget.ViewPager2;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.project.play.adapter.ProjectPlayAdapter;
import com.badao.quiz.function.project.play.presenter.ProjectPlayContract;
import com.badao.quiz.function.project.play.presenter.ProjectPlayPresenter;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@ViewInflate(presenter = ProjectPlayPresenter.class, layout = R.layout.fragment_project_challenge_play)
public class ProjectChallengePlayFragment extends BaseAnnotatedFragment<ProjectPlayContract.View, ProjectPlayContract.Presenter> implements ProjectPlayContract.View{
    @BindView(R.id.imPrevious)
    ImageView imPrevious;
    @BindView(R.id.tvNumberCorrect)
    TextView tvNumberCorrect;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvQuestionIndex)
    TextView tvQuestionIndex;
    @BindView(R.id.imQuit)
    ImageView imQuit;
    @BindView(R.id.vpQuestionPlay)
    ViewPager2 vpQuestionPlay;
    @BindView(R.id.tvLeft)
    TextView tvLeft;
    @BindView(R.id.tvRight)
    TextView tvRight;
    private ProjectPlayAdapter adapter;
    private Project project;
    private int viewMode;
    private List<Question> questions;
    private int questionCurrentIndex = 0;
    private int statusAnswer = AppConstants.PROJECT_CHALLENGE_ANSWERING;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        viewMode = getPresenter().getViewMode();
        project = getPresenter().getProject();
        questions = getPresenter().getQuestions(project.getId());
        if(project.getIsRandom()){
            List<Question> questionRandom = new ArrayList<>();
            List<Integer> randomIntArray = Utils.generateRandomIntList(questions.size(), project.getQuestionPerSession());
            for(int index: randomIntArray){
                questionRandom.add(questions.get(index));
            }
            questions = questionRandom;
        }
        project.setQuestions(questions);
        for(Question question: questions){
            if(question.getType() == AppConstants.QUESTION_VOCABULARY_TYPE){
                question.setContent(Utils.randomHiddenStr(question.getContent()));
            }
        }
        adapter = new ProjectPlayAdapter(getActivity(),project, viewMode);
        vpQuestionPlay.setUserInputEnabled(false);
        vpQuestionPlay.setAdapter(adapter);
        vpQuestionPlay.setOffscreenPageLimit(1);
        tvQuestionIndex.setText((questionCurrentIndex+1)+"/"+questions.size());
        imPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

        imQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show correct answer
               getPresenter().checkAnswerQuestion(questions.get(questionCurrentIndex));
                setViewMode(AppConstants.PROJECT_SHOW_ANSWER, questionCurrentIndex);
                tvRight.setText("Next");
            }
        });

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("questionCurrentIndex",questionCurrentIndex+"");
                if(questionCurrentIndex  >= questions.size()){
                    return;
                }
                if(statusAnswer == AppConstants.PROJECT_CHALLENGE_ANSWERING){
                    int status = getPresenter().checkAnswerQuestion(questions.get(questionCurrentIndex));
                    if(status == AppConstants.QUESTION_ANSWER_CORRECT){
                        tvRight.setText("Next");
                        statusAnswer = AppConstants.PROJECT_CHALLENGE_ANSWERED;
                        setViewMode(AppConstants.PROJECT_SHOW_ANSWER, questionCurrentIndex);
                    }else{
                        Toast.makeText(getContext(),"Wrong answer!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    tvRight.setText("Submit");
                    statusAnswer = AppConstants.PROJECT_CHALLENGE_ANSWERING;
                    tvQuestionIndex.setText((questionCurrentIndex+1)+"/"+questions.size());
                    if(questionCurrentIndex >= questions.size()){
                        return;
                    }
                    vpQuestionPlay.setCurrentItem(questionCurrentIndex+1);
                    questionCurrentIndex++;
                }
            }
        });

//        adapter = new ProjectPlayAdapter(getActivity(),project, viewMode);
//        vpQuestionPlay.setAdapter(adapter);
//        vpQuestionPlay.setOffscreenPageLimit(1);

    }

    @Override
    public void updateTime(String time) {
        tvTime.setText(time);
    }

    @Override
    public void updateQuestionPlay() {

    }
    public void setViewMode(int viewMode, int idx) {
        this.viewMode = viewMode;
        adapter.setViewMode(viewMode, idx);
    }
    @Override
    public void changeViewSubmit(HistorySubmit historySubmit) {

    }
}
