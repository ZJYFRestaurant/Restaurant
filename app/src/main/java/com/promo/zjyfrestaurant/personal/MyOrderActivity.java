package com.promo.zjyfrestaurant.personal;

import android.view.View;
import android.widget.ListView;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.application.ZjyfApplication;
import com.promo.zjyfrestaurant.bean.OrderTimeBean;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;

/**
 * 我的点餐。
 */
public class MyOrderActivity extends BaseActivity {


    private ListView myorderlv;
    private MyOderListViewAdapter adapter;

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
        ShowMenuRequset.getData(MyOrderActivity.this, HttpConstant.getFoodList, parma, OrderTimeBean.class, new RequestCallback<OrderTimeBean>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(getApplication(), msg);
            }

            @Override
            public void onSuccess(OrderTimeBean data) {
                data.parseData();
                adapter.notifyDataSetChanged(data.getOrderDatas());
            }
        });
    }

    private void initVeiw(View containerView) {
        setTitle(getResources().getString(R.string.personal_order));
        getData();

        myorderlv = (ListView) containerView.findViewById(R.id.myorder_lv);
        adapter = new MyOderListViewAdapter(getApplicationContext());
        myorderlv.setAdapter(adapter);
    }

}
