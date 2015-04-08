package com.promote.zjyfrestaurant.shoppingcart;

/**
 * Created by ACER on 2014/12/30.
 * <p/>
 * 点餐车监听者。
 */
public interface ShoppingCartObserver {

    public void update(ShoppingCartSubject subject, int num);
}
