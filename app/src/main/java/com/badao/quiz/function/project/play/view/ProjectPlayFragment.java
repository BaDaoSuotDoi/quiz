package com.badao.quiz.function.project.play.view;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.viewpager2.widget.ViewPager2;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.QuestionAnswerDB;
import com.badao.quiz.db.QuestionDB;
import com.badao.quiz.function.main.view.MainActivity;
import com.badao.quiz.function.project.play.adapter.ProjectPlayAdapter;
import com.badao.quiz.function.project.play.presenter.ProjectPlayContract;
import com.badao.quiz.function.project.play.presenter.ProjectPlayPresenter;
import com.badao.quiz.model.HistorySubmit;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;
import com.badao.quiz.model.RecordUserAnswer;
import com.badao.quiz.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = ProjectPlayPresenter.class, layout = R.layout.fragment_project_play)
public class ProjectPlayFragment  extends BaseAnnotatedFragment<ProjectPlayContract.View, ProjectPlayContract.Presenter> implements ProjectPlayContract.View{
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.vpQuestionPlay)
    ViewPager2 vpQuestionPlay;
    @BindView(R.id.tvLeft)
    TextView tvLeft;
    @BindView(R.id.tvQuestionIndex)
    TextView tvQuestionIndex;
    @BindView(R.id.tvRight)
    TextView tvRight;

    private Project project;
    private ProjectPlayAdapter adapter;

    private HistorySubmit historySubmit;

    private int viewMode;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        project = getPresenter().getProject();
        viewMode = getPresenter().getViewMode();
        Log.e("viewMode", viewMode+"");

        initViewMode();
        updateQuestionPlay();
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().submit(project);
                Log.e("Submit", "OK");
                adapter.setViewMode(AppConstants.PROJECT_SHOW_ANSWER);
                ((MainActivity)getActivity()).hiddenKeyboard();
            }
        });
    }

    @Override
    public void updateTime(String time) {
        tvTime.setText(time);
    }

    @Override
    public void updateQuestionPlay() {
        adapter = new ProjectPlayAdapter(getActivity(),project, viewMode);
        vpQuestionPlay.setAdapter(adapter);
        vpQuestionPlay.setOffscreenPageLimit(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().stopTime();
    }

    public void initViewMode(){
        if(viewMode == AppConstants.PROJECT_PLAY){
            project.setQuestions(getPresenter().getQuestions(project.getID()));

            updateTime("00:00");
            getPresenter().start();
        }else if(viewMode == AppConstants.PROJECT_SHOW_ANSWER){
            historySubmit = getPresenter().getHistorySubmit();
            List<RecordUserAnswer> recordUserAnswers =getPresenter().getUserAnswers();
            List<Question> questions = new ArrayList<>();
            for(RecordUserAnswer recordUserAnswer: recordUserAnswers){
                Question question = QuestionDB.getInstance(getContext()).findByPk(recordUserAnswer.getQuestionId());
                question.setAnswers(QuestionAnswerDB.getInstance(getContext()).findBy(question.getID()));
                question.setUserAnswers(recordUserAnswer);
                questions.add(question);
                Log.e("Question result", question.toString());
            }

            project.setQuestions(questions);
            updateTime(Utils.displayTime(historySubmit.getTimeElapsed()));
        }
    }
}
