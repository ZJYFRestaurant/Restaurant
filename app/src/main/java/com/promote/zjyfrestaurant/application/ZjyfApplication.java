package com.promote.zjyfrestaurant.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.jch.lib.util.SharedPreferenceUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.promote.zjyfrestaurant.exception.CrashHandler;
import com.promote.zjyfrestaurant.impl.RequestCallback;
import com.promote.zjyfrestaurant.impl.ShowMenuRequset;
import com.promote.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promote.zjyfrestaurant.shoppingcart.ShoppingCart;

import java.io.File;

/**
 * Created by ACER on 2014/12/9.
 */
public class ZjyfApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoaderConfiguration();
        SDKInitializer.initialize(getApplicationContext());
        ShoppingCart.newInstance().initData(getApplicationContext());

    }

    /**
     * 配置imgloader.
     */
    public void initImageLoaderConfiguration() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(1 * 1024 * 1024))
                .diskCacheSize(50 * 1024 * 1024)
                .threadPoolSize(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(
                        new UnlimitedDiscCache(new File(Constant.IMG_DIR
                        ))
                )
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);

        getUid();
        CrashHandler.newInstance().init(getApplicationContext());

    }

    /**
     * 获取Uid.
     *
     * @return
     */
    public int getUid() {

        int uid = SharedPreferenceUtil.getInt(getApplicationContext(), Constant.UID);
        if (uid != 0) {
            return uid;
        }
        ZJYFRequestParmater parmater = new ZJYFRequestParmater(getApplicationContext());
        String urlStr = HttpConstant.getId;

        ShowMenuRequset.getData(getApplicationContext(), urlStr, parmater, Integer.class, new RequestCallback<Integer>() {
            @Override
            public void onfailed(String msg) {
            }

            @Override
            public void onSuccess(Integer data) {
                SharedPreferenceUtil.saveInt(getApplicationContext(), Constant.UID, data);        //将uid保存到本地。
            }
        });
        return 0;
    }

    /**
     * 是否显示引导页。
     *
     * @return
     */
    public boolean shouldGuid() {

        return SharedPreferenceUtil.getBoolean(getApplicationContext(), Constant.GUIDE, true);
    }

    /**
     * 已经显示过引导页。
     */
    public void guidOver() {

        SharedPreferenceUtil.saveBoolean(getApplicationContext(), Constant.GUIDE, false);

    }
}
