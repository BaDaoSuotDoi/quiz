package com.badao.quiz.base.mvp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.badao.quiz.R;
import com.badao.quiz.base.contract.BaseContract;
import com.badao.quiz.base.mvp.view.ViewInflate;
import com.badao.quiz.function.main.model.MainActivityVM;
import com.badao.quiz.function.main.view.MainActivity;
import com.badao.quiz.helper.AnnotationHelper;
import com.badao.quiz.viewmode.BaseVMF;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseAnnotatedFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends BaseMvpFragment<V, P>  {

    @BindView(R.id.content_layout)
    protected View llBaseRootView;

    private MainActivityVM mainActivityVM;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null ) {
            mView = inflater.inflate(R.layout.fragment_base_toolbar_back, container, false);
            View viewStub = inflater.inflate(getLayoutId(), container, false);
            FrameLayout mainContent = mView.findViewById(R.id.content_layout);
            mainContent.addView(viewStub);

            viewStub.postDelayed(() -> {
                mView.findViewById(R.id.content_layout).setVisibility(View.VISIBLE);
            }, 50);
        }
        unbinder = ButterKnife.bind(this, mView);
        prepareInitView();
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected P createPresenterInstance() {
        return (P) AnnotationHelper.createPresenter(getClass(), getContext());
    }


    @Override
    protected int getLayoutId() {
        return getClass().getAnnotation(ViewInflate.class).layout();
    }

    @Override
    public void initViews(boolean isRefreshData) {

    }

    private void prepareInitView() {
        initViewModel();
        backHandle();
    }


    private void initViewModel() {
        mainActivityVM = new ViewModelProvider(requireActivity(), BaseVMF.getInstance()).get(MainActivityVM.class);
    }

    protected MainActivityVM getViewModel() {
        return mainActivityVM;
    }

    protected void backHandle() {
        if (getClass().getAnnotation(ViewInflate.class).isDisableBack()) return;

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(beforeBack()){
                    popBackStack();

                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    public boolean beforeBack(){
        return  true;
    }
}
