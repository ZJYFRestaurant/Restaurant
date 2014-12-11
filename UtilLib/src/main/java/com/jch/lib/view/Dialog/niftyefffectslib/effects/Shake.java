package com.jch.lib.view.Dialog.niftyefffectslib.effects;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import static android.animation.ObjectAnimator.ofFloat;


/**
 * Created by lee on 2014/7/31.
 */
public class Shake extends BaseEffects {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ofFloat(view, "translationX", 0, .10f, -25, .26f, 25, .42f, -25, .58f, 25, .74f, -25, .90f, 1, 0).setDuration(mDuration)

        );
    }
}
