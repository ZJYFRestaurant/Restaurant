package com.promo.zjyfrestaurant.impl;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.promo.zjyfrestaurant.util.DeviceInfo;

/**
 * Created by ACER on 2014/12/11.
 */
public class ZJYFRequestParmater extends RequestParams {

    public ZJYFRequestParmater(Context context) {

        super();
        put("time", DeviceInfo.getmCurTimeToken());
        put("uuid", DeviceInfo.getImei(context));
        put("secret", DeviceInfo.getAuth(context));
        put("version", DeviceInfo.getVersionCode(context));

    }
}
