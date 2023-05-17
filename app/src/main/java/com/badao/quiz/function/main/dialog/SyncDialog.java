package com.badao.quiz.function.main.dialog;

import com.badao.quiz.R;
import com.badao.quiz.base.dialog.BaseDialog;

public class SyncDialog extends BaseDialog {
    public SyncDialog(){
        setDisableDismiss(true);
    }
    @Override
    protected int getDialogLayout() {
        return R.layout.alter_sync;
    }
}
