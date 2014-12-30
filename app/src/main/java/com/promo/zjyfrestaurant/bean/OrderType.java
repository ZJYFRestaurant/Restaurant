package com.promo.zjyfrestaurant.bean;

/**
 * Created by ACER on 2014/12/30.
 * <p/>
 * 菜单预约类型。
 */
public enum OrderType {
    ARRIVE(0), BRING(1), SEND(2);

    private int value;

    private OrderType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
