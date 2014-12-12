package com.promo.zjyfrestaurant.application;

import android.os.Environment;

/**
 * Created by ACER on 2014/12/10.
 */
public class Constant {

    public static final String PRO_TAG = "ZJYF";

    /**
     * 是否可发行
     */
    public static final Boolean RELEASEABLE = false;

    /**
     * 磁盘缓存路径. *
     */
    public static final String CACHE_DIR = Environment.getExternalStorageDirectory() + "/ZJYF";
    /**
     * 圖片緩存路徑. *
     */
    public static final String IMG_DIR = CACHE_DIR + "/images";
}
