package com.promo.zjyfrestaurant.book;

import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.jch.lib.view.ScrollGridView;
import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.OrderDetailBean;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;

/**
 * 预约详情。
 */
public class BookDetailActivity extends BaseActivity {

    public static final String ORDERID_KEY = "orderId";
    /**
     * 订单。 *
     */
    private OrderDetailBean orderBean = null;

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
    private TableRow addrTr;
    private TextView addrTitleTv;
    private TextView sendAddrTv;

    private int orderId;


    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_book_detail, null);

        initView(containerView);

        orderId = getIntent().getIntExtra(ORDERID_KEY, 0);

        getData();
        return containerView;
    }

    @Override
    protected void getData() {

        if (orderId == 0)
            return;

        ZJYFRequestParmater parma = new ZJYFRequestParmater(getApplicationContext());
        parma.put("order_id", orderId);
        ShowMenuRequset.getData(BookDetailActivity.this, HttpConstant.getOrderDetail, parma, OrderDetailBean.class, new RequestCallback<OrderDetailBean>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(OrderDetailBean data) {
                if (data != null) {
                    orderBean = data;
                    initData();
                }

            }
        });
    }

    private void initView(View containerView) {

        setTitle(getResources().getString(R.string.book_detail));

        bookdetailmoney = (TextView) containerView.findViewById(R.id.book_detail_money);
        bookdetailcontact = (TextView) containerView.findViewById(R.id.book_detail_contact);
        bookdetailphonetr = (TableRow) containerView.findViewById(R.id.book_detail_phone_tr);
        bookdetailphone = (TextView) containerView.findViewById(R.id.book_detail_phone);
        bookdetailnumtv = (TextView) containerView.findViewById(R.id.book_detail_num_tv);
        bookdetailnumtr = (TableRow) containerView.findViewById(R.id.book_detail_num_tr);
        bookdetailtimetitle = (TextView) containerView.findViewById(R.id.book_detail_time_title);
        bookdetailtimetv = (TextView) containerView.findViewById(R.id.book_detail_time_tv);
        bookdetailothertv = (TextView) containerView.findViewById(R.id.book_detail_other_tv);
        bookdetailgv = (ScrollGridView) containerView.findViewById(R.id.book_detail_gv);
        addrTr = (TableRow) containerView.findViewById(R.id.book_detail_addr_tr);
        addrTitleTv = (TextView) containerView.findViewById(R.id.book_detail_addr_title);
        sendAddrTv = (TextView) containerView.findViewById(R.id.book_detail_send_addr_tv);


    }

    private void initData() {

        if (orderBean != null) {
            bookdetailmoney.setText(String.valueOf(orderBean.getPrice()));
            bookdetailcontact.setText(orderBean.getContact());
            bookdetailphone.setText(orderBean.getTel());

            switch (orderBean.getOrderType()) {
                case BRING: {
                    initBringData();
                    break;
                }
                case ARRIVE: {
                    initArriveData();
                    break;
                }
                case SEND: {
                    initSendData();
                    break;
                }
            }
            String otherStr = orderBean.getRemark();
            if (otherStr != null && !otherStr.equals("")) {
                bookdetailothertv.setText(bookdetailothertv.getText().toString() + otherStr);  //其他
            } else
                bookdetailothertv.setVisibility(View.GONE);
            confirmAdapter = new ComfirmBookAdapter(getApplicationContext(), orderBean.getProducts());
            bookdetailgv.setAdapter(confirmAdapter);
        }
    }

    /**
     * 取餐。
     */

    private void initBringData() {

        bookdetailnumtv.setText(String.valueOf(orderBean.getPeople_num()));
        bookdetailtimetv.setText(orderBean.getUse_time());
        bookdetailnumtr.setVisibility(View.GONE);
        bookdetailtimetitle.setText(getString(R.string.get_dish_time));
        bookdetailtimetv.setText(orderBean.getUse_time());
        addrTr.setVisibility(View.GONE);
    }

    /**
     * 亲临渔府
     */
    private void initArriveData() {

        bookdetailnumtv.setText(String.valueOf(orderBean.getPeople_num()));
        bookdetailtimetv.setText(orderBean.getUse_time());
        addrTr.setVisibility(View.GONE);
    }

    /**
     * 送餐
     */
    private void initSendData() {

        bookdetailnumtr.setVisibility(View.GONE);
        bookdetailtimetitle.setText(R.string.send_time_);
        bookdetailtimetv.setText(orderBean.getUse_time().trim());
        sendAddrTv.setText(orderBean.getAddress().trim() == null ? "" : orderBean.getAddress().trim());
    }


}
