package com.badao.quiz.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.badao.quiz.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CpnMainLayout extends CoordinatorLayout { ;
    int statusbarHeight;

    public CpnMainLayout(Context context) {
        super(context);
        init();
    }

    public CpnMainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CpnMainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        statusbarHeight = Utils.getStatusbarHeight(getContext());
//        ViewCompat.setOnApplyWindowInsetsListener(this, (v, windowInsetsCompat) -> {
//            WindowInsetsCompat resultCompat = null;
//            for (int i = callBacks.size() - 1; i >= 0; i--) {
//                WindowInsetsCompat compat = callBacks.get(i).onApplyWindowInsets(v, windowInsetsCompat);
//                if (resultCompat == null) resultCompat = compat;
//            }
//
//            if (resultCompat == null) {
//                Insets imeInsets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.ime());
//
//
//                resultCompat = new WindowInsetsCompat.Builder()
//                        .setInsets(WindowInsetsCompat.Type.statusBars(), Insets.of(0, statusbarHeight, 0, imeInsets.bottom))
//                        .build();
//            }
//
//            return ViewCompat.onApplyWindowInsets(CpnMainLayout.this, resultCompat);
//        });
    }


//    public interface OnWindownCallBack {
//        WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat windowInsetsCompat);
//    }
}