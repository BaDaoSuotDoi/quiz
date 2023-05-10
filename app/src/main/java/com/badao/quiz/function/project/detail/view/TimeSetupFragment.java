package com.badao.quiz.function.project.detail.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.badao.quiz.R;
import com.badao.quiz.component.SetupTimeCpn;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeSetupFragment extends Fragment {
    @BindView(R.id.tvMean)
    TextView tvMean;
    @BindView(R.id.tvNote)
    TextView tvNote;
    @BindView(R.id.stSetupTime)
    SetupTimeCpn stSetupTime;

    private String mean;
    private String note;
    private SetupTimeCpn.IListener iListener;
    private int time;
    public TimeSetupFragment(int time,String mean, String note,SetupTimeCpn.IListener iListener){
        this.time = time;
        this.mean = mean;
        this.note = note;
        this.iListener = iListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_setup, container, false);
        ButterKnife.bind(this, view);
        tvMean.setText(mean);
        tvNote.setText(note);
        stSetupTime.setListener(iListener);
        stSetupTime.setTime(this.time < -1 ? -this.time : this.time);
        return view;
    }
}
