package com.badao.quiz.function.question.play;

import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.question.dialog.AlterLeavePlayDialog;
import com.badao.quiz.function.question.play.presenter.QuestionPlayContract;
import com.badao.quiz.model.Question;

public abstract class QuestionPlayBaseFragment extends BaseAnnotatedFragment<QuestionPlayBaseContract.View, QuestionPlayBaseContract.Presenter>
        implements QuestionPlayBaseContract.View {
    private Question question;
    private int position;
    private int viewMode;
    private AlterLeavePlayDialog alterLeavePlayDialog;

    @Override
    public boolean beforeBack() {
        if(getViewMode() == AppConstants.PROJECT_PLAY && alterLeavePlayDialog != null){
            alterLeavePlayDialog.show(getParentFragmentManager(), AlterLeavePlayDialog.class.getName());
            return false;
        }
        return  true;
    }
    public QuestionPlayBaseFragment(Question question, int position, int viewMode) {
        this.question = question;
        this.position = position;
        this.viewMode = viewMode;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getViewMode() {
        return viewMode;
    }


    public void setViewMode(int viewMode) {
        this.viewMode = viewMode;
        initMode();
    }

    public void initAlterLeavePlayDialog() {
        alterLeavePlayDialog = new AlterLeavePlayDialog(new AlterLeavePlayDialog.IListener() {
            @Override
            public void onAgree() {
                popBackStack();
            }
        });
    }

    public abstract void initMode();
    public class QuestionUserAnswer{
        private int questionPosition;
        private String answer;

        public QuestionUserAnswer(int questionPosition, String answer) {
            this.questionPosition = questionPosition;
            this.answer = answer;
        }

        public int getQuestionPosition() {
            return questionPosition;
        }

        public void setQuestionPosition(int questionPosition) {
            this.questionPosition = questionPosition;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }

}
