package com.promo.zjyfrestaurant.book;

import android.view.View;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;

/**
 * 确认订单
 * <p/>
 * Created by ACER on 2014/12/24.
 */
public class ConfirmBookActivity extends BaseActivity {


    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_confirm_book_layout, null);

        initView(containerView);

        return containerView;
    }

    @Override
    protected void getData() {

    }

    private void initView(View containerView) {

        setTitle(getResources().getString(R.string.comfirm_book));
    }

}
