package com.promo.zjyfrestaurant.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ACER on 2014/12/16.
 */
public class AddressBean implements Parcelable {

    private String name;

    private String phone;

    private String addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(name);
        out.writeString(phone);
        out.writeString(addr);

    }

    public AddressBean() {
    }

    private AddressBean(Parcel in) {
        name = in.readString();
        phone = in.readString();
        addr = in.readString();
    }

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {

        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };

}
