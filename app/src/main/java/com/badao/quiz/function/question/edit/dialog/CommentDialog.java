package com.badao.quiz.function.question.edit.dialog;

import com.badao.quiz.base.dialog.BaseEditForm;

public class CommentDialog extends BaseEditForm {
    private ICommentDialog iCommentDialog;
    public CommentDialog(String content, ICommentDialog iCommentDialog) {
        setTitle("Add a comment");
        setContent(content);
        setHint("Add a comment...");
        setLabelApply("OK");
        setInstruction("Add a comment that provides additional information about the answer(s)");
        this.iCommentDialog = iCommentDialog;
    }

    @Override
    public void onClickApply(String content) {
        iCommentDialog.onCommentChange(content);
    }

    public interface ICommentDialog{
        void onCommentChange(String content);
    }

}
