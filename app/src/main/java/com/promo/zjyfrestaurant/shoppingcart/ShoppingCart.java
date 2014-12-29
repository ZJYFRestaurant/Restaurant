package com.promo.zjyfrestaurant.shoppingcart;

import android.content.Context;

import com.promo.zjyfrestaurant.bean.DishBean;
import com.promo.zjyfrestaurant.impl.CartAsync;

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
     * 从数据库中取数据。
     */
    public void initData(Context context) {
        this.mContext = context;
        new CartAsync(CartAsync.SELECT, context) {  //从数据库中取出数据。

            @Override
            protected void onPostExecute(ArrayList<DishBean> dishBeans) {
                dishBeans.clear();
                dishBeans.addAll(dishBeans);
            }
        }.execute();

    }

    /**
     * 向购物车中添加菜品。
     *
     * @param id
     */
    private void addDishById(int id) {

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
    private void addDishesById(int id, int num) {

        for (DishBean dishBean : dishBeans) {

            if (dishBean.getId() == id) {
                dishBean.setNum(dishBean.getNum() + num);
                break;
            }

        }
    }

    /**
     * 添加菜品。
     *
     * @param dish
     */
    public void addDish(DishBean dish) {

        addDishes(dish, 1);
    }

    /**
     * 添加过个菜品。
     *
     * @param dishBean
     * @param num
     */
    public void addDishes(DishBean dishBean, int num) {

        int id = dishBean.getId();
        for (DishBean dish : dishBeans) {

            if (id == dish.getId()) {
                dish.setNum(dish.getNum() + num);
                new CartAsync(CartAsync.UPDATE, mContext).execute(dishBean);        //更新数据库
                return;
            }
        }
        dishBean.setNum(num);
        dishBeans.add(dishBean);
        new CartAsync(CartAsync.ADD, mContext).execute(dishBean);

    }

    /**
     * 添加菜品。
     *
     * @param dish
     */
    public void reduceDish(DishBean dish) {

        reduceDishes(dish, -1);
    }

    /**
     * 添加过个菜品。
     *
     * @param dishBean
     * @param num
     */
    public void reduceDishes(DishBean dishBean, int num) {

        int id = dishBean.getId();
        for (DishBean dish : dishBeans) {

            if (id == dish.getId()) {
                dish.setNum(dish.getNum() + num);
                new CartAsync(CartAsync.UPDATE, mContext).execute(dishBean);        //更新数据库
                //TODO 当菜品的数量为0是 删除此菜品。
                return;
            }
        }

    }

    /**
     * 从购物车中减去指定的菜品。
     *
     * @param id
     */
    private void reduceDish(int id) {

        for (DishBean dishBean : dishBeans) {

            if (dishBean.getId() == id) {

                if (dishBean.getNum() != 0) {
                    dishBean.setNum(dishBean.getNum() - 1);
                    new CartAsync(CartAsync.UPDATE, mContext).execute(dishBean);        //更新数据库
                    //TODO 当菜品的数量为0是 删除此菜品。

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
    private void reduceDish(int id, int num) {
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

        int dishNum = 0;
        for (DishBean dishBean : dishBeans) {
            dishNum = dishNum + dishBean.getNum();
        }

        return dishNum;
    }
}
