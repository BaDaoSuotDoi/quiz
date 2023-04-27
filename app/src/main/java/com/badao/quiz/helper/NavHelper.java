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

        Log.e("actionId:", actionId+"//"+ R.id.homeFragment);
        navController.navigate(actionId, null, options);
    }

    public void navigate(int layoutId, AnimationType type) {
        if (layoutId == R.id.loginFragment) {
            forceNavigate(layoutId, type, null);
            return;
        }
        if (hasBackStackNavigationId(layoutId)) {
            NavDestination current = getCurrentDestination();
            if (current != null && current.getId() == layoutId) {
                BaseFragment baseFragment = (BaseFragment) getCurrentFragment();
                if (baseFragment != null) {
                    baseFragment.onRefreshData(null);
                    return;
                }
            }

            popBackStack(layoutId, false);
        } else {
            forceNavigate(layoutId, type, null);
        }
    }

    public void navigate(int layoutId, Bundle bundle, AnimationType type) {
        if (layoutId == R.id.loginFragment) {
            forceNavigate(layoutId, type, null);
            return;
        }
        if (hasBackStackNavigationId(layoutId)) {
            NavDestination current = getCurrentDestination();
            if (current != null && current.getId() == layoutId) {
                BaseFragment baseFragment = (BaseFragment) getCurrentFragment();
                if (baseFragment != null) {
                    baseFragment.onRefreshData(bundle);
                    return;
                }
            }

            popBackStack(layoutId, false, bundle);
        } else {
            forceNavigate(layoutId, type, bundle);
        }
    }

    public void forceNavigate(int layoutId, AnimationType type, Bundle bundle) {
        NavOptions options = createNavOptions(layoutId, type);
        Log.e("layout", layoutId+"//"+R.layout.fragment_home);
        navController.navigate(layoutId, bundle, options);
    }

    public boolean popBackStack() {
        return navController.popBackStack();
    }

    public boolean popBackStack(int destinationId, boolean inclusive) {
        return navController.popBackStack(destinationId, inclusive);
    }

    public void popBackStack(int destinationId, boolean inclusive, Bundle bundle) {
        NavBackStackEntry entry = findDesinationFromBackstack(destinationId);
        if (entry == null) {
            forceNavigate(destinationId, AnimationType.NONE, bundle);
            return;
        }

        NavDestination destination = getCurrentDestination();
        if (destination != null && destination.getId() == destinationId) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof BaseFragment) {
                ((BaseFragment) currentFragment).setRefreshBundle(bundle);
                ((BaseFragment) currentFragment).onRefreshData(bundle);
            }
            return;
        }

        entry.getSavedStateHandle().set(AppConstants.EXTRAS_REFRESH_BUNDLE, bundle);
        popBackStack(destinationId, inclusive);
    }

    public NavDestination getCurrentDestination() {
        return navController != null ? navController.getCurrentDestination() : null;
    }

    private NavBackStackEntry findDesinationFromBackstack(int destinationId) {
        try {
            return navController.getBackStackEntry(destinationId);
        } catch (Exception e) {
            return null;
        }
    }

    private Fragment getCurrentFragment() {
        Fragment navigationFragment = activity.getSupportFragmentManager().getPrimaryNavigationFragment();
        if (navigationFragment != null) {
            return navigationFragment.getChildFragmentManager().getPrimaryNavigationFragment();
        }
        return null;
    }

    public Bundle getRefreshDataBundle() {
        try {
            return navController.getCurrentBackStackEntry().getSavedStateHandle().get(AppConstants.EXTRAS_REFRESH_BUNDLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean hasBackStackNavigationId(int destinationId) {
        try {
            navController.getBackStackEntry(destinationId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private NavOptions createNavOptions(int layoutId, AnimationType type) {
//        if (layoutId == R.id.loginFragment) {
//            return AnimationHelper.createNavAnimationClearStack(R.id.loginFragment);
//        }
        return AnimationHelper.createNavAnimation(type);
    }
}
