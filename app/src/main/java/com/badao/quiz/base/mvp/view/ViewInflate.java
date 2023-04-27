package com.badao.quiz.base.mvp.view;

import com.badao.quiz.base.mvp.presenter.BasePresenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInflate {
    int LAYOUT_NOT_DEFINED = -1;

    Class<? extends BasePresenter> presenter();


    int layout() default LAYOUT_NOT_DEFINED;

    boolean hasToolbar() default true;

    boolean isBackHardwareDisable() default false;

    boolean isDisableBack() default false;
}