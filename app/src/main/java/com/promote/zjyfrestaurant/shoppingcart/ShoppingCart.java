package com.promote.zjyfrestaurant.shoppingcart;

import android.content.Context;

import com.promote.zjyfrestaurant.bean.DishBean;
import com.promote.zjyfrestaurant.impl.CartAsync;
import com.promote.zjyfrestaurant.util.LogCat;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/22.
 * <p/>
 * 购物车，单例模式。
 * <p/>
 * 购物车订购监听者。
 */
public class ShoppingCart extends ShoppingCartSubject implements CartOberver {


    private ArrayList<DishBean> dishBeans = new ArrayList<>();
    /**
     * 单体实例。*
     */
    private static ShoppingCart instance;

    private Context mContext;

    private CartOrderChangeListener orderChangeListener;

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
                ShoppingCart.this.dishBeans.clear();

                for (DishBean dishBean : dishBeans) {
                    dishBean.registerObserver(ShoppingCart.this);           //向菜品实例中注册订餐监听者。
                    ShoppingCart.this.dishBeans.add(dishBean);
                }

            }
        }.execute();

    }

    public CartOrderChangeListener getOrderChangeListener() {
        return orderChangeListener;
    }

    public void setOrderChangeListener(CartOrderChangeListener orderChangeListener) {
        this.orderChangeListener = orderChangeListener;
    }

    public void cleanOrderChangeListener() {
        this.orderChangeListener = null;
    }

    /**
     * 添加菜品。
     *
     * @param dish
     * @return int  dishBean 的数量。
     */
    public int addDish(DishBean dish) {

        return addDishes(dish, 1);
    }

    /**
     * 添加过个菜品。
     *
     * @param dishBean
     * @param num
     * @return int      dishBean 的数量。
     */
    public int addDishes(DishBean dishBean, int num) {

        int id = dishBean.getId();
        for (DishBean dish : dishBeans) {

            if (id == dish.getId()) {
                dish.setNum(dish.getNum() + num);
                new CartAsync(CartAsync.UPDATE, mContext).execute(dish);        //更新数据库
                setChanged();
                notifyObservers(getDishNum());
                LogCat.d("update dish id:" + dishBean.getId() + "----num:" + dishBean.getNum());
                return dish.getNum();
            }
        }
        dishBean.setNum(num);
        dishBean.registerObserver(ShoppingCart.this);
        dishBeans.add(dishBean);
        new CartAsync(CartAsync.ADD, mContext).execute(dishBean);
        LogCat.d("add dish id:" + dishBean.getId() + "----num:" + dishBean.getNum());
        setChanged();
        notifyObservers(getDishNum());

        return dishBean.getNum();
    }

    /**
     * 减少菜品。
     *
     * @param dish
     * @return int  dishBean 的数量。
     */
    public int reduceDish(DishBean dish) {

        return reduceDishes(dish, 1);
    }

    /**
     * 减少菜品。
     *
     * @param dishBean
     * @param num      dishBean 的数量。
     * @return int dishBean 的数量。
     */
    public int reduceDishes(DishBean dishBean, int num) {
        ArrayList<DishBean> copyDishBeanes = new ArrayList<>(dishBeans);
        int id = dishBean.getId();
        int dishNum = 0;
        for (DishBean dish : copyDishBeanes) {

            if (id == dish.getId()) {
                dish.setNum(dish.getNum() - num);
                new CartAsync(CartAsync.UPDATE, mContext).execute(dish);        //更新数据库
                dishNum = dish.getNum();
                // 当菜品的数量为0是 删除此菜品。
                if (dishBean.getNum() == 0) {
                    delDish(dishBean);
                }

                setChanged();
                notifyObservers(getDishNum());
            }
        }
        copyDishBeanes.clear();
        copyDishBeanes = null;
        return dishNum;
    }

    /**
     * 获得菜品数量。
     *
     * @param id 菜品id。
     * @return 菜品数量。
     */
    public int getDishNum(int id) {
        int num = 0;
        for (DishBean dishBean : dishBeans) {
            if (id == dishBean.getId()) {
                num = dishBean.getNum();
                break;
            }
        }
        return num;
    }

    /**
     * 从购物车中减去指定的菜品。
     *
     * @param id
     */
    private void reduceDish(int id) {
        ArrayList<DishBean> copyDishBeanes = new ArrayList<>(dishBeans);
        for (DishBean dishBean : copyDishBeanes) {

            if (dishBean.getId() == id) {

                if (dishBean.getNum() != 0) {
                    dishBean.setNum(dishBean.getNum() - 1);
                    new CartAsync(CartAsync.UPDATE, mContext).execute(dishBean);        //更新数据库
                    //当菜品的数量为0是 删除此菜品。
                    if (dishBean.getNum() == 0) {
                        delDish(dishBean);
                    }

                    setChanged();
                    notifyObservers(getDishNum());
                }

                break;
            }
        }
        copyDishBeanes.clear();
        copyDishBeanes = null;

    }

    /**
     * 从购物车中减去固定数量的菜品。
     *
     * @param id
     * @param num
     */
    private void reduceDish(int id, int num) {

        ArrayList<DishBean> copyDishBeanes = new ArrayList<>(dishBeans);
        for (DishBean dishBean : copyDishBeanes) {

            if (dishBean.getId() == id) {

                if (dishBean.getNum() != 0) {
                    dishBean.setNum(dishBean.getNum() - num);
                    new CartAsync(CartAsync.UPDATE, mContext).execute(dishBean);        //更新数据库
                    // 当菜品的数量为0是 删除此菜品。
                    if (dishBean.getNum() == 0) {
                        delDish(dishBean);
                    }
                    setChanged();
                    notifyObservers(getDishNum());
                    break;
                }
            }
        }

        copyDishBeanes.clear();
        copyDishBeanes = null;
    }

    /**
     * 删除菜品。
     *
     * @param dishBean
     */
    private void delDish(DishBean dishBean) {
        dishBeans.remove(dishBean);
        new CartAsync(CartAsync.DEL, mContext).execute(dishBean);
    }

    /**
     * 清空购物车。
     */

    public void clearCart() {
        dishBeans.clear();
        new CartAsync(CartAsync.CLEAR, mContext).execute();        //更新数据库
        setChanged();
        notifyObservers(getDishNum());      //
    }


    public ArrayList<DishBean> getDishBeans() {
        ArrayList<DishBean> dishBeanses = new ArrayList<>();
        for (DishBean dishBean : dishBeans) {

            if (dishBean.getNum() != 0)
                dishBeanses.add(dishBean);
        }
        return dishBeanses;
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

    /**
     * 删除所有没有下订单的菜品。
     *
     * @return int 删除后剩余的菜品。
     */
    public int deleteUnOrderDish() {
        int dishNum = 0;
        ArrayList<DishBean> copyDishBeanes = new ArrayList<>(dishBeans);
        for (DishBean dishBean : copyDishBeanes) {
            if (dishBean.getIsOrder() == 0)
                delDish(dishBean);
            else
                dishNum += dishBean.getNum();
        }

        copyDishBeanes.clear();
        copyDishBeanes = null;
        return dishNum;

    }

    /**
     * 获取订餐数量。
     *
     * @return
     */
    public int getOrderNum() {
        int orderNum = 0;
        for (DishBean dishBean : dishBeans) {
            if (dishBean.getIsOrder() == 1) {
                orderNum += dishBean.getNum();
            }
        }
        return orderNum;
    }

    public int getTotalOrderPrice() {
        int price = 0;
        for (DishBean dishBean : dishBeans) {

            if (dishBean.getIsOrder() == 1) {
                price += (dishBean.getNew_price() * dishBean.getNum());
            }
        }
        return price;
    }

    /**
     * 订餐数据改变,通知外部接口从新计算。
     */
    @Override
    public synchronized void update() {

        if (orderChangeListener != null) {
            orderChangeListener.onOrderChange(this);
            orderChangeListener.onOrderAllDesh(isOrderAll());
        }
    }

    private boolean isOrderAll() {

        int checkAll = 1;
        for (DishBean dishBean : dishBeans) {
            checkAll = checkAll & dishBean.getIsOrder();
            if (checkAll == 0)
                return false;       //没有全选。
        }

        return true;            //全选。
    }


    /**
     * 订购所有菜品。
     *
     * @param order 是否订购。
     */
    public void orderAllDish(boolean order) {
        int orderflag = order ? 1 : 0;
        for (DishBean dishBean : dishBeans) {
            dishBean.setIsOrder(orderflag);
        }
    }

    /**
     * 获取所有订购菜品。
     *
     * @return
     */
    public ArrayList<DishBean> getOrderDishes() {

        ArrayList<DishBean> orders = new ArrayList<DishBean>();
        for (DishBean dishBean : dishBeans) {
            if (dishBean.getIsOrder() == 1 && dishBean.getNum() != 0) {     //选择的订单菜品。
                orders.add(dishBean);
            }
        }
        return orders;
    }

}
