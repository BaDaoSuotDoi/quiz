package com.badao.quiz.function.project_detail.presenter;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.model.Project;

public class ProjectDetailContract {
    public interface View extends BaseContract.View {
          void updateName();
          void updateRandomMode();
          void updateTotalQuestion();
          void updateQuestionPerSession();
          void updateDuration();
    }

    public interface Presenter extends BaseContract.Presenter<ProjectDetailContract.View> {
        Project getProject();
    }
}
