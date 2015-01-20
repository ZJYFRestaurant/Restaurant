package com.promo.zjyfrestaurant.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.promo.zjyfrestaurant.shoppingcart.DishObservble;

import java.io.Serializable;

/**
 * 菜品。
 * <p/>
 * Created by ACER on 2014/12/22.
 */
public class DishBean extends DishObservble implements Parcelable, Serializable {

    private int id;
    private String name;
    private int category_id;
    private int old_price;
    private int new_price;
    private String cover;
    private String description;
    private int star;
    private int is_hot;
    private int is_show;
    /**
     * 是否订购被: 0 不订购，1 订购（在点餐车中点餐）。
     */
    private int isOrder = 1;
    /**
     * 点餐数量*
     */
    private int num;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.category_id);
        dest.writeFloat(this.old_price);
        dest.writeFloat(this.new_price);
        dest.writeString(this.cover);
        dest.writeString(this.description);
        dest.writeInt(this.star);
        dest.writeInt(this.is_hot);
        dest.writeInt(this.is_show);
    }

    public DishBean() {
    }

    private DishBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.category_id = in.readInt();
        this.old_price = in.readInt();
        this.new_price = in.readInt();
        this.cover = in.readString();
        this.description = in.readString();
        this.star = in.readInt();
        this.is_hot = in.readInt();
        this.is_show = in.readInt();
    }

    public static final Parcelable.Creator<DishBean> CREATOR = new Parcelable.Creator<DishBean>() {
        public DishBean createFromParcel(Parcel source) {
            return new DishBean(source);
        }

        public DishBean[] newArray(int size) {
            return new DishBean[size];
        }
    };

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
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

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(int isOrder) {
        this.isOrder = isOrder;
    }
}
