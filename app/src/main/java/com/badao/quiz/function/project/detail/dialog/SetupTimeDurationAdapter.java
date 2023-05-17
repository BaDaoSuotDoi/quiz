package com.badao.quiz.function.project.detail.dialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.badao.quiz.component.SetupTimeCpn;
import com.badao.quiz.function.project.detail.view.TimeSetupFragment;

public class SetupTimeDurationAdapter extends FragmentStateAdapter {
    public SetupTimeDurationAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    private int time;
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return new TimeSetupFragment(time,
                    "The duration you will configure here will be applied to all the test",
                    "",
                    new SetupTimeCpn.IListener() {
                        @Override
                        public void onChangeTime(int hour, int minute, int second) {
                            if(hour == -1){
                                time = -1;
                            }else{
                                time = hour * 3600 + minute * 60 + second;
                            }
                        }
                    }
            );
        }
        if(position == 1){
            return new TimeSetupFragment(time,
                    "The time you will configure here will be the time you want to allocate for each question",
                    "",
                    new SetupTimeCpn.IListener() {
                        @Override
                        public void onChangeTime(int hour, int minute, int second) {
                            if(hour == -1){
                                time = -1;
                            }else{
                                time = - (hour * 3600 + minute * 60 + second);
                            }
                        }
                    }
            );
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
