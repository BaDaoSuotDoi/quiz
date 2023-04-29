package com.badao.quiz.component;

import android.content.Context;
import android.util.AttributeSet;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.badao.quiz.utils.Utils;

public class MainLayoutCpn extends CoordinatorLayout { ;
    int statusbarHeight;

    public MainLayoutCpn(Context context) {
        super(context);
        init();
    }

    public MainLayoutCpn(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainLayoutCpn(Context context, AttributeSet attrs, int defStyleAttr) {
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


}