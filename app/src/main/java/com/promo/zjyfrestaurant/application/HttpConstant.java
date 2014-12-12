package com.promo.zjyfrestaurant.application;

/**
 * Created by ACER on 2014/12/11.
 */
public class HttpConstant {
    /**
     * 密钥. *
     */
    public static final String KEY = "ju34s4&6d567nuwe678l89kjdf56o34iw!e";

    private static final boolean HOSTLOCALABLE = true;

    private static final String HOST_LOCAL = "http://192.168.1.112";

    private static final String HOST_REMOTE = "";

    private static final String PORT = "";

    private static final String ROOT_URL = "/restaurant/index.php?s=/Home/Api/";

    /**
     * 获取基本连接
     *
     * @return
     */
    private static String getRootUrl() {
        String rootUrl = null;
        if (HOSTLOCALABLE) {
            rootUrl += HOST_LOCAL;
        } else
            rootUrl += HOST_REMOTE;
        rootUrl += PORT;
        rootUrl += ROOT_URL;

        return rootUrl;
    }


    public static final String getIndex = getRootUrl() + "getIndex";

}
