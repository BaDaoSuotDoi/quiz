package com.badao.quiz.utils;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BundleBuilder {
    public static Bundle bundleOf(Pair<String, Object>... data) {
        Bundle bundle = new Bundle();

        for (Pair<String, Object> s : data) {
            if (s.second instanceof Integer) {
                bundle.putInt(s.first, (Integer) s.second);
            } else if (s.second instanceof Float) {
                bundle.putFloat(s.first, (Float) s.second);
            } else if (s.second instanceof Double) {
                bundle.putDouble(s.first, (Double) s.second);
            } else if (s.second instanceof Long) {
                bundle.putLong(s.first, (Long) s.second);
            } else if (s.second instanceof Parcelable) {
                bundle.putParcelable(s.first, (Parcelable) s.second);
            } else if (s.second instanceof String) {
                bundle.putString(s.first, (String) s.second);
            } else if (s.second instanceof Boolean) {
                bundle.putBoolean(s.first, (Boolean) s.second);
            } else if (s.second instanceof Enum) {
                bundle.putSerializable(s.first, ((Enum<?>) s.second).name());
            } else if (s.second instanceof List) {
                if (((List<?>) s.second).isEmpty()) {
                    bundle.putParcelableArrayList(s.first, new ArrayList<>());
                } else {
                    if (((List<?>) s.second).get(0) instanceof Parcelable) {
                        bundle.putParcelableArrayList(s.first, (ArrayList<? extends Parcelable>) s.second);
                    }
                }
            } else if (s.second instanceof Serializable) {
                bundle.putSerializable(s.first, (Serializable) s.second);
            }
        }

        return bundle;
    }
}
