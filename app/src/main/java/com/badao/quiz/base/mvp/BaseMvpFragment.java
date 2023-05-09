package com.badao.quiz.base.mvp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.base.mvp.model.BasePresenterModel;
import com.badao.quiz.helper.NavHelper;

public abstract class BaseMvpFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends BaseFragment implements BaseContract.View, LifecycleOwner {
    protected P presenter;

    protected abstract P createPresenterInstance();

    public P getPresenter() {
        return presenter;
    }

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BasePresenterModel<V, P> viewModel = ViewModelProviders.of(this).get(BasePresenterModel.class);
        if (viewModel.getPresenter() == null) {
            viewModel.setPresenter(createPresenterInstance());
        }

        presenter = viewModel.getPresenter();
        presenter.attachView((V) this);
        presenter.attachLifecycle(getLifecycle());
        Bundle refreshData = getRefreshData();
        boolean isRefreshData = refreshData != null;

        if (isRefreshData) {
            presenter.setStateBundle(refreshData);
        } else if (getArguments() != null) {
            presenter.setStateBundle(getArguments());
        }

        // fixed if fragment don't have a presenter
        if (presenter != null) {
            presenter.onPresenterCreated(isRefreshData);
        } else {
            initViews(isRefreshData);
        }
    }

    private Bundle getRefreshData() {
        NavHelper navHelper = findMainNav();
        return navHelper != null ? navHelper.getRefreshDataBundle() : null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachLifecycle(getLifecycle());
        presenter.detachView();
    }


    @Override
    public void setRefreshBundle(Bundle bundle) {
        getPresenter().setStateBundle(bundle);
    }

    @Override
    public void onRefreshData(Bundle bundle) {
        initViews(true);
    }
}