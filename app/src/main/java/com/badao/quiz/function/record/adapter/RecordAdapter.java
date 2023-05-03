package com.badao.quiz.function.record.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.badao.quiz.function.history.view.HistoryFragment;
import com.badao.quiz.function.statistics.view.StatisticsFragment;

public class RecordAdapter extends FragmentStateAdapter {
    public RecordAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e("Fragment here", position+"");
        switch (position){
            case 0:
                return new HistoryFragment();
            case 1:
                return new StatisticsFragment();
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
