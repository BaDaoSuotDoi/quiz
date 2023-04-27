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
import com.badao.quiz.db.ProjectDB;
import com.badao.quiz.function.main.view.MainActivity;
import com.badao.quiz.model.Project;

import butterknife.BindView;
import butterknife.OnClick;

public class ProjectDialog extends BaseDialog {
    @BindView(R.id.edName)
    EditText edName;

    @BindView(R.id.tvCreate)
    TextView tvCreate;

    @OnClick({R.id.tvCancel, R.id.tvCreate})
    protected void OnClick(View view) {
        switch (view.getId()){
            case  R.id.tvCancel:
                dismiss();
                getViewModel().setStatusShowKey(false);
                break;
            case R.id.tvCreate:
                Project project = new Project(edName.getText().toString());
                Log.e("Project", project.toString());
                ProjectDB.getInstance(getContext()).create(project);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        edName.requestFocus();
        validCreate(edName.getText().toString());
        getViewModel().setStatusShowKey(true);
        edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validCreate(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        getViewModel().setStatusShowKey(false);
    }

    public void validCreate(String name){
        ViewGroup.LayoutParams layoutParams =  tvCreate.getLayoutParams();
        if(name.length() > 0){
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvCreate.setLayoutParams(layoutParams);
        }else{
            layoutParams.width = 0;
            layoutParams.height = 0;
            tvCreate.setLayoutParams(layoutParams);
        }
    }
    @Override
    protected int getDialogLayout() {
        return R.layout.dialog_project;
    }

}
