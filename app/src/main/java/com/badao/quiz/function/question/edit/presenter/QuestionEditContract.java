package com.badao.quiz.function.question.edit.presenter;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.function.project.question_edit.presenter.ProjectQuestionEditContract;
import com.badao.quiz.model.Project;

public class QuestionEditContract {
    public interface View extends BaseContract.View {
        void updateListAnswer();
        void checkValidInput();
    }

    public interface Presenter extends BaseContract.Presenter<QuestionEditContract.View> {
    }
}
