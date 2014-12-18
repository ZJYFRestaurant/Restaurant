package com.promo.zjyfrestaurant.personal;

import android.view.View;
import android.widget.Button;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;

public class FeedBackActivity extends BaseActivity {


    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_feed_back, null);
        init(containerView);

        return containerView;
    }

    private void init(View containerView) {
        setTitle(getResources().getString(R.string.personal_feedback));
        addOkBtn();

    }

    private void addOkBtn() {

        Button okBtn = (Button) View.inflate(getApplicationContext(), R.layout.add_addr_ok, null);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addRightItem(okBtn);
    }
}
