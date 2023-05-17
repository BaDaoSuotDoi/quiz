package com.badao.quiz.base.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.viewmode.BaseVMF;

import butterknife.ButterKnife;
import butterknife.Unbinder;
public abstract class BaseDialog extends DialogFragment {
    private static boolean isShowing = false;
    private boolean isDisableDismiss = false;
    protected abstract int getDialogLayout();
    private MainActivityVM mainActivityVM;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getDialogLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initViewModel();
        setupView();
        return view;
    }

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(isDisableDismiss){
            setCancelable(false);
        }
        return dialog;
    }

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        try {
            if (isShowing) return;

            super.show(manager, tag);
            isShowing = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showNow(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            if (isShowing) return;

            super.showNow(manager, tag);
            isShowing = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        isShowing = false;
        super.onDismiss(dialog);
    }

    private void initViewModel() {
        mainActivityVM = new ViewModelProvider(requireActivity(), BaseVMF.getInstance()).get(MainActivityVM.class);
    }

    protected MainActivityVM getViewModel() {
        return mainActivityVM;
    }

    public void setupView(){

    }

    public void setDisableDismiss(boolean disableDismiss) {
        isDisableDismiss = disableDismiss;
    }
}
