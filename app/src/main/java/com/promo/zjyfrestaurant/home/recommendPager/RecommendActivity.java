package com.promo.zjyfrestaurant.home.recommendPager;

import android.view.View;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;

/**
 * 菜品推荐。
 */
public class RecommendActivity extends BaseActivity {

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_recommend, null);

        initView(containerView);

        return containerView;
    }

    private void initView(View containerView) {
        setTitle(getResources().getString(R.string.recommend_dish));
    }


}
