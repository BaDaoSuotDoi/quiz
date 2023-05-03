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
import com.badao.quiz.function.project.play.adapter.ProjectPlayAdapter;
import com.badao.quiz.function.project.play.presenter.ProjectPlayContract;
import com.badao.quiz.function.project.play.presenter.ProjectPlayPresenter;
import com.badao.quiz.model.Project;
import com.badao.quiz.model.Question;

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
    private int viewMode;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        project = getPresenter().getProject();
        project.setQuestions(getPresenter().getQuestions(project.getID()));
        viewMode = getPresenter().getViewMode();
        Log.e("viewMode", viewMode+"");
        if(viewMode == AppConstants.PROJECT_PLAY){
            updateQuestionPlay();
            updateTime("00:00");
            getPresenter().start();
        }

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update here
                for(Question question: project.getQuestions()){
                    Log.e("Submit", question.toString());
                }
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
}
