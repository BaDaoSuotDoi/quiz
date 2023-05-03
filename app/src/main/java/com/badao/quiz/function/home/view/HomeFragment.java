package com.badao.quiz.function.home.view;

import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.core.view.WindowInsetsCompat;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.component.ProjectItemCpn;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.dialog.ProjectDialog;
import com.badao.quiz.function.home.presenter.HomeContract;
import com.badao.quiz.function.home.presenter.HomePresenter;
import com.badao.quiz.function.login.presenter.LoginContract;
import com.badao.quiz.model.Project;
import com.badao.quiz.utils.BundleBuilder;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.OnClick;

@ViewInflate(presenter = HomePresenter.class, layout = R.layout.fragment_home, hasToolbar = false)
public class HomeFragment extends BaseAnnotatedFragment<HomeContract.View, HomeContract.Presenter> implements HomeContract.View{

    @BindView(R.id.flProject)
    FlexboxLayout flProject;
    ProjectDialog projectDialog;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        getPresenter().initProjects();
        observe();

    }

    @OnClick({R.id.btLogout, R.id.btCreateProject})
    public  void onClick(View view){
        switch (view.getId()){
            case R.id.btLogout:
                FirebaseAuth.getInstance().signOut();
                navigate(R.id.loginFragment, AnimationType.FROM_BOTTOM_LEFT_CORNER_TO_WHOLE_SCREEN);
                break;
            case  R.id.btCreateProject:
                if(projectDialog == null){
                    projectDialog = new ProjectDialog();
                }
                projectDialog.show(getParentFragmentManager(), ProjectDialog.class.getName());
        }
    }

    @Override
    public void observe() {
        getViewModel().getMldProjectStatus().observe(this, payload -> {
            if(payload.getAction()  == AppConstants.PROJECT_ADD){
                Project project =(Project) payload.getValue();
                addProject(project);
            }
        });
    }

    @Override
    public void addProject(Project project) {
        flProject.addView(new ProjectItemCpn(getContext(), project, new ProjectItemCpn.IAction() {
            @Override
            public void navigateEdit(Project project) {
                navigate(R.id.projectDetailFragment, BundleBuilder.bundleOf(
                        Pair.create(AppConstants.PROJECT_ID, project.getID())
                ), AnimationType.FROM_RIGHT_TO_LEFT);
            }

            @Override
            public void navigateQuestionEdit(Project project) {
                navigate(R.id.projectQuestionEditFragment, BundleBuilder.bundleOf(
                        Pair.create(AppConstants.PROJECT_ID, project.getID())
                ), AnimationType.FROM_RIGHT_TO_LEFT);
            }

            @Override
            public void onDelete(Project project) {
                getPresenter().initProjects();
            }
        }));
    }

    @Override
    public void refreshView() {
        flProject.removeAllViews();
    }

}
