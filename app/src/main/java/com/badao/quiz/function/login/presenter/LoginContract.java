package com.badao.quiz.function.login.presenter;

import androidx.fragment.app.FragmentActivity;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.function.login.object.LoginRequest;
import com.google.firebase.auth.FirebaseAuth;

public class LoginContract {
    public interface View extends BaseContract.View {
        void navigationHome();
        void navigationSignup();
    }

    public interface Presenter extends BaseContract.Presenter<View> {
        void login(FragmentActivity activity, FirebaseAuth auth, String email, String password);
    }
}