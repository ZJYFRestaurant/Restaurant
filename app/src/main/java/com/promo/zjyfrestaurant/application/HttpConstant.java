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

    private static final String HOST_LOCAL = "http://fish.ebingoo.com";

    private static final String HOST_REMOTE = "";

    private static final String PORT = "";

    private static final String ROOT_URL = "/index.php?s=/Home/Api/";

    /**
     * 获取基本连接
     *
     * @return
     */
    private static String getRootUrl() {
        StringBuilder rootUrl = new StringBuilder();
        if (HOSTLOCALABLE) {
            rootUrl.append(HOST_LOCAL);
        } else
            rootUrl.append(HOST_REMOTE);
        rootUrl.append(PORT);
        rootUrl.append(ROOT_URL);

        return rootUrl.toString();
    }

    /**
     * 首页 *
     */
    public static final String getIndex = getRootUrl() + "getIndex";
    /**
     * 菜单详情。
     */
    public static final String getProductDetail = getRootUrl() + "getProductDetail";
    /**
     * 获取热门推荐产品列表
     */
    public static final String getHotProductList = getRootUrl() + "getHotProductList";
    /**
     * 优惠活动。
     */
    public static final String getActivities = getRootUrl() + "getActivities";

    public static final String getPhotoList = getRootUrl() + "getPhotoList";

    public static final String getProductCategory = getRootUrl() + "getProductCategory";

    public static final String getProductList = getRootUrl() + "getProductList";

    public static final String postOrder = getRootUrl() + "postOrder";

    public static final String login = getRootUrl() + "login";
    /**
     * 获得用户id.*
     */
    public static final String getId = getRootUrl() + "getId";

    /**
     * 获得我的订单。
     */
    public static final String getOrderList = getRootUrl() + "getOrderList";
    /**
     * 我的点餐。
     */
    public static final String getFoodList = getRootUrl() + "getFoodList";
    /**
     * 获取地理信息。
     */
    public static final String getAddressList = getRootUrl() + "getAddressList";

    public static final String addAddress = getRootUrl() + "addAddress";

}
