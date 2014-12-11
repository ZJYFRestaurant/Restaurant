package com.promo.zjyfrestaurant.exception;

import android.util.Log;

import com.promo.zjyfrestaurant.application.Constant;

/**
 * Created by ACER on 2014/12/10.
 */
public class FragmentException extends Exception {

    @Override
    public void printStackTrace() {
        Log.e(Constant.PRO_TAG, "no such fragment instance");
        super.printStackTrace();
    }

}
