package com.badao.quiz.helper;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;
import com.badao.quiz.base.mvp.BaseFragment;
import com.badao.quiz.constants.AppConstants;
import com.badao.quiz.function.main.view.MainActivity;

public class NavHelper {
    MainActivity activity;
    NavController navController;

    public NavHelper(MainActivity activity, int navId) {
        this.activity = activity;
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(navId);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    public void navigateSplash(boolean isLogin) {
        NavOptions options = new NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build();

        int actionId = 0;
        if (!isLogin) {
            actionId = R.id.loginFragment;
        } else{
            actionId = R.id.homeFragment;
        }

        navController.navigate(actionId, null, options);
    }

    public void navigate(int layoutId, AnimationType type) {
        NavOptions options = createNavOptions(layoutId, type);
        navController.navigate(layoutId, null, options);
    }

    public void navigate(int layoutId, Bundle bundle, AnimationType type) {
        NavOptions options = createNavOptions(layoutId, type);
        navController.navigate(layoutId, bundle, options);
    }


    public boolean popBackStack() {
        return navController.popBackStack();
    }


    public NavDestination getCurrentDestination() {
        return navController != null ? navController.getCurrentDestination() : null;
    }


    public Bundle getRefreshDataBundle() {
        try {
            return navController.getCurrentBackStackEntry().getSavedStateHandle().get(AppConstants.EXTRAS_REFRESH_BUNDLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private NavOptions createNavOptions(int layoutId, AnimationType type) {
        if (layoutId == R.id.loginFragment || layoutId == R.id.homeFragment) {
            return AnimationHelper.createNavAnimationClearStack(R.id.loginFragment);
        }
        return AnimationHelper.createNavAnimation(type);
    }
}
