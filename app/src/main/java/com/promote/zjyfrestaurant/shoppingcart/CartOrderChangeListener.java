package com.promote.zjyfrestaurant.shoppingcart;

/**
 * Created by ACER on 2014/12/30.
 */
public interface CartOrderChangeListener {

    public void onOrderChange(ShoppingCart shoppingCart);

    public void onOrderAllDesh(boolean orderall);
}
