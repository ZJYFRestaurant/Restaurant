package com.promote.zjyfrestaurant.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.jch.lib.view.ScrollGridView;
import com.promote.zjyfrestaurant.BaseActivity;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.application.HttpConstant;
import com.promote.zjyfrestaurant.application.ZjyfApplication;
import com.promote.zjyfrestaurant.bean.OrderBean;
import com.promote.zjyfrestaurant.bean.OrderType;
import com.promote.zjyfrestaurant.impl.RequestCallback;
import com.promote.zjyfrestaurant.impl.ShowMenuRequset;
import com.promote.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promote.zjyfrestaurant.personal.AddressActivity;
import com.promote.zjyfrestaurant.personal.MyBookActivity;
import com.promote.zjyfrestaurant.shoppingcart.ShoppingCart;
import com.promote.zjyfrestaurant.util.ContextUtil;

/**
 * 确认订单
 * <p/>
 * Created by ACER on 2014/12/24.
 */
public class ConfirmBookActivity extends BaseActivity {

    /**
     * 订单重复。
     */
    protected boolean confirm2Flag = false;

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
    private TableRow otherTr;
    private Button subBtn;
    private TableRow addrTr;
    private TextView addrTitleTv;
    private TextView sendAddrTv;
    private TextView bookDetailTr;


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
        otherTr = (TableRow) containerView.findViewById(R.id.confirm_other_tr);
        bookDetailTr = (TextView) containerView.findViewById(R.id.book_detail_tr);

        subBtn = (Button) containerView.findViewById(R.id.confirm_sub_btn);

        if (orderBean != null) {
            bookdetailmoney.setText(String.valueOf(orderBean.getPrice()));
            bookdetailcontact.setText(orderBean.getContact());
            bookdetailphone.setText(orderBean.getTel());

            switch (orderBean.getType()) {
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
            otherTr.setVisibility(orderBean.getRemark() == null || orderBean.getRemark().equals("") ? View.GONE : View.VISIBLE);
            bookdetailothertv.setText(bookdetailothertv.getText().toString() + orderBean.getRemark());  //其他
            confirmAdapter = new ComfirmBookAdapter(getApplicationContext(), orderBean.getProducts());
            bookdetailgv.setAdapter(confirmAdapter);
            confirmAdapter.notifyDataSetChanged();
        }
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirm2Flag)
                    ContextUtil.toast(getApplicationContext(), getString(R.string.order_tiwce));
                else
                    submit();
            }
        });

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
        bookDetailTr.setVisibility(View.GONE);

    }

    /**
     * 亲临渔府
     */
    private void initArriveData() {

        if (orderBean.getProducts() == null) {
            bookdetailgv.setVisibility(View.GONE);
        }
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
        bookdetailtimetv.setText(orderBean.getUse_time());
        sendAddrTv.setText(orderBean.getAddress_content());
        bookDetailTr.setVisibility(View.GONE);
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
        parma.put("products", orderBean.getProductStr());
        parma.put("price", orderBean.getPrice());

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
                intent.putExtra(MyBookActivity.TO_MYBOOK_KEY, MyBookActivity.FROM_CONFIRM);
                int fromCode = getIntent().getIntExtra(AddressActivity.FROM_ACIVITY_KEY, -1);
                intent.putExtra(AddressActivity.FROM_ACIVITY_KEY, fromCode);

                transNextPage(intent, false);
                confirm2Flag = true;    //已经提交过订单
            }
        });

    }

}
