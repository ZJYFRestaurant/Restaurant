package com.jch.lib.view.Dialog.niftyefffectslib.effects;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;


/**
 * Created by lee on 2014/7/31.
 */
public class NewsPaper extends BaseEffects {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "rotation", 1080, 720, 360, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration * 3 / 2),
                ObjectAnimator.ofFloat(view, "scaleX", 0.1f, 0.5f, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "scaleY", 0.1f, 0.5f, 1).setDuration(mDuration)

        );
    }
}
