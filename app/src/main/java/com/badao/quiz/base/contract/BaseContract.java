package com.badao.quiz.base.contract;

import android.os.Bundle;

import androidx.lifecycle.Lifecycle;

public class BaseContract {
    public interface View {
        void initViews(boolean isRefreshData);
    }

    public interface Presenter<V extends View> {
        void attachLifecycle(Lifecycle lifecycle);

        void detachLifecycle(Lifecycle lifecycle);

        void attachView(V view);

        void detachView();

        Bundle getStateBundle();

        void setStateBundle(Bundle bundle);

        V getView();

        boolean isViewAttached();

        void onPresenterCreated(boolean isRefreshData);

        void onPresenterDestroy();

        void clear();
    }
}