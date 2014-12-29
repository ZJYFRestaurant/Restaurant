package com.promo.zjyfrestaurant.home.recommendPager;

import android.view.View;

import com.jch.lib.util.DialogUtil;
import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;

import java.util.ArrayList;

/**
 * 渔府风采。
 */
public class MienActivity extends BaseActivity {

    private ArrayList<String> photoes = new ArrayList<String>();

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_mien, null);

        initView(containerView);

        return containerView;
    }

    @Override
    protected void getData() {
        String urlStr = HttpConstant.getPhotoList;
        ZJYFRequestParmater parma = new ZJYFRequestParmater(this);
        ShowMenuRequset.getListData(this, urlStr, parma, String.class, new RequestCallback<ArrayList<String>>() {

            @Override
            public void onfailed(String msg) {
                DialogUtil.toastMsg(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(ArrayList<String> data) {
                photoes.clear();
                photoes.addAll(data);
            }
        });
    }

    private void initView(View containerView) {

        setTitle(getResources().getString(R.string.mien_title));
//        addShoppingCart();

        getData();
    }

}
