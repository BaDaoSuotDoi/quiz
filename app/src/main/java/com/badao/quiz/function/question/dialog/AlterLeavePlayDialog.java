package com.badao.quiz.function.question.dialog;

import android.view.View;
import android.widget.TextView;

import com.badao.quiz.R;
import com.badao.quiz.base.dialog.BaseDialog;

import butterknife.BindView;

public class AlterLeavePlayDialog extends BaseDialog {
    @BindView(R.id.tvCancel)
    TextView tvCancel;

    @BindView(R.id.tvAgree)
    TextView tvAgree;

    private IListener iListener;
    public AlterLeavePlayDialog(IListener iListener){
        setDisableDismiss(true);
        this.iListener = iListener;
    }
    @Override
    protected int getDialogLayout() {
        return R.layout.dialog_leave_project_play;
    }

    @Override
    public void setupView() {
        super.setupView();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListener.onAgree();
                dismiss();
            }
        });
    }

    public interface  IListener{
        void onAgree();
    }
}
