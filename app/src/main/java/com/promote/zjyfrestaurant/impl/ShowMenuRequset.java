package com.promote.zjyfrestaurant.impl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jch.lib.util.DialogUtil;
import com.jch.lib.util.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.promote.zjyfrestaurant.bean.BaseListResponse;
import com.promote.zjyfrestaurant.bean.BaseResponse;
import com.promote.zjyfrestaurant.bean.ResultBaseBean;
import com.promote.zjyfrestaurant.bean.ResultBaseListBean;

import org.apache.http.Header;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/12.
 */
public class ShowMenuRequset {


    /**
     * 获得返回结果,data中没有list对象。
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

    /**
     * 获得返回结果，data中有list对象。
     *
     * @param jsonStr
     * @return
     */

    public static <T> BaseListResponse<T> getListResponse(String jsonStr, Class<T> t) {
        ResultBaseListBean<T> resultBaseListBean = ResultBaseListBean.fromJson(jsonStr, t);

        return resultBaseListBean.getResponse();
    }

    /**
     * 获得含有数组的数据结果。
     *
     * @param context
     * @param url
     * @param parmater
     * @param listT
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> getListData(Activity context, String url, ZJYFRequestParmater parmater, final Class<T> listT, final RequestCallback callback) {

        final Dialog dialog = DialogUtil.waitingDialog(context);
        HttpUtil.post(url, parmater, new JsonHttpResponseHandler("utf-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                BaseListResponse<T> baseListResponse = getListResponse(response.toString(), listT);

                if (baseListResponse.getCode() == 100) {    //将data中的jsonStr --》 T
                    callback.onSuccess(baseListResponse.getData());
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

    /**
     * 后台获取数据，没有progressDialog
     *
     * @param context
     * @param url
     * @param parmater
     * @param t
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> T getData(Context context, String url, ZJYFRequestParmater parmater, final Class<T> t, final RequestCallback callback) {

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

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.onfailed("网络数据错误");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callback.onfailed("网络数据获取失败");
            }
        });

        return null;
    }

    /**
     * @param context
     * @param url
     * @param parmater
     * @param t
     * @param callback
     * @param <T>
     * @return
     */
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
