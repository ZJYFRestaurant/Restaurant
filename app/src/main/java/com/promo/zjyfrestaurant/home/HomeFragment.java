package com.promo.zjyfrestaurant.home;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.jch.lib.util.DialogUtil;
import com.jch.lib.view.ScrollGridView;
import com.jch.lib.view.ScrollListView;
import com.promo.zjyfrestaurant.BaseFragment;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.IndexDataBean;
import com.promo.zjyfrestaurant.home.recommendPager.RecommendActivity;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static HomeFragment fragment;
    private ScrollGridView homeGv = null;
    private HomeSupAdapter homeSupAdapter = null;
    private HomeSpecialtyAdapter specialtyAdapter = null;
    private ScrollListView listView = null;

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

        homeGv = (ScrollGridView) view.findViewById(R.id.home_sup_gv);
        listView = (ScrollListView) view.findViewById(R.id.home_specialty_lv);

        homeGv.setSelector(new BitmapDrawable());
        homeSupAdapter = new HomeSupAdapter(getActivity(), supDrawables);
        homeGv.setAdapter(homeSupAdapter);
        homeGv.setOnItemClickListener(this);

        specialtyAdapter = new HomeSpecialtyAdapter(getActivity(), null);
        listView.setAdapter(specialtyAdapter);

        getData();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Class newClz = null;

        switch (position) {
            case 0: {

                newClz = RecommendActivity.class;
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            default: {

            }
        }

        Intent intent = new Intent(getActivity(), newClz);
        transNextPage(intent);
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
