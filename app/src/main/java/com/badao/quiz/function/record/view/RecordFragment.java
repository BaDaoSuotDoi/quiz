package com.badao.quiz.function.record.view;

import android.annotation.SuppressLint;

import androidx.viewpager2.widget.ViewPager2;

import com.badao.quiz.R;
import com.badao.quiz.base.mvp.BaseAnnotatedFragment;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.record.adapter.RecordAdapter;
import com.badao.quiz.function.record.presenter.RecordContract;
import com.badao.quiz.function.record.presenter.RecordPresenter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
@ViewInflate(presenter = RecordPresenter.class, layout = R.layout.fragment_record_project)
public class RecordFragment extends BaseAnnotatedFragment<RecordContract.View, RecordContract.Presenter> implements RecordContract.View{

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager2 viewPager;

    @Override
    public void initViews(boolean isRefreshData) {
        super.initViews(isRefreshData);
        RecordAdapter recordAdapter = new RecordAdapter(getActivity());
        viewPager.setAdapter(recordAdapter);
        viewPager.setOffscreenPageLimit(1);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Tab " + (position + 1))
        );

        tabLayoutMediator.attach();
    }
}
