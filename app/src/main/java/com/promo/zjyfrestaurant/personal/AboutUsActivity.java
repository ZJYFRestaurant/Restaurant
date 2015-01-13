package com.promo.zjyfrestaurant.personal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.UsBean;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;

public class AboutUsActivity extends BaseActivity {

    private ImageView mHeadIv = null;
    private UsBean usBean;
    private ImageView headimg;
    private TextView usnametv;
    private TextView uspricetv;
    private TextView ustimetv;
    private TextView usaddrtv;
    private TextView usphonetv;

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_about_us, null);

        init(containerView);

        getData();

        return containerView;
    }

    @Override
    protected void getData() {

        ShowMenuRequset.getData(AboutUsActivity.this, HttpConstant.getBaseInfo, new ZJYFRequestParmater(getApplicationContext()), UsBean.class, new RequestCallback<UsBean>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(UsBean data) {
                usBean = data;
                initData();
            }
        });

    }

    /**
     * 初始化view。
     *
     * @param containerView
     */
    private void init(View containerView) {

        setTitle(getResources().getString(R.string.personal_about));
        mHeadIv = (ImageView) containerView.findViewById(R.id.head_img);
        headimg = (ImageView) containerView.findViewById(R.id.head_img);
        usnametv = (TextView) containerView.findViewById(R.id.us_name_tv);
        uspricetv = (TextView) containerView.findViewById(R.id.us_price_tv);
        ustimetv = (TextView) containerView.findViewById(R.id.us_time_tv);
        usaddrtv = (TextView) containerView.findViewById(R.id.us_addr_tv);
        usphonetv = (TextView) containerView.findViewById(R.id.us_phone_tv);
    }

    private void initData() {
        usnametv.setText(usBean.getShop_name());
        uspricetv.setText(usBean.getShop_average_money());
        ustimetv.setText(usBean.getShop_time());
        usaddrtv.setText(usBean.getShop_address());
        usphonetv.setText(usBean.getShop_tel());
    }

}
