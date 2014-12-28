package com.promo.zjyfrestaurant.home.recommendPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.jch.lib.util.DialogUtil;
import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.PrivilegeActionBean;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCartView;

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

        //TODO 活动详情。
    }


}
