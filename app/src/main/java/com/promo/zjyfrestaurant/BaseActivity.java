package com.promo.zjyfrestaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.promo.zjyfrestaurant.shoppingcart.ShoppingCartView;

/**
 * activity基类。显示titleBar样式，页面跳转效果.
 * Created by ACER on 2014/12/9.
 */
public abstract class BaseActivity extends Activity {


    private Button backbtn;
    private TextView titletv;
    private FrameLayout contentfl;
    private RelativeLayout title_rl;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        initialize();
    }


    protected abstract View initContentView();

    /**
     * 网络访问。
     */
    protected abstract void getData();


    private void initialize() {

        backbtn = (Button) findViewById(R.id.back_btn);
        titletv = (TextView) findViewById(R.id.title_tv);
        contentfl = (FrameLayout) findViewById(R.id.content_fl);

        title_rl = (RelativeLayout) findViewById(R.id.title_rl);

        View contentView = initContentView();
        if (contentView == null)
            return;
        contentView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentfl.addView(contentView);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }


    /**
     * 隐藏标题栏.
     */
    protected void hideTitle() {
        title_rl.setVisibility(View.GONE);
    }

    /**
     * 设置标题。
     *
     * @param title
     */
    protected void setTitle(String title) {
        if (title != null && !title.equals("")) {
            titletv.setText(title);
        }
    }

    protected void hideBackBtn() {
        backbtn.setVisibility(View.GONE);
    }

    /**
     * 添加title右边的组件.
     *
     * @param rightItem
     */
    protected void addRightItem(View rightItem) {

        if (rightItem != null) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.width = (int) getResources().getDimension(R.dimen.add_btn_width);

            rightItem.setLayoutParams(params);
            title_rl.addView(rightItem);

        }
    }

    protected void addRightCart() {
        addShoppingCart();
    }

    private void addShoppingCart() {

        ShoppingCartView shoppingCartView = new ShoppingCartView(getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addRightItem(shoppingCartView);
    }

    /**
     * @param intent
     */
    protected void transNextPage(Intent intent, boolean backAble) {
        startActivity(intent);
        if (!backAble) {
            finish();
        }
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
}
