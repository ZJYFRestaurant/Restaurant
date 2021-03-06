package com.promote.zjyfrestaurant.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jch.lib.util.DialogUtil;
import com.jch.lib.util.DisplayUtil;
import com.jch.lib.util.FileUtil;
import com.jch.lib.util.ImageManager;
import com.promote.zjyfrestaurant.BaseActivity;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.application.Constant;
import com.promote.zjyfrestaurant.application.HttpConstant;
import com.promote.zjyfrestaurant.bean.ProDetailBean;
import com.promote.zjyfrestaurant.impl.RequestCallback;
import com.promote.zjyfrestaurant.impl.ShowMenuRequset;
import com.promote.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promote.zjyfrestaurant.shoppingcart.ShoppingCartView;
import com.promote.zjyfrestaurant.util.ContextUtil;
import com.promote.zjyfrestaurant.view.AddDealPicker;

/**
 * 菜品详情。
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

    @Override
    protected void onResume() {
        super.onResume();
        if (proDetailBean != null)
            initData();

    }

    public void initView(View containerView) {

        setTitle(getResources().getString(R.string.menu_detail));
        addShoppingCart();

        menudetailimg = (ImageView) containerView.findViewById(R.id.menu_detail_img);

        int except = (int) (getResources().getDimension(R.dimen.common_pad) * 2 + getResources().getDimension(R.dimen.home_item_pad) * 2);
        DisplayUtil.resizeViewByScreenWidth(menudetailimg, Constant.RECM_IMG_POINT.x, Constant.RECM_IMG_POINT.y, except, this);
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
        ImageManager.load(proDetailBean.getCover(), menudetailimg, ContextUtil.getRectangleImgOptions(), 10);
        menudetailnametv.setText(proDetailBean.getName() == null ? "" : proDetailBean.getName());
        menudetailstar.setStartNum(proDetailBean.getStar());
        menudetailpricetv.setText(String.valueOf(proDetailBean.getNew_price()));
        menudetailpromttv.setText(proDetailBean.getDescription() == null ? "" : proDetailBean.getDescription());
        menudetailpicker.setDishBean(proDetailBean);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.share_imgBtn) {
            menudetailimg.setDrawingCacheEnabled(true);
            Intent shareIntent = FileUtil.share(proDetailBean.getCover(), getResources().getString(R.string.share_addr), null);
            startActivity(Intent.createChooser(shareIntent, "选择分享"));
        }

    }
}
