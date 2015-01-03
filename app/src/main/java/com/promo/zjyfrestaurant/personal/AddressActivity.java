package com.promo.zjyfrestaurant.personal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.application.ZjyfApplication;
import com.promo.zjyfrestaurant.bean.AddressBean;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;
import com.promo.zjyfrestaurant.util.LogCat;

import java.util.ArrayList;

/**
 * 地址管理。
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener {

    static final int ADDCODE = 0;
    static final String ADDINFO_KEY = "addr";

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_address, null);

        init(containerView);
        return containerView;
    }

    @Override
    protected void getData() {

        ZJYFRequestParmater parma = new ZJYFRequestParmater(getApplicationContext());
        parma.put("uid", ((ZjyfApplication) getApplicationContext()).getUid());

        ShowMenuRequset.getListData(AddressActivity.this, HttpConstant.getAddressList, parma, AddressBean.class, new RequestCallback<ArrayList<AddressBean>>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(AddressActivity.this, msg);
            }

            @Override
            public void onSuccess(ArrayList<AddressBean> data) {


            }
        });

    }

    private void init(View containerView) {

        setTitle(getResources().getString(R.string.personal_address));
        addTittleRightBtn();

        getData();
    }

    private void addTittleRightBtn() {

        Button addBtn = (Button) View.inflate(getApplicationContext(), R.layout.add_btn_layout, null);
        addBtn.setOnClickListener(this);
        addRightItem(addBtn);

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_btn_layout: {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, ADDCODE);
                break;
            }
            default: {

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (RESULT_OK) {

            case RESULT_OK: {
                break;
            }

        }

        if (requestCode == ADDCODE) {
//            AddressBean addressBean = data.getParcelableExtra(ADDINFO_KEY);
            LogCat.i("dafds");
        }
    }
}
