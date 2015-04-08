package com.promote.zjyfrestaurant.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;
import com.jch.lib.util.DialogUtil;

/**
 * Created by ACER on 2014/12/18.
 */
public class SDKReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String s = intent.getAction();
        if (s != null && s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
            DialogUtil.toastMsg(context, "key 验证出错!");
        } else if (s != null && s.equals(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)) {
            DialogUtil.toastMsg(context, "网络出错");
        }

    }
}
