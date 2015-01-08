package com.promo.zjyfrestaurant.loading;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.promo.zjyfrestaurant.MainActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.ZjyfApplication;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * 加载界面。
 */
public class LoadingActivity extends FragmentActivity implements GuidPageFragment.OnFragmentInteractionListener {

    private ViewPager loadingvp;
    private CirclePageIndicator pagerindicator;

    private int[] pagerBgs = new int[]{R.drawable.guid1_img, R.drawable.guid2_img, R.drawable.guid3_img};
    private int[] pagerTexts = new int[]{R.drawable.guid1_text, R.drawable.guid2_text, R.drawable.guid3_text};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (((ZjyfApplication) getApplicationContext()).shouldGuid()) {       //显示引导页。
            showGuidPager();
        } else {
            showLoadingPager();     //显示加载页。
        }
    }


    private void initialize() {

        loadingvp = (ViewPager) findViewById(R.id.loading_vp);
        pagerindicator = (CirclePageIndicator) findViewById(R.id.pager_indicator);
        LoadingFragmentAdapter adapter = new LoadingFragmentAdapter(getSupportFragmentManager(), pagerBgs, pagerTexts);
        loadingvp.setAdapter(adapter);
        pagerindicator.setViewPager(loadingvp);


    }

    /**
     * 引导页.
     */
    private void showGuidPager() {
        setContentView(R.layout.activity_guid);
        initialize();
        ((ZjyfApplication) getApplicationContext()).guidOver();
    }

    private void showLoadingPager() {
        View contentView = View.inflate(getApplicationContext(), R.layout.activity_loading, null);
        setContentView(contentView);
        contentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                LoadingActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 1500);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}