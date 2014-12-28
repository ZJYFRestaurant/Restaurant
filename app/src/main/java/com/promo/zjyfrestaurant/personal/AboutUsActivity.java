package com.promo.zjyfrestaurant.personal;

import android.view.View;
import android.widget.ImageView;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;

public class AboutUsActivity extends BaseActivity {

    private ImageView mHeadIv = null;


    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_about_us, null);

        init(containerView);

        return containerView;
    }

    @Override
    protected void getData() {

    }

    /**
     * 初始化view。
     *
     * @param containerView
     */
    private void init(View containerView) {

        setTitle(getResources().getString(R.string.personal_about));
        mHeadIv = (ImageView) containerView.findViewById(R.id.head_img);

    }


}
