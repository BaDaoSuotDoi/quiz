package com.badao.quiz.function.project.detail.dialog;

import com.badao.quiz.base.dialog.BaseEditForm;

public class QuestionPerSessionDialog extends BaseEditForm {
    private IQuestionPerSession iQuestionPerSession;
    public QuestionPerSessionDialog(String content, IQuestionPerSession iQuestionPerSession) {
        setTitle("Maximum per session");
        setContent(content);
        setHint("Max questions number per session.");
        setLabelApply("OK");
        setInputNumber();
        this.iQuestionPerSession = iQuestionPerSession;
    }
    @Override
    public void onClickApply(String content) {
        this.iQuestionPerSession.onDataChange(content);
    }

    public interface IQuestionPerSession{
        void onDataChange(String content);
    }
}
