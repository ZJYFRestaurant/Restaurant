package com.promote.zjyfrestaurant.bean;

/**
 * Created by ACER on 2014/12/12.
 */
public class BaseResponse<T> {

    T data;
    int code;
    /**
     * 错误信息。 *
     */
    String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
