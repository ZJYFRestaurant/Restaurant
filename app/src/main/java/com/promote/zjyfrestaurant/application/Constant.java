package com.promote.zjyfrestaurant.application;

import android.graphics.Point;
import android.os.Environment;

/**
 * Created by ACER on 2014/12/10.
 */
public class Constant {

    public static final String PRO_TAG = "ZJYF";

    public static final String UID = "uid";
    /**
     * 引导页 *
     */
    public static final String GUIDE = "guide";

    /**
     * 是否可发行
     */
    public static final Boolean RELEASEABLE = false;

    public static final int DB_VERSION = 7;

    /**
     * 磁盘缓存路径. *
     */
    public static final String CACHE_DIR = Environment.getExternalStorageDirectory() + "/ZJYF";
    /**
     * 圖片緩存路徑. *
     */
    public static final String IMG_DIR = CACHE_DIR + "/images";

    public static final Point HOME_IMG_POINT = new Point(500, 250);

    public static final Point PRIV_IMG_POINT = new Point(286, 160);

    public static final Point RECM_IMG_POINT = new Point(600, 288);

    public static final Point GUID_IMG_POINT = new Point(1000, 1280);

    public static final Point FEED_BACK_IMG = new Point(608, 124);
}
