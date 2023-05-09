package com.badao.quiz.function.main.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseMvpActivity;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.main.presenter.MainActivityContract;
import com.badao.quiz.function.main.presenter.MainActivityPresenter;
import com.badao.quiz.helper.NavHelper;
import com.badao.quiz.viewmode.BaseVMF;

public class MainActivity  extends BaseMvpActivity<MainActivityContract.Presenter>
        implements MainActivityContract.View{
    public NavHelper navHelper;
    private MainActivityVM mMainActivityModel;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initNavHelper() {
        navHelper = new NavHelper(this, R.id.navHost);
        Log.e("Init navHelper", "navHelper");
    }


    private void initViewModel() {
        mMainActivityModel = new ViewModelProvider(this, BaseVMF.getInstance()).get(MainActivityVM.class);
    }

    private void doObserve() {

        mMainActivityModel.getEventShowKeyBoard().observe(this, isShow -> {
           toggleKeyboard(isShow);
        });
    }

    @Override
    public void initViews(boolean isRefreshData) {
        initViewModel();
        initNavHelper();
        doObserve();
    }

    @Override
    protected MainActivityContract.Presenter createPresenterInstance() {
        return new MainActivityPresenter(this);
    }



}