package com.badao.quiz.function.project_detail.view;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.project_detail.presenter.ProjectDetailContract;
import com.badao.quiz.function.project_detail.presenter.ProjectDetailPresenter;
import com.badao.quiz.model.Project;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = ProjectDetailPresenter.class, layout = R.layout.fragment_project_detail, isDisableBack = false)
public class ProjectDetailFragment extends BaseAnnotatedFragment<ProjectDetailContract.View, ProjectDetailContract.Presenter> implements ProjectDetailContract.View{
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCreatedAt)
    TextView tvCreatedAt;
    @BindView(R.id.tvUpdatedAt)
    TextView tvUpdatedAt;
    @BindView(R.id.tvTotalQuestion)
    TextView tvTotalQuestion;

//    @BindView(R.id.imTotalQuestion)
//    ImageView imTotalQuestion;

    @BindView(R.id.tvRandomMode)
    TextView tvRandomMode;
    @BindView(R.id.imRandomMode)
    ImageView imRandomMode;

    @BindView(R.id.tvQuestionPerSession)
    TextView tvQuestionPerSession;

    @BindView(R.id.imQuestionPerSession)
    ImageView imQuestionPerSession;

    @BindView(R.id.tvDuration)
    TextView tvDuration;

    @BindView(R.id.imDuration)
    ImageView imDuration;

    Project project;
    int totalQuestion = 0;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        project = getPresenter().getProject();
        updateRandomMode();
        updateTotalQuestion();
        updateQuestionPerSession();
        updateDuration();

        imRandomMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), imRandomMode);
                popupMenu.inflate(R.menu.menu_choice);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.action_yes:
                                Log.e("Project Action", "yes");
                                return true;
                            case R.id.action_no:
                                Log.e("Project Action", "no");
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        imQuestionPerSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public void updateRandomMode() {
        tvRandomMode.setText(String.valueOf(project.isRandom()));
    }

    @Override
    public void updateTotalQuestion() {
        tvTotalQuestion.setText(String.valueOf(totalQuestion));
    }

    @Override
    public void updateQuestionPerSession() {
        tvQuestionPerSession.setText(String.valueOf(project.getQuestionPerSession()));
    }

    @Override
    public void updateDuration() {
        tvDuration.setText(String.valueOf(project.getDuration()));
    }

    @Override
    protected void onBackHardwareClicked() {
        super.onBackHardwareClicked();
        Log.d("Hacker", "--->1");
    }

    @Override
    protected void onBack() {
        super.onBack();
        Log.d("Hacker", "--->2");
    }
}
