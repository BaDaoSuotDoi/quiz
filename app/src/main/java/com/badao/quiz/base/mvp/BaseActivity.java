package com.badao.quiz.base.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    public void toggleKeyboard(Boolean isShow) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        Boolean inputStatus = inputMethodManager.isActive();
        Log.e("Status",(isShow?"Show true":"Show false") + (inputStatus?"inputStatus true":"inputStatus false"));
        if(!isShow || inputStatus){
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public void hiddenKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}