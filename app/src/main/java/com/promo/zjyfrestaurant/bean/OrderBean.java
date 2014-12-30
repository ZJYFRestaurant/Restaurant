package com.promo.zjyfrestaurant.bean;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/30.
 * 订单列表。
 */
public class OrderBean {
    /**
     * 地址id. *
     */
    private int address_id;
    /**
     * 地址内容 *
     */
    private String address_content;
    /**
     * 联系人 *
     */
    private String contact;
    /**
     * 联系人电话 *
     */
    private String tel;
    /**
     * 类型：0订座，1到店取菜，2外卖
     */
    private OrderType type;
    /**
     * 订座及到店取菜的时间
     */
    private String use_time;
    /**
     * 订座人数。
     */
    private int people_num;
    /**
     * 备注信息。
     */
    private String remark;
    /**
     * 总价格。
     */
    private int price;
    /**
     * 订单产品。 *
     */
    private ArrayList<DishBean> products = new ArrayList<>();

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getAddress_content() {
        return address_content;
    }

    public void setAddress_content(String address_content) {
        this.address_content = address_content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public int getPeople_num() {
        return people_num;
    }

    public void setPeople_num(int people_num) {
        this.people_num = people_num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void addProduct(DishBean dishBean) {
        products.add(dishBean);
    }

    public boolean removeProduct(DishBean dishBean) {
        if (products.contains(dishBean))
            return products.remove(dishBean);
        return false;
    }

    /**
     * 获得订餐列表。
     *
     * @return
     */
    public String getProductStr() {

        StringBuilder sb = new StringBuilder();
        for (DishBean dishBean : products) {
            sb.append(dishBean.getId()).append("*").append(dishBean.getNum()).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
