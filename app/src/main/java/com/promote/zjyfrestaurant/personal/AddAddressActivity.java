package com.promote.zjyfrestaurant.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jch.lib.util.DialogUtil;
import com.jch.lib.util.TextUtil;
import com.jch.lib.util.VaildUtil;
import com.promote.zjyfrestaurant.BaseActivity;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.application.HttpConstant;
import com.promote.zjyfrestaurant.application.ZjyfApplication;
import com.promote.zjyfrestaurant.bean.AddressBean;
import com.promote.zjyfrestaurant.impl.RequestCallback;
import com.promote.zjyfrestaurant.impl.ShowMenuRequset;
import com.promote.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promote.zjyfrestaurant.util.ContextUtil;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    private EditText mNameEt = null;
    private EditText mPhoneEt = null;
    private EditText mAddrEt = null;
    private AddressBean address = new AddressBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 地址信息。
     */


    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_add_address, null);

        init(containerView);

        return containerView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void getData() {

        ZJYFRequestParmater parma = new ZJYFRequestParmater(getApplicationContext());
        parma.put("uid", address.getUid());
        parma.put("content", address.getContent());
        parma.put("contact", address.getContact());
        parma.put("tel", address.getTel());

        ShowMenuRequset.getData(AddAddressActivity.this, HttpConstant.addAddress, parma, Integer.class, new RequestCallback<Integer>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(Integer data) {
                address.setId(data);
                Intent intent = new Intent();
                intent.putExtra(AddressActivity.ADDINFO_KEY, address);
                setResult(Activity.RESULT_OK, intent);
                AddAddressActivity.this.finish();
            }
        });
    }

    private void init(View containerView) {
        addOkBtn();
        setTitle(getResources().getString(R.string.add_address));

        mNameEt = (EditText) containerView.findViewById(R.id.add__addr_name_et);
        mPhoneEt = (EditText) containerView.findViewById(R.id.add_addr_phone_et);
        mAddrEt = (EditText) containerView.findViewById(R.id.add_addr_et);

    }

    private void addOkBtn() {

        Button okBtn = (Button) View.inflate(getApplicationContext(), R.layout.add_addr_ok, null);
        okBtn.setOnClickListener(this);

        addRightItem(okBtn);
    }

    /**
     * 检查地址信息。
     *
     * @return
     */
    private boolean checkAddrInfo() {
        boolean checkResult = true;
        String nameStr = mNameEt.getText().toString().trim();
        String phoneStr = mPhoneEt.getText().toString().trim();
        String phoneStrR = VaildUtil.validPhone(phoneStr).trim();
        String addrStr = mAddrEt.getText().toString();
        if (TextUtil.stringIsNull(nameStr)) {
            DialogUtil.toastMsg(getApplicationContext(), getResources().getString(R.string.add_name_warning));
            checkResult = false;
        } else if (!phoneStrR.equals("")) {
            DialogUtil.toastMsg(getApplicationContext(), phoneStrR);
            checkResult = false;
        } else if (TextUtil.stringIsNull(addrStr)) {
            DialogUtil.toastMsg(getApplicationContext(), getResources().getString(R.string.add_addr_warning));
            checkResult = false;
        }
        address.setContact(nameStr);
        address.setTel(phoneStr);
        address.setContent(addrStr);
        address.setUid(((ZjyfApplication) getApplicationContext()).getUid());

        return checkResult;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_addr_ok_btn: {

                if (checkAddrInfo()) {
                    getData();
                }

                break;
            }
            default: {

            }
        }

    }
}
