package com.badao.quiz.function.splash.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowInsetsCompat;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.main.view.MainActivity;
import com.badao.quiz.function.splash.presenter.SplashContract;
import com.badao.quiz.function.splash.presenter.SplashPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = SplashPresenter.class, layout = R.layout.fragment_splash, isBackHardwareDisable = true)
public class SplashFragment
        extends BaseAnnotatedFragment<SplashContract.View, SplashContract.Presenter>
        implements SplashContract.View {

    @Override
    public void initViews(boolean isRefreshData) {
        Log.e("Run here", "init view splash");
        if (getViewModel().isPendingCheckActivity()) return;
//        FirebaseApp.initializeApp(requireContext());
        doObserve();
    }

    private void doObserve() {
        Log.e("Run here", "doObserve");
        getPresenter().isLogin();

    }


    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Disable back press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //DO NOTHING
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }


    @Override
    public void configMainView() {
        Log.e("Run here: ", "Ok");
        if (getActivity() == null || !(getActivity() instanceof MainActivity)) {
            Log.e("Return: ", "Ok");
            return;
        }

        boolean isLogin = false;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            isLogin = true;
        }

        findMainNav().navigateSplash(isLogin);
    }

}