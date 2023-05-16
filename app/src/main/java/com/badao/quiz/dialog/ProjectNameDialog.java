package com.badao.quiz.dialog;

import com.badao.quiz.base.dialog.BaseDialog;
import com.badao.quiz.base.dialog.BaseEditForm;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.model.Project;

import java.util.HashMap;
import java.util.Map;

public class ProjectNameDialog extends BaseEditForm {
    private Project project;

    public ProjectNameDialog(Project project) {
        this.project = project;
        setTitle("Rename project");
        setContent(this.project.getName());
        setHint("Enter the project name...");
        setLabelApply("RENAME");
    }

    @Override
    public void onClickApply(String content) {
        Map<String, String >keys = new HashMap<>();
        keys.put("name", content);
        project.setName(content);
        ProjectDB.getInstance(getContext()).update(keys,project.getId());
        getViewModel().setProjectStatus(new MainActivityVM.Payload(
                AppConstants.PROJECT_UPDATE,
                project
        ));
    }
}
