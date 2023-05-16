package com.badao.quiz.base.mvp;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import com.badao.quiz.base.contract.BaseContract;

public abstract class BaseMvpActivity<P extends BaseContract.Presenter> extends BaseActivity implements BaseContract.View {
    private P presenter;

    protected abstract P createPresenterInstance();

    public P getPresenter() {
        return presenter;
    }


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = createPresenterInstance();

        if (presenter != null) {
            presenter.attachView(this);
        }
        initViews(false);
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
            presenter.clear();
        }
    }

}