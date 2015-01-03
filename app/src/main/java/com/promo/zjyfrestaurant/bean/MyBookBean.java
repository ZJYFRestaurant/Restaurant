package com.promo.zjyfrestaurant.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ACER on 2014/12/31.
 * <p/>
 * 我的订单实例。
 */
public class MyBookBean implements Parcelable {

    private int id;
    private int type;
    private int people_num;
    private String use_time;
    private String cover;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPeople_num() {
        return people_num;
    }

    public void setPeople_num(int people_num) {
        this.people_num = people_num;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.type);
        dest.writeInt(this.people_num);
        dest.writeString(this.use_time);
        dest.writeString(this.cover);
    }

    public MyBookBean() {
    }

    private MyBookBean(Parcel in) {
        this.id = in.readInt();
        this.type = in.readInt();
        this.people_num = in.readInt();
        this.use_time = in.readString();
        this.cover = in.readString();
    }

    public static final Parcelable.Creator<MyBookBean> CREATOR = new Parcelable.Creator<MyBookBean>() {
        public MyBookBean createFromParcel(Parcel source) {
            return new MyBookBean(source);
        }

        public MyBookBean[] newArray(int size) {
            return new MyBookBean[size];
        }
    };
}
