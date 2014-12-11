package com.jch.lib.view.Dialog.niftyefffectslib;

import com.jch.lib.view.Dialog.niftyefffectslib.effects.BaseEffects;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.FadeIn;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.Fall;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.FlipH;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.FlipV;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.NewsPaper;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.RotateBottom;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.RotateLeft;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.Shake;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.SideFall;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.SlideBottom;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.SlideLeft;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.SlideRight;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.SlideTop;
import com.jch.lib.view.Dialog.niftyefffectslib.effects.Slit;

/**
 * Created by lee on 2014/7/30.
 */
public enum Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects = null;
        try {
            bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            throw new Error("Can not init animatorClazz instance");
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            throw new Error("Can not init animatorClazz instance");
        }
        return bEffects;
    }
}
