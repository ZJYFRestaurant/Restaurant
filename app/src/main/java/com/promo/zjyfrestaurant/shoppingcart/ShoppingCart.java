package com.promo.zjyfrestaurant.shoppingcart;

import android.content.Context;

import com.promo.zjyfrestaurant.bean.DishBean;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/22.
 * <p/>
 * 购物车，单例模式。
 */
public class ShoppingCart {


    private ArrayList<DishBean> dishBeans = new ArrayList<>();

    private static ShoppingCart instance;

    private Context mContext;

    public static ShoppingCart newInstance() {

        if (instance == null) {
            instance = new ShoppingCart();
        }

        return instance;
    }

    private ShoppingCart() {

    }

    /**
     * 向购物车中添加菜品。
     *
     * @param id
     */
    public void addDish(int id) {

        for (DishBean dishBean : dishBeans) {

            if (dishBean.getId() == id) {
                dishBean.setNum(dishBean.getNum() + 1);
                break;
            }

        }
    }

    /**
     * 向购物车中添加固定数量的菜品。
     *
     * @param id
     * @param num
     */
    public void addDish(int id, int num) {

        for (DishBean dishBean : dishBeans) {

            if (dishBean.getId() == id) {
                dishBean.setNum(dishBean.getNum() + num);
                break;
            }

        }
    }

    /**
     * 从购物车中减去指定的菜品。
     *
     * @param id
     */
    public void reduceDish(int id) {

        for (DishBean dishBean : dishBeans) {

            if (dishBean.getId() == id) {

                if (dishBean.getNum() != 0) {
                    dishBean.setNum(dishBean.getNum() - 1);
                }

                break;
            }
        }

    }

    /**
     * 从购物车中减去固定数量的菜品。
     *
     * @param id
     * @param num
     */
    public void reduceDish(int id, int num) {
        for (DishBean dishBean : dishBeans) {

            if (dishBean.getId() == id) {

                if (dishBean.getNum() != 0) {
                    dishBean.setNum(dishBean.getNum() - num);
                }

                break;
            }
        }
    }

    public ArrayList<DishBean> getDishBeans() {
        return dishBeans;
    }

    public void setDishBeans(ArrayList<DishBean> dishBeans) {
        this.dishBeans = dishBeans;
    }

    /**
     * 获取购物车中的菜品数量。
     *
     * @return
     */
    public int getDishNum() {

        int dishNum = 12;
        for (DishBean dishBean : dishBeans) {
            dishNum = dishNum + dishBean.getNum();
        }

        return dishNum;
    }
}
