package com.promo.zjyfrestaurant.home.recommendPager;

import android.view.View;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;

public class MienActivity extends BaseActivity {


    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_mien, null);

        initView(containerView);

        return containerView;
    }

    private void initView(View containerView) {

        setTitle(getResources().getString(R.string.menu_title));
//        addShoppingCart();
    }

//    private void addShoppingCart() {
//
//        ShoppingCartView shoppingCartView = new ShoppingCartView(getApplicationContext());
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        addRightItem(shoppingCartView);
//
//    }
}
