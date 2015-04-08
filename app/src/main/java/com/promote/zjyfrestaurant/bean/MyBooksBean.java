package com.promote.zjyfrestaurant.bean;

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

    /**
     * 获得订单类型数量。
     *
     * @return
     */
    public int getBookTypeNum() {

        if ((overdue == null || overdue.size() == 0) && (normal == null || normal.size() == 0))
            return 0;
        else if (overdue != null && overdue.size() > 0 && normal != null && normal.size() > 0)
            return 2;
        else return 1;
    }

    /**
     * 获得当含有一种订单时，该订单的下表。
     *
     * @return 0为nomal, 1为over。2为不是一种订单。
     */
    public int getOneChildIndex() {

        if (getBookTypeNum() == 1) {
            if (getNormal() != null && getNormal().size() > 0)
                return 0;
            else return 1;
        }
        return 2;
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
