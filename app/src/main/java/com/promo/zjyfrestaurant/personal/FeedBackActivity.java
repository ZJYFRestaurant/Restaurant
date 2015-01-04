package com.promo.zjyfrestaurant.personal;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.application.ZjyfApplication;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;

/**
 * 意见反馈。
 */
public class FeedBackActivity extends BaseActivity {


    private EditText feedbacket;
    private Button feedbacksmtbtn;

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_feed_back, null);
        init(containerView);

        return containerView;
    }

    @Override
    protected void getData() {

        final ZJYFRequestParmater parma = new ZJYFRequestParmater(getApplicationContext());
        parma.put("uid", ((ZjyfApplication) getApplicationContext()).getUid());
        parma.put("content", feedbacket.getText().toString().trim());
        ShowMenuRequset.getData(FeedBackActivity.this, HttpConstant.addSuggest, parma, String.class, new RequestCallback<String>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(String data) {
                ContextUtil.toast(getApplicationContext(), data);
            }
        });

    }

    private void init(View containerView) {
        setTitle(getResources().getString(R.string.personal_feedback));
        addOkBtn();

        feedbacket = (EditText) containerView.findViewById(R.id.feedback_et);
        feedbacksmtbtn = (Button) containerView.findViewById(R.id.feedback_smt_btn);
        feedbacksmtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!feedbacket.getText().toString().trim().equals(""))
                    getData();
                else {
                    ContextUtil.toast(getApplicationContext(), "期待您的反馈");
                }
            }
        });
    }

    private void addOkBtn() {

        Button okBtn = (Button) View.inflate(getApplicationContext(), R.layout.add_addr_ok, null);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        addRightItem(okBtn);
    }

    private void initialize() {

    }
}
