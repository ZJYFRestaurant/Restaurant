package com.promo.zjyfrestaurant.bean;

import com.google.gson.Gson;

/**
 * Created by ACER on 2014/12/12.
 */
public class ResultBaseBean {


    BaseResponse response;

    public <T> BaseResponse<T> getResponse(Class<T> data) {
        return response;
    }

    /**
     * 获得返回结果。
     *
     * @param jsonStr
     * @return
     */
    public static <T> BaseResponse<T> getResponse(String jsonStr, Class<T> data) {

        Gson gson = new Gson();
        return ((ResultBaseBean) gson.fromJson(jsonStr, ResultBaseBean.class)).getResponse(data);
    }


}
