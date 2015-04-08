package com.promote.zjyfrestaurant.home.recommendPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.jch.lib.util.DialogUtil;
import com.promote.zjyfrestaurant.BaseActivity;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.application.HttpConstant;
import com.promote.zjyfrestaurant.bean.PrivilegeActionBean;
import com.promote.zjyfrestaurant.impl.RequestCallback;
import com.promote.zjyfrestaurant.impl.ShowMenuRequset;
import com.promote.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promote.zjyfrestaurant.shoppingcart.ShoppingCartView;

import java.util.ArrayList;

/**
 * 优惠活动。
 */
public class PrivilegeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ArrayList<PrivilegeActionBean> privileges = new ArrayList<>();
    private ListView privilegelv;
    private PrivilegAdapter adapter = null;

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_privilege, null);
        initView(containerView);
        return containerView;
    }

    public void initView(View containerView) {

        setTitle(getResources().getString(R.string.privilage));
        addShoppingCart();

        privilegelv = (ListView) containerView.findViewById(R.id.privilege_lv);
        adapter = new PrivilegAdapter(this);
        privilegelv.setAdapter(adapter);

        privilegelv.setOnItemClickListener(this);

        getData();

    }

    @Override
    protected void getData() {

        String urlStr = HttpConstant.getActivities;
        ZJYFRequestParmater parma = new ZJYFRequestParmater(this);

        ShowMenuRequset.getListData(this, urlStr, parma, PrivilegeActionBean.class, new RequestCallback<ArrayList<PrivilegeActionBean>>() {
            @Override
            public void onfailed(String msg) {
                DialogUtil.toastMsg(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(ArrayList<PrivilegeActionBean> data) {
                if (data != null) {
                    privileges.clear();
                    privileges.addAll(data);
                    adapter.notifyDataSetChanged(data);
                }
            }
        });
    }

    private void addShoppingCart() {

        ShoppingCartView shoppingCartView = new ShoppingCartView(getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addRightItem(shoppingCartView);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PrivilegeDetailActivity.class);
        int pro_id = privileges.get(position).getId();

        Bundle bundle = new Bundle();
        bundle.putString(PrivilegeDetailActivity.DETAIL_ID, String.valueOf(pro_id));
        bundle.putParcelable(PrivilegeDetailActivity.DETAIL_KEY, privileges.get(position));

        intent.putExtras(bundle);
        transNextPage(intent, true);
    }


}
