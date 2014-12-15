package com.promo.zjyfrestaurant.personal;

import android.view.View;
import android.widget.Button;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;

public class AddressActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_address, null);

        init(containerView);
        return containerView;
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_btn_layout: {


                break;
            }
            default: {

            }
        }
    }
}
