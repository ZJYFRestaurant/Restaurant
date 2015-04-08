package com.promote.zjyfrestaurant.impl;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.promote.zjyfrestaurant.util.DeviceInfo;

/**
 * Created by ACER on 2014/12/11.
 */
public class ZJYFRequestParmater extends RequestParams {

    public ZJYFRequestParmater(Context context) {

        super();
        put("os", "android");
        put("time", DeviceInfo.getmCurTimeToken());
        put("uuid", DeviceInfo.getImei(context));
        put("secret", DeviceInfo.getAuth(context));
        put("version", DeviceInfo.getVersionCode(context));

    }
}
