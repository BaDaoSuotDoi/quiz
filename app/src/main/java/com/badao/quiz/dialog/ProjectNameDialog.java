package com.badao.quiz.dialog;

import com.badao.quiz.base.dialog.BaseDialog;
import com.badao.quiz.base.dialog.BaseEditForm;

public class ProjectNameDialog extends BaseEditForm {
    public ProjectNameDialog(String name) {
        setTitle("Rename project");
        setContent(name);
        setHint("Enter the project name...");
        setLabelApply("RENAME");
    }

    @Override
    public void onClickCancel() {
        dismiss();
    }

    @Override
    public void onClickApply(String content) {

    }
}
