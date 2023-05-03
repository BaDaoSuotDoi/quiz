package com.badao.quiz.function.question.play.presenter;

import com.badao.quiz.base.contract.BaseContract;
public class QuestionPlayContract {
    public interface View extends BaseContract.View {
        void updateListAnswer();
        void updateContent();
        void updateComment();
    }

    public interface Presenter extends BaseContract.Presenter<QuestionPlayContract.View> {

    }
}
