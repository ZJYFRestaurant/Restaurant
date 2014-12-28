package com.promo.zjyfrestaurant.personal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.bean.AddressBean;
import com.promo.zjyfrestaurant.util.LogCat;

public class AddressActivity extends BaseActivity implements View.OnClickListener {

    static final int ADDCODE = 0x55555;
    static final String ADDINFO_KEY = "addr";

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_address, null);

        init(containerView);
        return containerView;
    }

    @Override
    protected void getData() {

    }

    private void init(View containerView) {

        setTitle(getResources().getString(R.string.personal_address));
        addTittleRightBtn();
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
        if (resultCode == ADDCODE) {
            AddressBean addressBean = data.getParcelableExtra(ADDINFO_KEY);
            LogCat.i("dafds");
        }

    }
}
