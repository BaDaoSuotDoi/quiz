package com.badao.quiz.function.signup.presenter;

import androidx.fragment.app.FragmentActivity;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.function.login.presenter.LoginContract;
import com.google.firebase.auth.FirebaseAuth;

public class SignupContract {

    public interface View extends BaseContract.View {
        void navigationLogin();
    }

    public interface Presenter extends BaseContract.Presenter<SignupContract.View> {
        void signup(FragmentActivity activity, String email, String password);
    }
}
