package com.promo.zjyfrestaurant.impl;

import android.app.Activity;
import android.app.Dialog;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jch.lib.util.DialogUtil;
import com.jch.lib.util.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.promo.zjyfrestaurant.bean.BaseResponse;
import com.promo.zjyfrestaurant.bean.ResultBaseBean;

import org.apache.http.Header;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by ACER on 2014/12/12.
 */
public class ShowMenuRequset {


    /**
     * 获得返回结果。
     *
     * @param jsonStr
     * @return
     */
    public static <T> BaseResponse<T> getResponse(String jsonStr, Class<T> t) {

        Gson gson = new Gson();

        Type type = new TypeToken<ResultBaseBean<T>>() {
        }.getType();
        ResultBaseBean<T> resultBaseBean = gson.fromJson(jsonStr, type);


        return resultBaseBean.getResponse();
    }

    public static <T> T getData(Activity context, String url, ZJYFRequestParmater parmater, final Class<T> t, final RequestCallback callback) {

        final Dialog dialog = DialogUtil.waitingDialog(context);
        HttpUtil.post(url, parmater, new JsonHttpResponseHandler("utf-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                BaseResponse<T> resultBaseBean = getResponse(response.toString(), t);

                if (resultBaseBean.getCode() == 100) {
                    Gson gson = new Gson();
                    String dataJsonStr = gson.toJson(resultBaseBean.getData());         //第二次解析内部data数据。由于泛型在gson中不能做第二层参数，需要将response中的data从新变成json,然后用Gson第二次解析。
                    T dataBean = (T) gson.fromJson(dataJsonStr, t);
                    callback.onSuccess(dataBean);
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
