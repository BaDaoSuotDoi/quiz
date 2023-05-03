package com.badao.quiz.function.history.presenter;


import android.content.Context;

import com.badao.quiz.base.mvp.presenter.BasePresenter;
import com.badao.quiz.db.HistorySubmitDB;
import com.badao.quiz.model.HistorySubmit;

import java.util.List;

public class HistoryPresenter extends BasePresenter<HistoryContract.View> implements HistoryContract.Presenter {
    public HistoryPresenter(Context context) {
        super(context);
    }

    @Override
    public List<HistorySubmit> getListHistorySubmit() {
        return HistorySubmitDB.getInstance(getContext()).findBy();
    }
}
