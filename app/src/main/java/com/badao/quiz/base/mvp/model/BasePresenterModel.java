package com.badao.quiz.base.mvp.model;

import androidx.lifecycle.ViewModel;

import com.badao.quiz.base.contract.BaseContract;

public class BasePresenterModel<V extends BaseContract.View, P extends BaseContract.Presenter<V>> extends ViewModel {
    private P presenter;

    public void setPresenter(P presenter) {
        if (this.presenter == null) {
            this.presenter = presenter;
        }
    }

    public P getPresenter() {
        return this.presenter;
    }


    @Override
    protected void onCleared() {
        presenter.onPresenterDestroy();
        presenter = null;
        super.onCleared();
    }
}