package com.badao.quiz.function.login.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.CountDownLatch;


public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{

    public LoginPresenter(Context context) {
        super(context);
    }

    @Override
    public void login(FragmentActivity activity, FirebaseAuth auth, String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success");
                        FirebaseUser user = auth.getCurrentUser();
                        Log.e("user", user.toString());
                        getView().triggerSyncData();
                        getView().navigationHome();
                    }   else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.getException());
                        Toast.makeText(getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                    getView().setStatusButtonLogin(true);
                });
    }
}