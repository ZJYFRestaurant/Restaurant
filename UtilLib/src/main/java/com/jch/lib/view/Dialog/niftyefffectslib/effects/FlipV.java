package com.jch.lib.view.Dialog.niftyefffectslib.effects;


import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Created by lee on 2014/7/31.
 */
public class FlipV extends BaseEffects {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "rotationX", -90, 0).setDuration(mDuration)

        );
    }
}
