package com.badao.quiz.helper;

import android.content.Context;
import android.util.Log;

import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.base.mvp.view.ViewInflate;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class AnnotationHelper {

    public static BaseContract.Presenter createPresenter(Class<?> annotatedClass, Context context) {
        try {
            return Objects.requireNonNull(annotatedClass.getAnnotation(ViewInflate.class)).presenter().getDeclaredConstructor(Context.class).newInstance(context);

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            Log.e("Cannot create an instance of " , annotatedClass.toString());
            throw null;
        }
    }
}
