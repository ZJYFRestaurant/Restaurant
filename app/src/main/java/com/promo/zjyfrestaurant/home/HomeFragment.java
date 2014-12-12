package com.promo.zjyfrestaurant.home;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.jch.lib.view.ScrollGridView;
import com.promo.zjyfrestaurant.BaseFragment;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.IndexDataBean;
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

        homeGv.setSelector(new BitmapDrawable());
        homeSupAdapter = new HomeSupAdapter(getActivity(), supDrawables);
        homeGv.setAdapter(homeSupAdapter);
        homeGv.setOnItemClickListener(this);
        getData();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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


            }

            @Override
            public void onSuccess(IndexDataBean data) {

                if (data != null) {
                    mIndexData = data;
                }

            }
        });
    }


}
