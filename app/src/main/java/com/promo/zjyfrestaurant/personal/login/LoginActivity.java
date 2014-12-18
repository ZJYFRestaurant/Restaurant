package com.promo.zjyfrestaurant.personal.login;

import android.view.View;
import android.widget.ImageView;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private ImageView personalheadimg;
    private ImageView taobaologinbtn;
    private ImageView qqloginbtn;
    private ImageView sinaloginbtn;

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_login, null);
        init(containerView);

        return containerView;
    }

    private void init(View containerView) {

        hideTitle();
        personalheadimg = (ImageView) containerView.findViewById(R.id.personal_head_img);
        taobaologinbtn = (ImageView) containerView.findViewById(R.id.taobao_login_btn);
        qqloginbtn = (ImageView) containerView.findViewById(R.id.qq_login_btn);
        sinaloginbtn = (ImageView) containerView.findViewById(R.id.sina_login_btn);

        taobaologinbtn.setOnClickListener(this);
        qqloginbtn.setOnClickListener(this);
        sinaloginbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.taobao_login_btn: {

                break;
            }

            case R.id.qq_login_btn: {

                break;
            }

            case R.id.sina_login_btn: {
                break;
            }
            default: {

            }
        }
    }
}
