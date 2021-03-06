package com.promote.zjyfrestaurant.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/25.
 */
public class BaseListResponse<T> implements Serializable {

    ArrayList<T> data;

    private String msg;
    int code;

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static BaseListResponse fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(BaseListResponse.class, clazz);
        return gson.fromJson(json, objectType);

    }

    public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(BaseListResponse.class, clazz);
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
