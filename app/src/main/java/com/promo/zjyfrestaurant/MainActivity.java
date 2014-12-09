package com.promo.zjyfrestaurant;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {


    private FrameLayout contentfl;
    private RadioButton mainrb;
    private RadioButton findrb;
    private RadioButton publishrb;
    private RadioButton centerrb;
    private RadioGroup mainrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initialize();
    }


    private void initialize() {

        contentfl = (FrameLayout) findViewById(R.id.content_fl);
        mainrb = (RadioButton) findViewById(R.id.main_rb);
        findrb = (RadioButton) findViewById(R.id.find_rb);
        publishrb = (RadioButton) findViewById(R.id.publish_rb);
        centerrb = (RadioButton) findViewById(R.id.center_rb);
        mainrg = (RadioGroup) findViewById(R.id.main_rg);
        mainrg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {




    }
}
