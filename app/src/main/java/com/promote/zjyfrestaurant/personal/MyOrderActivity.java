package com.promote.zjyfrestaurant.personal;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.promote.zjyfrestaurant.BaseActivity;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.application.HttpConstant;
import com.promote.zjyfrestaurant.application.ZjyfApplication;
import com.promote.zjyfrestaurant.bean.OrderTimeBean;
import com.promote.zjyfrestaurant.impl.RequestCallback;
import com.promote.zjyfrestaurant.impl.ShowMenuRequset;
import com.promote.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promote.zjyfrestaurant.util.ContextUtil;

/**
 * 我的点餐。
 */
public class MyOrderActivity extends BaseActivity {


    private ListView myorderlv;
    private MyOderListViewAdapter adapter;
    private LinearLayout noOrderLl;

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
                myorderlv.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(OrderTimeBean data) {
                if (data != null && data.getTime() != null && data.getTime().size() != 0) {
                    adapter.notifyDataSetChanged(data);
                    noOrderLl.setVisibility(View.GONE);
                } else {
                    myorderlv.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initVeiw(View containerView) {
        setTitle(getResources().getString(R.string.personal_order));
        getData();

        myorderlv = (ListView) containerView.findViewById(R.id.myorder_lv);
        noOrderLl = (LinearLayout) containerView.findViewById(R.id.myorder_no_msg_ll);
        adapter = new MyOderListViewAdapter(getApplicationContext());
        myorderlv.setAdapter(adapter);
    }


}
