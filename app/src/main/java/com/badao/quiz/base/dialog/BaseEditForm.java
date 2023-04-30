package com.badao.quiz.base.dialog;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.badao.quiz.R;

import butterknife.BindView;

public abstract class BaseEditForm  extends BaseDialog{
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.edContent)
    EditText edContent;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvApply)
    TextView tvApply;

    private String title;
    private String content;
    private String hint;
    private String labelApply;

    @Override
    public void onStart() {
        super.onStart();
        initView();
        edContent.requestFocus();
        getViewModel().setStatusShowKey(true);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                getViewModel().setStatusShowKey(false);
            }
        });

        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickApply(edContent.getText().toString());
                edContent.setText("");
                dismiss();
            }
        });

        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ViewGroup.LayoutParams layoutParams =  tvApply.getLayoutParams();
                if(!content.equals(charSequence.toString())){
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    tvApply.setLayoutParams(layoutParams);
                }else{
                    layoutParams.width = 0;
                    layoutParams.height = 0;
                    tvApply.setLayoutParams(layoutParams);
                }
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

    public void initView(){
        tvTitle.setText(title == null ? "Title": title);
        edContent.setText(content == null ? "": content);
        edContent.setHint(hint == null ? "Enter here..": hint);
        tvApply.setText(labelApply == null ? "APPLY": labelApply);
    }

    public abstract void onClickApply(String content);

    public void setTitle(String title){
        this.title =title;
    }

    public void setHint(String hint){
        this.hint = hint;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setLabelApply(String label){
        this.labelApply = label;
    }
    @Override
    protected int getDialogLayout() {
        return R.layout.item_edit_form;
    }


}
