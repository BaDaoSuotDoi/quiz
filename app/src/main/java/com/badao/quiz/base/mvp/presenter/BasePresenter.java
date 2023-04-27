package com.badao.quiz.base.mvp.presenter;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.CallSuper;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import com.badao.quiz.base.contract.BaseContract;

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V>, LifecycleObserver {
    private Context context;
    private V view;
    private Bundle stateBundle;

    private Handler handler = new Handler();
    private boolean isFirstCreateFragment = true;

    public BasePresenter(Context context) {
        this.context = context;
    }


    @Override
    final public void attachLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    final public void detachLifecycle(Lifecycle lifecycle) {
        lifecycle.removeObserver(this);
    }

    @Override
    final public void attachView(V view) {
        this.view = view;
    }

    @Override
    final public void detachView() {
        if (view != null) {
            view = null;
            handler.removeCallbacksAndMessages(null);
            clear();
        }
    }

    @Override
    public void clear() {

    }


    @Override
    final public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void onPresenterCreated(boolean isRefreshData) {
        if (view != null) {
            // delay animation
            if (isFirstCreateFragment) {
                isFirstCreateFragment = false;
                handler.postDelayed(() -> {
                    view.initViews(isRefreshData);
                }, 900);
            } else {
                view.initViews(isRefreshData);
            }
        }
    }

    @Override
    final public Bundle getStateBundle() {
        return stateBundle == null ?
                stateBundle = new Bundle() : stateBundle;
    }

    @Override
    public void setStateBundle(Bundle bundle) {
        this.stateBundle = bundle;
    }


    @CallSuper
    @Override
    public void onPresenterDestroy() {
        if (stateBundle != null && !stateBundle.isEmpty()) {
            stateBundle.clear();
        }
    }

    final public V getView() {
        return view;
    }

    protected Context getContext() {
        return this.context;
    }

}
