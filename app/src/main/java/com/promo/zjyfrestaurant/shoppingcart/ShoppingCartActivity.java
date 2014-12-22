package com.promo.zjyfrestaurant.shoppingcart;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;

public class ShoppingCartActivity extends BaseActivity {


    @Override
    protected View initContentView() {
        View containerView = View.inflate(getApplicationContext(), R.layout.activity_shopping_cart, null);

        initView(containerView);

        return containerView;
    }

    private void initView(View containerView) {

        setTitle(getResources().getString(R.string.shopping_cart));
        addShoppingCart();
    }

    private void addShoppingCart() {

        ShoppingCartView shoppingCartView = new ShoppingCartView(getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addRightItem(shoppingCartView);

    }
}
