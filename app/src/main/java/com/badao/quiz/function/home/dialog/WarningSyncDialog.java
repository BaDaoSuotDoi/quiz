package com.badao.quiz.function.home.dialog;

import com.badao.quiz.base.dialog.BaseEditForm;

public class WarningSyncDialog extends BaseEditForm {
    private IListener iListener;
    public WarningSyncDialog(IListener iListener){
        setTitle("Waring Logout");
        setInstruction("Do you want continue logout. Unsynchronized data will be lost!" );
        setLabelApply("I AGREE");
        setHideEditText(true);
        setShowApply(true);
        setDisableKeyBoard(true);
        setDisableDismiss(true);
        this.iListener = iListener;
    }
    @Override
    public void onClickApply(String content) {
       iListener.onForceLogout();
       dismiss();
    }

    public interface IListener{
        void onForceLogout();
    }
}
