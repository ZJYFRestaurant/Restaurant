package com.promo.zjyfrestaurant.home.recommendPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCartView;

public class MenuActivity extends BaseActivity {


    @Override
    protected View initContentView() {
        View containerView = View.inflate(getApplicationContext(), R.layout.activity_menu, null);

        initView(containerView);
        return containerView;
    }

    /**
     * 初始化view。
     *
     * @param containerView
     */
    private void initView(View containerView) {

        setTitle(getResources().getString(R.string.menu_title));
        addShoppingCart();

    }

    private void addShoppingCart() {

        ShoppingCartView shoppingCartView = new ShoppingCartView(getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        shoppingCartView.setLayoutParams(params);
        addRightItem(shoppingCartView);

    }
}
