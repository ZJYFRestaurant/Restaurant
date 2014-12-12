package com.promo.zjyfrestaurant.impl;

import android.app.Activity;
import android.app.Dialog;

import com.jch.lib.util.DialogUtil;
import com.jch.lib.util.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.promo.zjyfrestaurant.bean.BaseResponse;
import com.promo.zjyfrestaurant.bean.ResultBaseBean;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by ACER on 2014/12/12.
 */
public class ShowMenuRequset {


    public static <T> T getData(Activity context, String url, ZJYFRequestParmater parmater, final Class<T> data, final RequestCallback callback) {

        final Dialog dialog = DialogUtil.waitingDialog(context);
        HttpUtil.post(url, parmater, new JsonHttpResponseHandler("utf-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                BaseResponse<T> resultBaseBean = ResultBaseBean.getResponse(response.toString(), data);
                if (resultBaseBean.getCode() == 100) {
                    callback.onSuccess(resultBaseBean.getData());
                } else {
                    callback.onfailed("网络数据错误。");
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.onfailed("网络数据错误");
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callback.onfailed("网络数据获取失败");
                dialog.dismiss();
            }
        });

        return null;
    }


}
