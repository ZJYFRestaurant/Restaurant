package com.promo.zjyfrestaurant.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.promo.zjyfrestaurant.bean.DishBean;

import java.util.ArrayList;

/**
 * 异步线程操作数据库。
 * <p/>
 * Created by ACER on 2014/12/29.
 */
public class CartAsync extends AsyncTask<DishBean, Integer, ArrayList<DishBean>> {

    public static final int ADD = 2;    //向购物车添加数据。
    public static final int UPDATE = 3; //更新购物车。
    public static final int CLEAR = 4;  //清空数据库。
    public static final int SELECT = 5;    //显示数据。

    private int methodtype;
    private Context context;

    public CartAsync(int methodType, Context context) {
        this.methodtype = methodType;
        this.context = context;
    }

    @Override
    protected ArrayList<DishBean> doInBackground(DishBean[] params) {

        switch (methodtype) {
            case ADD: {
                addData(params[0]);
                return null;
            }
            case UPDATE: {

                updateDB(params[0]);
                return null;
            }
            case CLEAR: {

                clearData();

                return null;
            }
            case SELECT: {
                return getCartData();
            }
            default: {
                return null;
            }
        }
    }

    /**
     * 更新数据库。
     */
    private void updateDB(DishBean dishBean) {
        CartDao dao = new CartDao(context);
        dao.updateDish(dishBean);
    }

    /**
     * 添加数据。
     *
     * @param dishBean
     */
    private void addData(DishBean dishBean) {
        CartDao dao = new CartDao(context);
        dao.addDish(dishBean);
    }

    /**
     * 查询购物车数据。
     *
     * @return
     */
    private ArrayList<DishBean> getCartData() {
        CartDao dao = new CartDao(context);
        return dao.getDishes();
    }

    /**
     * 清空数据。
     */
    private void clearData() {
        CartDao dao = new CartDao(context);
        dao.clearCart();
    }


}
