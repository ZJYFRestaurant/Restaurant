package com.jch.lib.view.Dialog.niftyefffectslib.effects;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;


/**
 * Created by lee on 2014/7/31.
 */
public class SideFall extends BaseEffects {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 2, 1.5f, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "scaleY", 2, 1.5f, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "rotation", 25, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "translationX", 80, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration * 3 / 2)

        );
    }
}
