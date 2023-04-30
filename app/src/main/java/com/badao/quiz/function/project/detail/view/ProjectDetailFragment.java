package com.badao.quiz.function.project.detail.view;


import android.annotation.SuppressLint;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.dialog.ProjectNameDialog;
import com.badao.quiz.function.project.detail.presenter.ProjectDetailContract;
import com.badao.quiz.function.project.detail.presenter.ProjectDetailPresenter;
import com.badao.quiz.model.Project;
import com.badao.quiz.utils.BundleBuilder;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = ProjectDetailPresenter.class, layout = R.layout.fragment_project_detail, isDisableBack = false)
public class ProjectDetailFragment extends BaseAnnotatedFragment<ProjectDetailContract.View, ProjectDetailContract.Presenter> implements ProjectDetailContract.View{
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.imName)
    ImageView imName;
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

    @BindView(R.id.tvEdit)
    TextView tvEdit;

    Project project;
    int totalQuestion = 0;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        project = getPresenter().getProject();
        init();
        imName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectNameDialog dialog = new ProjectNameDialog(project);
                dialog.show(getParentFragmentManager(), ProjectNameDialog.class.getName());
            }
        });
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

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(R.id.projectQuestionEditFragment, BundleBuilder.bundleOf(
                        Pair.create(AppConstants.PROJECT_ID, project.getID())
                ), AnimationType.FROM_RIGHT_TO_LEFT);
            }
        });

        getViewModel().getMldProjectStatus().observe(this, payload -> {
            if(payload.getAction()  == AppConstants.PROJECT_UPDATE){
                Project project =(Project) payload.getValue();
                this.project = project;
                init();
            }
        });
    }

    public void init(){
        updateName();
        updateRandomMode();
        updateTotalQuestion();
        updateQuestionPerSession();
        updateDuration();
        updateCreatedAt();
        updateUpdatedAt();
    }
    @Override
    public void updateName() {
        tvName.setText(project.getName());
    }

    @Override
    public void updateCreatedAt() {
        tvCreatedAt.setText(project.getCreatedAt());
    }

    @Override
    public void updateUpdatedAt() {
        tvUpdatedAt.setText(project.getLastUpdated());
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

}
