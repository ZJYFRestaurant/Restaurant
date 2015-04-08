package com.promote.zjyfrestaurant.home;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.jch.lib.util.DialogUtil;
import com.jch.lib.view.PagerScrollView;
import com.jch.lib.view.ScrollGridView;
import com.jch.lib.view.ScrollListView;
import com.promote.zjyfrestaurant.BaseFragment;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.application.HttpConstant;
import com.promote.zjyfrestaurant.bean.IndexDataBean;
import com.promote.zjyfrestaurant.home.recommendPager.MenuActivity;
import com.promote.zjyfrestaurant.home.recommendPager.MienActivity;
import com.promote.zjyfrestaurant.home.recommendPager.PrivilegeActivity;
import com.promote.zjyfrestaurant.home.recommendPager.RecommendActivity;
import com.promote.zjyfrestaurant.impl.RequestCallback;
import com.promote.zjyfrestaurant.impl.ShowMenuRequset;
import com.promote.zjyfrestaurant.impl.ZJYFRequestParmater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {

    private static HomeFragment fragment;
    private ScrollGridView homeGv = null;
    private HomeSupAdapter homeSupAdapter = null;
    private HomeSpecialtyAdapter specialtyAdapter = null;
    private ScrollListView listView = null;
    private PagerScrollView pagerScrollView = null;

    private IndexDataBean mIndexData;

    private int[] supDrawables = new int[]{
            R.drawable.home_sup_recommand_sel,
            R.drawable.home_sup_fav_sel,
            R.drawable.home_sup_menu_sel,
            R.drawable.home_sup_mien_sel
    };

    private View mContainerView = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {

        if (fragment == null) {
            fragment = new HomeFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
        }
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View addContentView(LayoutInflater inflater) {

        mContainerView = inflater.inflate(R.layout.fragment_home, null);

        hideHeadView();
        init(mContainerView);
        return mContainerView;
    }

    private void init(View view) {
        pagerScrollView = (PagerScrollView) view.findViewById(R.id.home_sv);
        homeGv = (ScrollGridView) view.findViewById(R.id.home_sup_gv);
        listView = (ScrollListView) view.findViewById(R.id.home_specialty_lv);
        listView.setParentScrollView(pagerScrollView);

        homeGv.setSelector(new BitmapDrawable());
        homeSupAdapter = new HomeSupAdapter(getActivity(), supDrawables);
        homeGv.setAdapter(homeSupAdapter);
        homeGv.setOnItemClickListener(new GridItemClickLSN());

        specialtyAdapter = new HomeSpecialtyAdapter(getActivity(), null);
        listView.setAdapter(specialtyAdapter);
        listView.setOnItemClickListener(new ListItemClk());

        getData();

    }

    /**
     * 四大分类。
     */
    private class GridItemClickLSN implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Class newClz = null;

            switch (position) {
                case 0: {
                    newClz = RecommendActivity.class;
                    break;
                }
                case 1: {
                    newClz = PrivilegeActivity.class;
                    break;
                }
                case 2: {
                    newClz = MenuActivity.class;
                    break;
                }
                case 3: {

                    newClz = MienActivity.class;
                    break;
                }
                default: {

                }
            }
            if (newClz != null) {
                Intent intent = new Intent(getActivity(), newClz);
                transNextPage(intent);
            }
        }

    }

    private class ListItemClk implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), MenuDetailActivity.class);
            int pro_id = mIndexData.getHotProductList().get(position).getId();

            Bundle bundle = new Bundle();
            bundle.putString(MenuDetailActivity.PRO_ID_KEY, String.valueOf(pro_id));
            intent.putExtras(bundle);
            transNextPage(intent);
        }
    }

    /**
     * 获取网络数据。
     */
    private void getData() {

        String urlStr = HttpConstant.getIndex;
        ZJYFRequestParmater parmater = new ZJYFRequestParmater(getActivity().getApplicationContext());
        ShowMenuRequset.getData(getActivity(), urlStr, parmater, IndexDataBean.class, new RequestCallback<IndexDataBean>() {
            @Override
            public void onfailed(String msg) {
                DialogUtil.toastMsg(getActivity().getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(IndexDataBean data) {
                if (data != null) {
                    mIndexData = data;
                    specialtyAdapter.notifyDataSetChanged(mIndexData.getHotProductList());
                }

            }
        });
    }


}
