package com.badao.quiz.base.mvp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.function.main.view.MainActivity;
import com.badao.quiz.helper.AnimationHelper;
import com.badao.quiz.helper.NavHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected Unbinder unbinder;

    protected abstract int getLayoutId();
    private View view;
    private String tagCustom = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
//        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbinder.unbind();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public String getTagCustom() {
        return tagCustom;
    }

    public void setTagCustom(String tagCustom) {
        this.tagCustom = tagCustom;
    }

    public void navigate(int layoutId, AnimationType type) {
        NavHelper navHelper = findMainNav();
        if (layoutId == R.id.loginFragment) {
            navHelper.forceNavigate(layoutId, type, null);
            return;
        }
        if (navHelper != null) {
            Log.e("navHelper", "Run here");
            navHelper.navigate(layoutId, type);
        } else if (getView() != null) {
            Navigation.findNavController(getView()).navigate(layoutId, null, AnimationHelper.createNavAnimation(type));
        }
    }

    public void navigate(int layoutId, Bundle bundle, AnimationType type) {
        NavHelper navHelper = findMainNav();
        if (layoutId == R.id.loginFragment) {
            navHelper.forceNavigate(layoutId, type, null);
            return;
        }

        if (navHelper != null) {
            navHelper.navigate(layoutId, bundle, type);
        } else if (getView() != null) {
            Navigation.findNavController(getView()).navigate(layoutId, bundle, AnimationHelper.createNavAnimation(type));
        }
    }

    public boolean popBackStack() {
        NavHelper navHelper = findMainNav();
        if (navHelper != null) {
            if(navHelper.getCurrentDestination().getId() == R.id.homeFragment){
                getActivity().finish();
                return false;
            }
            Log.e("Pop here ok", "2");
            return navHelper.popBackStack();
        } else if (getView() != null) {
            Log.e("Pop here", "2");

            Navigation.findNavController(getView()).popBackStack();
        }

        return false;
    }

    public NavHelper findMainNav() {
        return (getActivity() instanceof MainActivity) ? ((MainActivity) getActivity()).navHelper : null;
    }

    public void onRefreshData(Bundle bundle) {
        //this function is refreash data
    }

    public abstract void setRefreshBundle(Bundle bundle);

}