package com.promo.zjyfrestaurant.personal;

import android.view.View;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.application.ZjyfApplication;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;

/**
 * 我的点餐。
 */
public class MyOrderActivity extends BaseActivity {


    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_my_order, null);
        initVeiw(containerView);
        return containerView;
    }

    @Override
    protected void getData() {

        ZJYFRequestParmater parma = new ZJYFRequestParmater(getApplicationContext());
        parma.put("uid", ((ZjyfApplication) getApplicationContext()).getUid());
        ShowMenuRequset.getData(MyOrderActivity.this, HttpConstant.getFoodList, parma, String.class, new RequestCallback<String>() {
            @Override
            public void onfailed(String msg) {

            }

            @Override
            public void onSuccess(String data) {

            }
        });
    }

    private void initVeiw(View containerView)

    {

        setTitle(getResources().getString(R.string.personal_order));

        getData();
    }

}
