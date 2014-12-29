package com.promo.zjyfrestaurant.bean;

/**
 * Created by ACER on 2014/12/29.
 */
public class ProductBean {

    private int id;
    private String name;
    private int old_price;
    private int new_price;
    private String cover;
    private int star;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOld_price() {
        return old_price;
    }

    public void setOld_price(int old_price) {
        this.old_price = old_price;
    }

    public int getNew_price() {
        return new_price;
    }

    public void setNew_price(int new_price) {
        this.new_price = new_price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
