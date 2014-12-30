package com.promo.zjyfrestaurant.shoppingcart;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.book.bookActivity.BookActivity;

public class ShoppingCartActivity extends BaseActivity implements CartOrderChangeListener, CompoundButton.OnCheckedChangeListener {


    private View cartbartopline;
    private Button cartbarnextbtn;
    private TextView carttotalparttv;
    private TextView carttotalpricetv;
    private RelativeLayout cartbtmbar;
    private ListView cartlv;
    private CheckBox selectAllCb;

    private ShoppingCartAdapter adapter;

    @Override
    protected View initContentView() {
        View containerView = View.inflate(getApplicationContext(), R.layout.activity_shopping_cart, null);

        initView(containerView);

        return containerView;
    }

    @Override
    protected void getData() {

    }

    /**
     * 初始化数据。
     *
     * @param containerView
     */
    private void initView(View containerView) {

        setTitle(getResources().getString(R.string.shopping_cart));
        addRightCart();     //添加右侧购物车。

        ShoppingCart.newInstance().setOrderChangeListener(this);

        cartbartopline = (View) containerView.findViewById(R.id.cartbar_top_line);
        cartbarnextbtn = (Button) containerView.findViewById(R.id.cartbar_next_btn);
        carttotalparttv = (TextView) containerView.findViewById(R.id.cart_total_part_tv);
        carttotalpricetv = (TextView) containerView.findViewById(R.id.cart_total_price_tv);
        cartbtmbar = (RelativeLayout) containerView.findViewById(R.id.cart_btmbar);
        cartlv = (ListView) containerView.findViewById(R.id.cart_lv);
        selectAllCb = (CheckBox) containerView.findViewById(R.id.cart_btmbar_cb);
        selectAllCb.setOnCheckedChangeListener(this);

        adapter = new ShoppingCartAdapter(getApplicationContext());
        cartlv.setAdapter(adapter);

        updateTatalData(ShoppingCart.newInstance());

        cartbarnextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShoppingCartActivity.this, BookActivity.class);
                transNextPage(intent, true);

            }
        });
    }


    @Override
    public void onOrderChange(ShoppingCart shoppingCart) {
        updateTatalData(shoppingCart);
    }

    /**
     * 跟新订购所有数据。
     *
     * @param shoppingCart
     */
    private void updateTatalData(ShoppingCart shoppingCart) {
        carttotalparttv.setText(String.valueOf(shoppingCart.getOrderNum()));
        carttotalpricetv.setText(String.valueOf(shoppingCart.getTotalOrderPrice()));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ShoppingCart shoppingCart = ShoppingCart.newInstance();
        shoppingCart.orderAllDish(isChecked);
        adapter.notifyDataSetChanged();
    }
}
