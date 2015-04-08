package com.promote.zjyfrestaurant.shoppingcart;

/**
 * Created by ACER on 2014/12/30.
 * <p/>
 * 菜品订餐被监听者。
 */
public class DishObservble {

    private boolean change = false;

    private CartOberver oberver = null;

    public void registerObserver(CartOberver oberver) {
        this.oberver = oberver;
    }


    public void unRegisterObserver() {
        oberver = null;
    }

    /**
     * 通知监听者数据改变。
     */
    public void nodifyDataChanged() {

        if (change && oberver != null) {
            oberver.update();
            change = false;
        }
    }

    public void dataChanged() {
        change = true;
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }


}
