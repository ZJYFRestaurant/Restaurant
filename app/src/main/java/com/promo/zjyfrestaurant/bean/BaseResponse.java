package com.promo.zjyfrestaurant.bean;

/**
 * Created by ACER on 2014/12/12.
 */
public class BaseResponse<T> {

    T data;
    int code;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
