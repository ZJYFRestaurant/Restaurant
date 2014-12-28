package com.promo.zjyfrestaurant.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jch.lib.util.DialogUtil;
import com.jch.lib.util.ImageManager;
import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.ProDetailBean;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCartView;
import com.promo.zjyfrestaurant.util.ContextUtil;
import com.promo.zjyfrestaurant.view.AddDealPicker;

/**
 * 菜单详情。
 */
public class MenuDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String PRO_ID_KEY = "product_id";
    private String pro_id;

    private ProDetailBean proDetailBean;
    private ImageView menudetailimg;
    private AddDealPicker menudetailpicker;
    private TextView menudetailnametv;
    private HomeStartView menudetailstar;
    private TextView menudetailpricetv;
    private TextView menudetailpromttv;
    private ImageButton imgBtn;

    @Override

    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_menu_detail, null);

        initView(containerView);
        return containerView;
    }

    public void initView(View containerView) {

        setTitle(getResources().getString(R.string.menu_detail));
        addShoppingCart();

        menudetailimg = (ImageView) containerView.findViewById(R.id.menu_detail_img);
        menudetailpicker = (AddDealPicker) containerView.findViewById(R.id.menu_detail_picker);
        menudetailnametv = (TextView) containerView.findViewById(R.id.menu_detail_name_tv);
        menudetailstar = (HomeStartView) containerView.findViewById(R.id.menu_detail_star);
        menudetailpricetv = (TextView) containerView.findViewById(R.id.menu_detail_price_tv);
        menudetailpromttv = (TextView) containerView.findViewById(R.id.menu_detail_promt_tv);
        imgBtn = (ImageButton) containerView.findViewById(R.id.share_imgBtn);

        imgBtn.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        pro_id = bundle.getString(PRO_ID_KEY);

        getData();

    }

    /**
     * 添加购物车。
     */
    private void addShoppingCart() {

        ShoppingCartView shoppingCartView = new ShoppingCartView(getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        shoppingCartView.setLayoutParams(params);
        addRightItem(shoppingCartView);
    }

    /**
     * 获取网络数据。
     */
    @Override
    protected void getData() {
        String urlStr = HttpConstant.getProductDetail;
        ZJYFRequestParmater parmater = new ZJYFRequestParmater(getApplicationContext());

        parmater.put(PRO_ID_KEY, pro_id);
        ShowMenuRequset.getData(this, urlStr, parmater, ProDetailBean.class, new RequestCallback<ProDetailBean>() {
            @Override
            public void onfailed(String msg) {
                DialogUtil.toastMsg(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(ProDetailBean data) {
                if (data != null) {
                    proDetailBean = data;
                    initData();
                }

            }
        });

    }

    private void initData() {
        ImageManager.load(proDetailBean.getCover(), menudetailimg, ContextUtil.getRectangleImgOptions());
        menudetailnametv.setText(proDetailBean.getName() == null ? "" : proDetailBean.getName());
        menudetailstar.setStartNum(proDetailBean.getStar());
        menudetailpricetv.setText(proDetailBean.getNew_price());
        menudetailpromttv.setText(proDetailBean.getDescription() == null ? "" : proDetailBean.getDescription());

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.share_imgBtn) {
            Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
            intent.setType("text/plain"); // 分享发送的数据类型
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject)); // 分享的主题
            intent.putExtra(Intent.EXTRA_TEXT, menudetailnametv.getText().toString()); // 分享的内容
            startActivity(Intent.createChooser(intent, "选择分享"));
        }

    }
}
