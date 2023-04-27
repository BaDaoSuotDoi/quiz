package com.badao.quiz.function.login.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowInsetsCompat;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.login.presenter.LoginContract;
import com.badao.quiz.function.login.presenter.LoginPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = LoginPresenter.class, layout = R.layout.fragment_login, hasToolbar = false, isBackHardwareDisable = true)
public class LoginFragment extends BaseAnnotatedFragment<LoginContract.View, LoginContract.Presenter> implements LoginContract.View{
    @BindView(R.id.edEmail)
    EditText edEmail;

    @BindView(R.id.edPassword)
    EditText edPassword;

    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.tvSignup)
    TextView tvSignup;
    FirebaseAuth mAuth;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        if(edEmail == null){
            Log.e("Null object", "edEmail");
            return;
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btLogin)
    public void login(){
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        if(email.equals("")){
            Toast.makeText(getContext(), "Email empty!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.equals("")){
            Toast.makeText(getContext(), "Password empty!",Toast.LENGTH_SHORT).show();
            return;
        }

        getPresenter().login(getActivity(),mAuth, email, password);
    }

    @Override
    public void navigationHome() {
        navigate(R.id.homeFragment, AnimationType.FROM_BOTTOM_LEFT_CORNER_TO_WHOLE_SCREEN);
    }

    @Override
    @OnClick(R.id.tvSignup)
    public void navigationSignup() {
        navigate(R.id.signupFragment, AnimationType.FROM_BOTTOM_LEFT_CORNER_TO_WHOLE_SCREEN);
    }

    @Override
    protected void onBackHardwareClicked() {
        super.onBackHardwareClicked();
    }
}
