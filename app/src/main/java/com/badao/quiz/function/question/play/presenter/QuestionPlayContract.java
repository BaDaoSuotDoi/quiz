package com.badao.quiz.function.question.play.presenter;

import com.badao.quiz.base.contract.BaseContract;
public class QuestionPlayContract {
    public interface View extends BaseContract.View {
        void initMode();
        void updateContent();
        void updateComment();
        void updateSolution(boolean isShow);
        void initAlterLeavePlayDialog();
    }

    public interface Presenter extends BaseContract.Presenter<QuestionPlayContract.View> {

    }
}
