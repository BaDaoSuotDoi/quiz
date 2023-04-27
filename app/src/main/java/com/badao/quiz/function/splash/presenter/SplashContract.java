package com.badao.quiz.function.splash.presenter;

import com.badao.quiz.base.contract.BaseContract;

public class SplashContract {
    public interface View extends BaseContract.View {
        void configMainView();

    }

    public interface Presenter extends BaseContract.Presenter<View> {


        void isLogin();
    }
}