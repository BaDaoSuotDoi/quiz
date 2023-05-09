package com.badao.quiz.function.history.presenter;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.model.HistorySubmit;

import java.util.List;

public class HistoryContract {
    public interface View extends BaseContract.View {
        void navigateProjectResult(HistorySubmit historySubmit);
    }

    public interface Presenter extends BaseContract.Presenter<HistoryContract.View> {
        List<HistorySubmit> getListHistorySubmit();
    }
}
