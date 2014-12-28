package com.promo.zjyfrestaurant.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ACER on 2014/12/26.
 */
public class PrivilegeActionBean implements Parcelable {

    private int id;
    private String title;
    private String create_time;
    private String start_date;
    private String end_date;
    private String read_num;
    private String cover;
    private String content;
    private int is_delete;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.create_time);
        dest.writeString(this.start_date);
        dest.writeString(this.end_date);
        dest.writeString(this.read_num);
        dest.writeString(this.cover);
        dest.writeString(this.content);
        dest.writeInt(this.is_delete);
    }

    public PrivilegeActionBean() {
    }

    private PrivilegeActionBean(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.create_time = in.readString();
        this.start_date = in.readString();
        this.end_date = in.readString();
        this.read_num = in.readString();
        this.cover = in.readString();
        this.content = in.readString();
        this.is_delete = in.readInt();
    }

    public static final Parcelable.Creator<PrivilegeActionBean> CREATOR = new Parcelable.Creator<PrivilegeActionBean>() {
        public PrivilegeActionBean createFromParcel(Parcel source) {
            return new PrivilegeActionBean(source);
        }

        public PrivilegeActionBean[] newArray(int size) {
            return new PrivilegeActionBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }
}
