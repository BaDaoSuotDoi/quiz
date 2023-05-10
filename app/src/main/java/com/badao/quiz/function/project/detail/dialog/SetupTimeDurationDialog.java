package com.badao.quiz.function.project.detail.dialog;

import android.view.View;
import android.widget.TextView;

import androidx.viewpager2.widget.ViewPager2;

import com.badao.quiz.R;
import com.badao.quiz.base.dialog.BaseDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;

public class SetupTimeDurationDialog extends BaseDialog {
    @BindView(R.id.tlSetup)
    TabLayout tlSetup;
    @BindView(R.id.vpSetup)
    ViewPager2 vpSetup;
    @BindView(R.id.tvUndo)
    TextView tvUndo;
    @BindView(R.id.tvSave)
    TextView tvSave;

    private IListener iListener;
    private SetupTimeDurationAdapter adapter;
    private int time;
    public SetupTimeDurationDialog(int time, IListener iListener){
        this.time = time;
        this.iListener = iListener;
    }
    @Override
    protected int getDialogLayout() {
        return R.layout.dialog_setup_time_duration;
    }

    @Override
    public void setupView() {
        super.setupView();
        adapter = new SetupTimeDurationAdapter(getActivity());
        adapter.setTime(time);
        vpSetup.setAdapter(adapter);
        if(this.time < -1){
            vpSetup.setCurrentItem(1);
        }
        new TabLayoutMediator(tlSetup, vpSetup,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Test duration");
                            break;
                        case 1:
                            tab.setText("Time per question");
                            break;
                    }
                }).attach();
        tvUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListener.onSave(adapter.getTime());
                dismiss();
            }
        });
    }

    public interface IListener{
        void onSave(int time);
    }
}
