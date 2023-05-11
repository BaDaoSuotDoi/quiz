package com.badao.quiz.base.dialog;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
    @BindView(R.id.tvInstruction)
    TextView tvInstruction;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvApply)
    TextView tvApply;

    private boolean isHideEditText = false;
    private String title;
    private String content;
    private String hint;
    private String labelApply;
    private String instruction = "";
    private boolean isInputNumber = false;
    private boolean isShowApply = false;

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
                if(content!=null && !content.equals(charSequence.toString())){
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
        if(!instruction.isEmpty()){
            tvInstruction.setText(instruction);
            ViewGroup.LayoutParams layoutParams =  tvInstruction.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvInstruction.setLayoutParams(layoutParams);
        }
        if(isInputNumber){
            this.edContent.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if(isHideEditText){
            edContent.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams layoutParams =  edContent.getLayoutParams();
            layoutParams.width = 0;
            layoutParams.height = 0;
            edContent.setLayoutParams(layoutParams);
        }
        if(isShowApply){
            ViewGroup.LayoutParams layoutParams =  tvApply.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvApply.setLayoutParams(layoutParams);
        }
    }

    public abstract void onClickApply(String content);

    public void setTitle(String title){
        this.title = title;
    }

    public void setHint(String hint){
        this.hint = hint;
    }
    public void setInstruction(String instruction){
        this.instruction = instruction;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setInputNumber(){
        isInputNumber = true;
    }

    public void setHideEditText(boolean hideEditText) {
        isHideEditText = hideEditText;
    }

    public void setLabelApply(String label){
        this.labelApply = label;
    }

    public void setShowApply(boolean showApply) {
        isShowApply = showApply;
    }

    @Override
    protected int getDialogLayout() {
        return R.layout.dialog_edit_form;
    }


}
