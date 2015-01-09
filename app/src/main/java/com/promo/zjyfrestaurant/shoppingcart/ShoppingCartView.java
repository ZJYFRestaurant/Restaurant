package com.promo.zjyfrestaurant.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.promo.zjyfrestaurant.R;

/**
 * 购物车。
 */
public class ShoppingCartView extends FrameLayout implements View.OnClickListener, ShoppingCartObserver {

    /**
     * 购物车实例。
     */
    private ShoppingCart shoppingCart = null;
    private View contentView = null;
    private ImageView cartimg;
    private TextView carttv;

    public ShoppingCartView(Context context) {
        super(context);
        init(null, 0);
    }

    public ShoppingCartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ShoppingCartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        contentView = View.inflate(getContext(), R.layout.shoppingcat_view, null);
        cartimg = (ImageView) contentView.findViewById(R.id.cart_img);
        carttv = (TextView) contentView.findViewById(R.id.cart_tv);
        addView(contentView);

        setOnClickListener(this);

        shoppingCart = ShoppingCart.newInstance();
        shoppingCart.addObserver(this);         //购物车数据单例中注册监听者。

        carttv.setText(String.valueOf(shoppingCart.getDishNum()));
    }

    public void addDish(int id) {
        addDish(id, 1);
    }

    public void addDish(int id, int num) {
        carttv.setText(shoppingCart.getDishNum());
        visibleNumTv();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        visibleNumTv();
    }

    private void visibleNumTv() {
        if (shoppingCart.getDishNum() != 0) {
            carttv.setVisibility(VISIBLE);
        } else {
            carttv.setVisibility(GONE);
        }
    }

    public void reduceDish(int id) {
        carttv.setText(shoppingCart.getDishNum());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), ShoppingCartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
//        getContext().overridePendingTransition(R.anim.slide_in_right, R.anim.static_anim);
    }


    @Override
    public void update(ShoppingCartSubject subject, int num) {

        carttv.setText(String.valueOf(num));
        visibleNumTv();
    }
}
