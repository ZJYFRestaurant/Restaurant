package com.promo.zjyfrestaurant.impl;

/**
 * Created by ACER on 2014/12/12.
 */
public interface RequestCallback<T> {

    public void onfailed(String msg);

    public void onSuccess(T data);
}
