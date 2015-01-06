package com.promo.zjyfrestaurant.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by ACER on 2015/1/5.
 * <p/>
 * 我的订餐数据。
 */
public class OrderTimeBean {

    private ArrayList<TimeOrderBean> time = new ArrayList<>();

    private HashMap<String, ArrayList<TimeOrderBean>> orderDatas = new HashMap<String, ArrayList<TimeOrderBean>>();

    public ArrayList<TimeOrderBean> getTime() {
        return time;
    }

    public void setTime(ArrayList<TimeOrderBean> timeOrderBeans) {
        this.time = timeOrderBeans;
    }

    public Set<String> getTimeOrderKeys() {
        return orderDatas.keySet();
    }

    public ArrayList<ArrayList<TimeOrderBean>> getTimeOrderValue() {

        return (ArrayList) orderDatas.values();
    }

    public HashMap<String, ArrayList<TimeOrderBean>> getOrderDatas() {
        return orderDatas;
    }

    public void setOrderDatas(HashMap<String, ArrayList<TimeOrderBean>> orderDatas) {
        this.orderDatas = orderDatas;
    }

    public void parseData() {

        for (TimeOrderBean timeOrderBean : time) {      //将一维数组变成二维数组.
            if (orderDatas.containsKey(timeOrderBean.getCreate_time())) {       //这个time已经存在
                ArrayList<TimeOrderBean> timeOrderBeans = orderDatas.get(timeOrderBean.getCreate_time());
                timeOrderBeans.add(timeOrderBean);
                orderDatas.put(timeOrderBean.getCreate_time(), timeOrderBeans);
            } else {        //time 不存在。
                ArrayList<TimeOrderBean> timeOrderBeans = new ArrayList<TimeOrderBean>();
                timeOrderBeans.add(timeOrderBean);
                orderDatas.put(timeOrderBean.getCreate_time(), timeOrderBeans);
            }
        }
    }

}
