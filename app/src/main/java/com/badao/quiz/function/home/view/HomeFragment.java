package com.badao.quiz.function.home.view;

import android.view.View;

import androidx.core.view.WindowInsetsCompat;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.dialog.ProjectDialog;
import com.badao.quiz.function.home.presenter.HomeContract;
import com.badao.quiz.function.home.presenter.HomePresenter;
import com.badao.quiz.function.login.presenter.LoginContract;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.OnClick;

@ViewInflate(presenter = HomePresenter.class, layout = R.layout.fragment_home, hasToolbar = false)
public class HomeFragment extends BaseAnnotatedFragment<HomeContract.View, HomeContract.Presenter> implements HomeContract.View{

    private ProjectDialog projectDialog;

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

}
