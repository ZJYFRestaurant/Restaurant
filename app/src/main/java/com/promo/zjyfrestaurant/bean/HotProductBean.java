package com.promo.zjyfrestaurant.bean;

/**
 * Created by ACER on 2014/12/12.
 */
public class HotProductBean {
    /**
     * 评星等级。*
     */
    private int start;

    private int id;

    private String name;

    private float price;

    private String cover;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
