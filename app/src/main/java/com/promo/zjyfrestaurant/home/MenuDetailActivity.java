package com.promo.zjyfrestaurant.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCartView;

/**
 * 菜单详情。
 */
public class MenuDetailActivity extends BaseActivity {


    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_menu_detail, null);

        initView(containerView);
        return containerView;
    }

    public void initView(View containerView) {

        setTitle(getResources().getString(R.string.menu_detail));
        addShoppingCart();

    }

    private void addShoppingCart() {

        ShoppingCartView shoppingCartView = new ShoppingCartView(getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        shoppingCartView.setLayoutParams(params);
        addRightItem(shoppingCartView);

    }
}
