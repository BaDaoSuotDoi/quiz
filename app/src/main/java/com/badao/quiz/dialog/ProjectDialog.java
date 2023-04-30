package com.badao.quiz.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.badao.quiz.R;
import com.badao.quiz.base.dialog.BaseDialog;
import com.badao.quiz.base.dialog.BaseEditForm;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.main.view.MainActivity;
import com.badao.quiz.model.Project;

import butterknife.BindView;
import butterknife.OnClick;

public class ProjectDialog extends BaseEditForm {

    public ProjectDialog() {
        setTitle("New project");
        setContent("");
        setHint("Enter the project name...");
        setLabelApply("CREATE");
    }

    @Override
    public void onClickApply(String content) {
        Project project = new Project(content);
        Log.e("Project", project.toString());
        ProjectDB.getInstance(getContext()).create(project);
        getViewModel().setProjectStatus(new MainActivityVM.Payload(
                AppConstants.PROJECT_ADD,
                project
        ));
    }
}
