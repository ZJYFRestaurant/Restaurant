package com.promote.zjyfrestaurant.bean;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ACER on 2014/12/25.
 * <p/>
 * data中为list的返回结果。
 */
public class ResultBaseListBean<T> {

    BaseListResponse<T> response;

    public BaseListResponse<T> getResponse() {
        return response;
    }


    public static ResultBaseListBean fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultBaseListBean.class, clazz);
        return gson.fromJson(json, objectType);
    }

    public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultBaseListBean.class, clazz);
        return gson.toJson(this, objectType);
    }


    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
