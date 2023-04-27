package com.badao.quiz.viewmode;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class BaseVMF implements ViewModelProvider.Factory {
    private static BaseVMF instance;

    public static BaseVMF getInstance() {
        if (instance == null) {
            instance = new BaseVMF();
        }
        return instance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor().newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        throw new NullPointerException("Null viewModel");
    }
}