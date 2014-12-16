package com.promo.zjyfrestaurant.personal;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;

/**
 * Created by ACER on 2014/12/16.
 */
public class ModifyAddrActivity extends BaseActivity {

    private EditText mNameEt = null;
    private EditText mPhoneEt = null;
    private EditText mAddrEt = null;

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_modify_addr_layout, null);

        init(containerView);
        return null;
    }

    private void init(View containerView) {

        mNameEt = (EditText) containerView.findViewById(R.id.modify_addr_name);
        mPhoneEt = (EditText) containerView.findViewById(R.id.modify_addr_phone);
        mAddrEt = (EditText) containerView.findViewById(R.id.modify_addr);
        setTitle(getResources().getString(R.string.modify_addr));
        addRightBtn();


    }

    private void addRightBtn() {

        Button okBtn = (Button) View.inflate(getApplicationContext(), R.layout.add_addr_ok, null);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        addRightItem(okBtn);

    }
}
