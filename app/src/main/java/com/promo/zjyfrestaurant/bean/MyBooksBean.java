package com.promo.zjyfrestaurant.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/31.
 */
public class MyBooksBean implements Parcelable {

    private ArrayList<MyBookBean> overdue;
    private ArrayList<MyBookBean> normal;

    public ArrayList<MyBookBean> getOverdue() {
        return overdue;
    }

    public void setOverdue(ArrayList<MyBookBean> overdue) {
        this.overdue = overdue;
    }

    public ArrayList<MyBookBean> getNormal() {
        return normal;
    }

    public void setNormal(ArrayList<MyBookBean> normal) {
        this.normal = normal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.overdue);
        dest.writeSerializable(this.normal);
    }

    public MyBooksBean() {
    }

    private MyBooksBean(Parcel in) {
        this.overdue = (ArrayList<MyBookBean>) in.readSerializable();
        this.normal = (ArrayList<MyBookBean>) in.readSerializable();
    }

    public static final Parcelable.Creator<MyBooksBean> CREATOR = new Parcelable.Creator<MyBooksBean>() {
        public MyBooksBean createFromParcel(Parcel source) {
            return new MyBooksBean(source);
        }

        public MyBooksBean[] newArray(int size) {
            return new MyBooksBean[size];
        }
    };
}
