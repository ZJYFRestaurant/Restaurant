package com.promo.zjyfrestaurant.home.recommendPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCartView;

public class PrivilegeActivity extends BaseActivity {


    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_privilege, null);
        initView(containerView);
        return containerView;
    }

    public void initView(View containerView) {

        setTitle(getResources().getString(R.string.privilage));
        addShoppingCart();

    }

    private void addShoppingCart() {

        ShoppingCartView shoppingCartView = new ShoppingCartView(getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addRightItem(shoppingCartView);

    }


}
