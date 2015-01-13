package com.promo.zjyfrestaurant.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.jch.lib.view.ScrollGridView;
import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.application.ZjyfApplication;
import com.promo.zjyfrestaurant.bean.OrderBean;
import com.promo.zjyfrestaurant.bean.OrderType;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.personal.MyBookActivity;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCart;
import com.promo.zjyfrestaurant.util.ContextUtil;

/**
 * 确认订单
 * <p/>
 * Created by ACER on 2014/12/24.
 */
public class ConfirmBookActivity extends BaseActivity {

    public static final String ORDER_KEY = "orders";
    /**
     * 订单。 *
     */
    private OrderBean orderBean = null;

    private ComfirmBookAdapter confirmAdapter;
    private TextView bookdetailmoney;
    private TextView bookdetailcontact;
    private TextView bookdetailphone;
    private TableRow bookdetailphonetr;
    private TextView bookdetailnumtv;
    private TableRow bookdetailnumtr;
    private TextView bookdetailtimetitle;
    private TextView bookdetailtimetv;
    private TextView bookdetailothertv;
    private ScrollGridView bookdetailgv;
    private Button subBtn;


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

        Bundle bundle = getIntent().getExtras();
        orderBean = (OrderBean) bundle.getParcelable(ORDER_KEY);

        setTitle(getResources().getString(R.string.comfirm_book));

        bookdetailmoney = (TextView) findViewById(R.id.book_detail_money);
        bookdetailcontact = (TextView) findViewById(R.id.book_detail_contact);
        bookdetailphone = (TextView) findViewById(R.id.book_detail_phone);
        bookdetailphonetr = (TableRow) findViewById(R.id.book_detail_phone_tr);
        bookdetailnumtv = (TextView) findViewById(R.id.book_detail_num_tv);
        bookdetailnumtr = (TableRow) findViewById(R.id.book_detail_num_tr);
        bookdetailtimetitle = (TextView) findViewById(R.id.book_detail_time_title);
        bookdetailtimetv = (TextView) findViewById(R.id.book_detail_time_tv);
        bookdetailothertv = (TextView) findViewById(R.id.book_detail_other_tv);
        bookdetailgv = (ScrollGridView) findViewById(R.id.book_detail_gv);
        subBtn = (Button) findViewById(R.id.confirm_sub_btn);

        if (orderBean != null) {
            bookdetailmoney.setText(String.valueOf(orderBean.getPrice()));
            bookdetailcontact.setText(orderBean.getContact());
            bookdetailnumtv.setText(String.valueOf(orderBean.getPeople_num()));
            bookdetailtimetv.setText(orderBean.getUse_time());
            confirmAdapter = new ComfirmBookAdapter(getApplicationContext(), orderBean.getProducts());
            bookdetailgv.setAdapter(confirmAdapter);
        }

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

    }

    /**
     * 下单。
     */
    private void submit() {
        ZJYFRequestParmater parma = new ZJYFRequestParmater(getApplicationContext());
        parma.put("uid", ((ZjyfApplication) getApplicationContext()).getUid());
        parma.put("type", orderBean.getType().getValue());
        if (orderBean.getType() == OrderType.ARRIVE) {  //到店吃饭。

            parma.put("people_num", orderBean.getPeople_num());

        } else if (orderBean.getType() == OrderType.BRING) {        //外带

        } else {        //送餐
            parma.put("address_id", orderBean.getAddress_id());
            parma.put("address_content", orderBean.getAddress_content());
        }
        parma.put("use_time", orderBean.getUse_UnitTime());
        parma.put("contact", orderBean.getContact());
        parma.put("tel", orderBean.getTel());
        parma.put("remark", orderBean.getRemark());
        parma.put("price", orderBean.getPrice());
        parma.put("products", orderBean.getProductStr());


        ShowMenuRequset.getData(ConfirmBookActivity.this, HttpConstant.postOrder, parma, Integer.class, new RequestCallback<Integer>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(Integer data) {

                ShoppingCart.newInstance().clearCart(); //清空菜单。

                ContextUtil.toast(getApplicationContext(), "下单成功，订单号为：" + data);
                Intent intent = new Intent(ConfirmBookActivity.this, MyBookActivity.class);
                transNextPage(intent, true);
                ConfirmBookActivity.this.finish();
            }
        });

    }

}
