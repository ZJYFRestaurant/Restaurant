package com.promote.zjyfrestaurant.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ACER on 2014/12/16.
 * 地址。
 */
public class AddressBean implements Parcelable {

    private int id;

    private String content;

    private String tel;

    private int uid;

    private String contact;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.content);
        dest.writeString(this.tel);
        dest.writeInt(this.uid);
        dest.writeString(this.contact);
    }

    public AddressBean() {
    }

    private AddressBean(Parcel in) {
        this.id = in.readInt();
        this.content = in.readString();
        this.tel = in.readString();
        this.uid = in.readInt();
        this.contact = in.readString();
    }

    public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}
