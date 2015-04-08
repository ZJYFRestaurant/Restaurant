package com.promote.zjyfrestaurant.home.recommendPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jch.lib.util.DialogUtil;
import com.promote.zjyfrestaurant.BaseActivity;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.application.HttpConstant;
import com.promote.zjyfrestaurant.bean.ProDetailBean;
import com.promote.zjyfrestaurant.home.MenuDetailActivity;
import com.promote.zjyfrestaurant.impl.RequestCallback;
import com.promote.zjyfrestaurant.impl.ShowMenuRequset;
import com.promote.zjyfrestaurant.impl.ZJYFRequestParmater;

import java.util.ArrayList;

/**
 * 菜品推荐。
 */
public class RecommendActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView recommendlist;
    private RecommendAdapter recommendAdapter;

    private ArrayList<ProDetailBean> proDetailBeans = new ArrayList<>();

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_recommend, null);

        initView(containerView);

        return containerView;
    }

    private void initView(View containerView) {
        setTitle(getResources().getString(R.string.recommend_dish));
        addRightCart();

        recommendlist = (ListView) containerView.findViewById(R.id.recommend_list);
        recommendAdapter = new RecommendAdapter(this);
        recommendlist.setAdapter(recommendAdapter);
        recommendlist.setOnItemClickListener(this);

        getData();
    }

    /**
     * 获取网络数据。
     */
    @Override
    protected void getData() {
        String urlStr = HttpConstant.getHotProductList;
        ZJYFRequestParmater parmater = new ZJYFRequestParmater(getApplicationContext());

        ShowMenuRequset.getListData(this, urlStr, parmater, ProDetailBean.class, new RequestCallback<ArrayList<ProDetailBean>>() {
            @Override
            public void onfailed(String msg) {
                DialogUtil.toastMsg(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(ArrayList<ProDetailBean> data) {
                if (data != null) {

                    proDetailBeans.clear();
                    proDetailBeans.addAll(data);
                    recommendAdapter.notifyDataSetChanged(proDetailBeans);
                }

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, MenuDetailActivity.class);
        int pro_id = proDetailBeans.get(position).getId();

        Bundle bundle = new Bundle();
        bundle.putString(MenuDetailActivity.PRO_ID_KEY, String.valueOf(pro_id));
        intent.putExtras(bundle);
        transNextPage(intent, true);
    }
}
