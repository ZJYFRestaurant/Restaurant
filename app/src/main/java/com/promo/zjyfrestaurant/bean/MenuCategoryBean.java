package com.promo.zjyfrestaurant.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ACER on 2014/12/29.
 */
public class MenuCategoryBean implements Parcelable {

    private int id;
    private String name;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public MenuCategoryBean() {
    }

    private MenuCategoryBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<MenuCategoryBean> CREATOR = new Parcelable.Creator<MenuCategoryBean>() {
        public MenuCategoryBean createFromParcel(Parcel source) {
            return new MenuCategoryBean(source);
        }

        public MenuCategoryBean[] newArray(int size) {
            return new MenuCategoryBean[size];
        }
    };
}
