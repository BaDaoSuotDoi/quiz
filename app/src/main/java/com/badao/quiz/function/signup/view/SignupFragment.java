package com.badao.quiz.function.signup.view;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.signup.presenter.SignupContract;
import com.badao.quiz.function.signup.presenter.SignupPresenter;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = SignupPresenter.class, layout = R.layout.fragment_signup, hasToolbar = false)
public class SignupFragment extends BaseAnnotatedFragment<SignupContract.View, SignupContract.Presenter> implements SignupContract.View{
    @BindView(R.id.edEmail)
    EditText edEmail;
    @BindView(R.id.edPassword)
    EditText edPassword;
    @BindView(R.id.edRePassword)
    EditText edRePassword;
    @Override
    @OnClick(R.id.tvLogin)
    public void navigationLogin() {
        navigate(R.id.loginFragment,  AnimationType.FROM_BOTTOM_LEFT_CORNER_TO_WHOLE_SCREEN);
    }

    @Override
    protected void onBackHardwareClicked() {
        Log.e("Run here", "back");
        popBackStack();
    }

    @OnClick(R.id.btSignup)
    public  void signup(){
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();
        String rePassword = edRePassword.getText().toString();
        if(email.isEmpty()){
            Toast.makeText(getContext(), "Email empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(getContext(), "Password empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(rePassword.isEmpty()){
            Toast.makeText(getContext(), "RePassword empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(rePassword)){
            Toast.makeText(getContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
            return;
        }
        getPresenter().signup(getActivity() ,email, password);
    }
}
