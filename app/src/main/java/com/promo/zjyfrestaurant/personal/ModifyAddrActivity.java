package com.promo.zjyfrestaurant.personal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.AddressBean;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;

/**
 * 编辑地址.
 */
public class ModifyAddrActivity extends BaseActivity {

    private EditText mNameEt = null;
    private EditText mPhoneEt = null;
    private EditText mAddrEt = null;

    static final String MODIFY_ADDR_KEY = "modify_add";
    /**
     * 默认地址。
     */
    private AddressBean address = null;

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_modify_addr_layout, null);

        init(containerView);
        return containerView;
    }

    @Override
    protected void getData() {

        ZJYFRequestParmater parma = new ZJYFRequestParmater(getApplicationContext());
        if (address != null) {
            parma.put("content", address.getContent());
            parma.put("id", address.getId());
            parma.put("tel", address.getTel());
            parma.put("contact", address.getContact());
        }

        ShowMenuRequset.getData(ModifyAddrActivity.this, HttpConstant.updateAddress, parma, String.class, new RequestCallback<String>() {

            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(String data) {
                ContextUtil.toast(getApplicationContext(), data);
                setResult(RESULT_OK, new Intent().putExtra(MODIFY_ADDR_KEY, address));
                ModifyAddrActivity.this.finish();
            }
        });


    }

    private void init(View containerView) {
        setTitle(getResources().getString(R.string.modify_addr));
        addRightBtn();

        mNameEt = (EditText) containerView.findViewById(R.id.modify_addr_name);
        mPhoneEt = (EditText) containerView.findViewById(R.id.modify_addr_phone);
        mAddrEt = (EditText) containerView.findViewById(R.id.modify_addr);

        address = getIntent().getParcelableExtra(MODIFY_ADDR_KEY);
        if (address != null) {
            mNameEt.setText(address.getContact());
            mPhoneEt.setText(address.getTel());
            mAddrEt.setText(address.getContent());
        }

    }

    private void addRightBtn() {

        Button okBtn = (Button) View.inflate(getApplicationContext(), R.layout.add_addr_ok, null);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //更新修改。
                assert address == null;
                address.setContent(mAddrEt.getText().toString().trim());
                address.setContact(mNameEt.getText().toString().trim());
                address.setTel(mPhoneEt.getText().toString().trim());

                getData();
            }
        });
        addRightItem(okBtn);

    }
}
