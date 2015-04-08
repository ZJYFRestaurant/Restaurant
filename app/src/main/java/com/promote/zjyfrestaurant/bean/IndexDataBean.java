package com.promote.zjyfrestaurant.bean;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/12.
 */
public class IndexDataBean {

    private IndexAdv ads;

    private ArrayList<HotProductBean> hotProductList;

    public IndexAdv getAds() {
        return ads;
    }

    public void setAds(IndexAdv ads) {
        this.ads = ads;
    }

    public ArrayList<HotProductBean> getHotProductList() {
        return hotProductList;
    }

    public void setHotProductList(ArrayList<HotProductBean> hotProductList) {
        this.hotProductList = hotProductList;
    }


}
