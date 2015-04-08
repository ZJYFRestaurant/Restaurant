package com.promote.zjyfrestaurant.bean;

import java.util.ArrayList;

/**
 * Created by ACER on 2015/1/14.
 */
public class OrderDetailBean {
    /**
     * 用户id.
     */
    private int uid;
    /**
     * 附加条件。
     */
    private String remark;
    /**
     * 订单状态。
     */
    private int status;
    /**
     * 预约时间。
     */
    private String use_time;
    /**
     * 电话。
     */
    private String tel;

    /**
     *
     */
    private int is_delete;
    /**
     * 订单类型。
     */
    private int type;
    /**
     * 联系人。
     */
    private String contact;
    /**
     * 序列号。
     */
    private int id;
    /**
     * 价格。
     */
    private double price;
    /**
     * 地址。
     */
    private String address;
    /**
     * 地址序号。
     */
    private int address_id;
    /**
     * 订单创建时间。
     */
    private String create_time;
    /**
     * 人数。
     */
    private int people_num;
    /**
     * 产品。
     */
    private ArrayList<DishBean> products;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getType() {
        return type;
    }

    /**
     * 返回订单类型。
     *
     * @return
     */
    public OrderType getOrderType() {

        OrderType orderType = null;
        switch (type) {
            case 0: {
                orderType = OrderType.ARRIVE;
                break;
            }
            case 1: {
                orderType = OrderType.BRING;
                break;
            }
            case 2: {
                orderType = OrderType.SEND;
                break;
            }
        }
        return orderType;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getPeople_num() {
        return people_num;
    }

    public void setPeople_num(int people_num) {
        this.people_num = people_num;
    }

    public ArrayList<DishBean> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<DishBean> products) {
        this.products = products;
    }


}
