package com.promo.zjyfrestaurant.personal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jch.lib.util.DialogUtil;
import com.jch.lib.util.TextUtil;
import com.jch.lib.util.VaildUtil;
import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.bean.AddressBean;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    private EditText mNameEt = null;
    private EditText mPhoneEt = null;
    private EditText mAddrEt = null;
    /**
     * 地址信息。
     */
    private AddressBean addressBean = null;

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_add_address, null);

        init(containerView);

        return containerView;
    }

    @Override
    protected void getData() {

    }

    private void init(View containerView) {
        addOkBtn();

        mNameEt = (EditText) containerView.findViewById(R.id.add__addr_name_et);
        mPhoneEt = (EditText) containerView.findViewById(R.id.add_addr_phone_et);
        mAddrEt = (EditText) containerView.findViewById(R.id.add_addr_et);

        setTitle(getResources().getString(R.string.add_address));

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
    private AddressBean checkAddrInfo() {

        String nameStr = mNameEt.getText().toString().trim();
        String phoneStr = mPhoneEt.getText().toString().trim();
        String phoneStrR = VaildUtil.validPhone(phoneStr).trim();
        String addrStr = mAddrEt.getText().toString();
        if (TextUtil.stringIsNull(nameStr)) {
            DialogUtil.toastMsg(getApplicationContext(), getResources().getString(R.string.add_name_warning));
        } else if (!phoneStrR.equals("")) {
            DialogUtil.toastMsg(getApplicationContext(), phoneStrR);
        } else if (TextUtil.stringIsNull(addrStr)) {
            DialogUtil.toastMsg(getApplicationContext(), getResources().getString(R.string.add_addr_warning));
        } else {
            addressBean = new AddressBean();
            addressBean.setName(nameStr);
            addressBean.setPhone(phoneStr);
            addressBean.setAddr(addrStr);
            return addressBean;
        }

        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_addr_ok_btn: {

                AddressBean address = checkAddrInfo();
                if (address != null) {
                    Intent intent = new Intent();
                    intent.putExtra(AddressActivity.ADDINFO_KEY, addressBean);
                    //TODO 数据网络了连接
                    setResult(AddressActivity.ADDCODE, intent);
                    this.finish();
                }

                break;
            }
            default: {

            }
        }

    }
}
