package com.promo.zjyfrestaurant.personal;

import android.view.View;
import android.widget.Button;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;

public class MyBookActivity extends BaseActivity {

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_my_book, null);

        init(containerView);

        return containerView;
    }


    private void init(View containerView) {

        setTitle(getResources().getString(R.string.personal_book));

    }

    private void addRightBtn() {

        Button addBtn = new Button(getApplicationContext());


    }


}
