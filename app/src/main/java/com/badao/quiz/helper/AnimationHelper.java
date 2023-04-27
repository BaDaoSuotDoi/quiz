package com.badao.quiz.helper;

import androidx.navigation.NavOptions;

import com.badao.quiz.R;
import com.badao.quiz.base.animation.AnimationType;

public class AnimationHelper {
    public static NavOptions createNavAnimation(AnimationType animationType) {
        NavOptions.Builder builder = new NavOptions.Builder();
        builder.setEnterAnim(R.anim.right_to_left)
                .setExitAnim(R.anim.left_to_right_exit)
                .setPopEnterAnim(R.anim.left_to_right)
                .setPopExitAnim(R.anim.right_to_left_exit);


        return builder.build();
    }

    public static NavOptions createNavAnimationClearStack(int popupToMainId) {
        NavOptions.Builder builder = new NavOptions.Builder();
        builder.setEnterAnim(R.anim.right_to_left)
                .setExitAnim(R.anim.left_to_right_exit)
                .setPopEnterAnim(R.anim.left_to_right)
                .setPopExitAnim(R.anim.right_to_left_exit)
                .setPopUpTo(popupToMainId, false);
        return builder.build();
    }
}