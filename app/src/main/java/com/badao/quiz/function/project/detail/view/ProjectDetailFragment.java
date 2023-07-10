package com.badao.quiz.function.project.detail.view;


import android.annotation.SuppressLint;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.dialog.ProjectNameDialog;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.project.detail.dialog.QuestionPerSessionDialog;
import com.badao.quiz.function.project.detail.dialog.ScheduleDialog;
import com.badao.quiz.function.project.detail.dialog.SetupTimeDurationDialog;
import com.badao.quiz.function.project.detail.presenter.ProjectDetailContract;
import com.badao.quiz.function.project.detail.presenter.ProjectDetailPresenter;
import com.badao.quiz.model.Project;
import com.badao.quiz.service.NotificationService;
import com.badao.quiz.utils.BundleBuilder;
import com.badao.quiz.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = ProjectDetailPresenter.class, layout = R.layout.fragment_project_detail)
public class ProjectDetailFragment extends BaseAnnotatedFragment<ProjectDetailContract.View, ProjectDetailContract.Presenter> implements ProjectDetailContract.View{
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.imName)
    ImageView imName;
    @BindView(R.id.imSchedule)
    ImageView imSchedule;
    @BindView(R.id.imProjectMode)
    ImageView imProjectMode;
    @BindView(R.id.tvSchedule)
    TextView tvSchedule;
    @BindView(R.id.tvCreatedAt)
    TextView tvCreatedAt;
    @BindView(R.id.tvUpdatedAt)
    TextView tvUpdatedAt;
    @BindView(R.id.tvTotalQuestion)
    TextView tvTotalQuestion;
    @BindView(R.id.tvProjectType)
    TextView tvProjectType;
    @BindView(R.id.imProjectType)
    ImageView imProjectType;

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
    @BindView(R.id.tvLabelDuration)
    TextView tvLabelDuration;

    @BindView(R.id.imDuration)
    ImageView imDuration;

    @BindView(R.id.tvEdit)
    TextView tvEdit;
    @BindView(R.id.imPlay)
    ImageView imPlay;

    @BindView(R.id.llQuestionPerSession)
    LinearLayout llQuestionPerSession;

    Project project;
    int totalQuestion = 0;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        project = getPresenter().getProject();
        Log.e("project", project.toString());
        totalQuestion = getPresenter().getNumberQuestion();
        init();
        if(!project.getIsRandom()){
            ViewGroup.LayoutParams layoutParams = llQuestionPerSession.getLayoutParams();
            layoutParams.height = 0;
            layoutParams.width = 0;
            llQuestionPerSession.setLayoutParams(layoutParams);
        }
        imName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectNameDialog dialog = new ProjectNameDialog(project);
                dialog.show(getParentFragmentManager(), ProjectNameDialog.class.getName());
            }
        });
        imProjectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), imProjectType);
                popupMenu.inflate(R.menu.menu_project_type);
                Map<String, String> keys = new HashMap<>();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int projectType = project.getType();
                        switch (menuItem.getItemId()){
                            case R.id.action_normal:
                                projectType = AppConstants.PROJECT_NORMAL_TYPE;
                                break;
                            case R.id.action_vocabulary:
                                projectType = AppConstants.PROJECT_VOCABULARY_TYPE;
                                break;
                        }
                        project.setType(projectType);
                        keys.put("type", projectType+"");
                        ProjectDB.getInstance(getContext()).update(keys, project.getId());
                        tvProjectType.setText(project.getNameType());
                        return true;
                    }
                });
                popupMenu.show();
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
                        Map<String, String> keys = new HashMap<>();
                        ViewGroup.LayoutParams layoutParams = llQuestionPerSession.getLayoutParams();

                        switch (menuItem.getItemId()){
                            case R.id.action_yes:
                                keys.put("is_random", 1+"");
                                ProjectDB.getInstance(getContext()).update(keys, project.getId());
                                Toast.makeText(getContext(),"Update Random Successful", Toast.LENGTH_SHORT).show();
                                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                llQuestionPerSession.setLayoutParams(layoutParams);
                                tvRandomMode.setText("true");
                                return true;
                            case R.id.action_no:
                                keys.put("is_random", 0+"");
                                ProjectDB.getInstance(getContext()).update(keys, project.getId());
                                Toast.makeText(getContext(),"Update Random Successful", Toast.LENGTH_SHORT).show();
                                layoutParams.height = 0;
                                layoutParams.width = 0;
                                llQuestionPerSession.setLayoutParams(layoutParams);
                                tvRandomMode.setText("false");
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


        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(R.id.projectQuestionEditFragment, BundleBuilder.bundleOf(
                        Pair.create(AppConstants.PROJECT_ID, project.getId())
                ), AnimationType.FROM_RIGHT_TO_LEFT);
            }
        });

        imPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuestion > 0){
                    navigateProjectPlay();
                }else{
                    Toast.makeText(getContext(), "Cannot play.Empty project!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imQuestionPerSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionPerSessionDialog dialog = new QuestionPerSessionDialog(tvQuestionPerSession.getText().toString(), new QuestionPerSessionDialog.IQuestionPerSession() {
                    @Override
                    public void onDataChange(String content) {
                        Map<String, String> keys = new HashMap<>();
                        keys.put("question_per_session", content);
                        ProjectDB.getInstance(getContext()).update(keys, project.getId());
                        tvQuestionPerSession.setText(content);
                        project.setQuestionPerSession(Integer.parseInt(content));
                        Toast.makeText(getContext(), "Update question per session successful!",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show(getParentFragmentManager(), QuestionPerSessionDialog.class.getName());
            }
        });

        imDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupTimeDurationDialog dialog = new SetupTimeDurationDialog(project.getDuration(),new SetupTimeDurationDialog.IListener() {
                    @Override
                    public void onSave(int time) {
                        Log.e("Save duration", "Ok");
                        Map<String, String> keys = new HashMap<>();
                        keys.put("duration",String.valueOf(time));
                        ProjectDB.getInstance(getContext()).update(keys, project.getId());
                        project.setDuration(time);
                        updateDuration();
                    }
                });
                dialog.show(getParentFragmentManager(),SetupTimeDurationDialog.class.getName());
            }
        });
        imSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleDialog scheduleDialog = new ScheduleDialog("", new ScheduleDialog.IListener() {
                    @Override
                    public void onSave(String date) {
                        Log.e("Schedule",date);
                        Map<String, String> keys = new HashMap<>();
                        keys.put("schedule", date);
                        ProjectDB.getInstance(getContext()).update(keys, project.getId());
                        tvSchedule.setText(Utils.displaySchedule(date));
                        project.setSchedule(date);
                        getViewModel().getMlScheduleNotificationProject().postValue(new MainActivityVM.Payload(AppConstants.SCHEDULE_NOTIFICATION, project));
                    }
                });
               scheduleDialog.show(getParentFragmentManager(), ScheduleDialog.class.getName());
            }
        });

        imProjectMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), imProjectMode);
                popupMenu.inflate(R.menu.menu_project_mode);
                Map<String, String> keys = new HashMap<>();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int projectMode = project.getMode();
                        switch (menuItem.getItemId()){
                            case R.id.project_exam_mode:
                                projectMode = AppConstants.PROJECT_EXAM_MODE;
                                break;
                            case R.id.project_challenge_mode:
                                projectMode = AppConstants.PROJECT_CHALLENGE_MODE;
                                break;
                        }

                        project.setMode(projectMode);
                        keys.put("mode", projectMode+"");
                        ProjectDB.getInstance(getContext()).update(keys, project.getId());
                        updateProjectPlay();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        observe();
    }

    public void init(){
        updateName();
        updateRandomMode();
        updateTotalQuestion();
        updateQuestionPerSession();
        updateProjectType();
        updateDuration();
        updateCreatedAt();
        updateUpdatedAt();
        updateProjectPlay();
        if(!project.getSchedule().isEmpty()){
            tvSchedule.setText(Utils.displaySchedule(project.getSchedule()));
        }
    }

    @Override
    public void navigateProjectPlay() {
        int page = R.id.projectPlayFragment;
        if(project.getMode() == AppConstants.PROJECT_CHALLENGE_MODE){
            page = R.id.projectChallengePlayFragment;
        }
        navigate(page, BundleBuilder.bundleOf(
                Pair.create(AppConstants.PROJECT_ID, project.getId()),
                Pair.create(AppConstants.VIEW_MODE, AppConstants.PROJECT_PLAY)
        ), AnimationType.FROM_RIGHT_TO_LEFT);
    }

    @Override
    public void observe() {
        getViewModel().getMldProjectStatus().observe(this, payload -> {
            if(payload.getAction()  == AppConstants.PROJECT_UPDATE){
                Project project =(Project) payload.getValue();
                this.project = project;
                init();
            }
        });
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
        tvRandomMode.setText(String.valueOf(project.getIsRandom()));
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
        int duration = project.getDuration();
        if(duration == -1){
            tvDuration.setText("Endless");
            tvLabelDuration.setText("Duration of the questionnaire");
        }else if(duration < -1){
            tvDuration.setText(String.valueOf(-duration));
            tvLabelDuration.setText("Time per question");
        }else {
            tvDuration.setText(String.valueOf(duration));
            tvLabelDuration.setText("Duration of the questionnaire");
        }
    }

    @Override
    public void updateProjectType() {
        tvProjectType.setText(project.getNameType());
    }

    @Override
    public void updateProjectPlay() {
        if(project.getMode() == AppConstants.PROJECT_CHALLENGE_MODE){
            imPlay.setImageResource(R.drawable.ic_challenge_play);
        }
        if(project.getMode() == AppConstants.PROJECT_EXAM_MODE){
            imPlay.setImageResource(R.drawable.ic_play);
        }
    }

}
