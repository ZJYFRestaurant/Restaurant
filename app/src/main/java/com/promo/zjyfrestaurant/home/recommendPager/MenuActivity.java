package com.promo.zjyfrestaurant.home.recommendPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.jch.lib.util.DialogUtil;
import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.MenuCategoryBean;
import com.promo.zjyfrestaurant.bean.ProductBean;
import com.promo.zjyfrestaurant.home.MenuDetailActivity;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCartView;
import com.promo.zjyfrestaurant.view.RadioListView;

import java.util.ArrayList;

/**
 * 飘香菜单。
 */
public class MenuActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {


    private RadioListView menucatlv;
    private ListView menulv;
    private MenuAdapter cateAdapter;
    private MenuListAdapter menuListAdapter;
    private ArrayList<MenuCategoryBean> menuCategoryBeans = new ArrayList<>();
    private ArrayList<ProductBean> productBeans = new ArrayList<>();

    private int page = 0;
    /**
     * 页码 *
     */
    private int pageSize = 20;

    @Override
    protected View initContentView() {
        View containerView = View.inflate(getApplicationContext(), R.layout.activity_menu, null);

        initView(containerView);
        return containerView;
    }

    @Override
    protected void getData() {

        String urlStr = HttpConstant.getProductCategory;
        ShowMenuRequset.getListData(this, urlStr, new ZJYFRequestParmater(getApplicationContext()), MenuCategoryBean.class, new RequestCallback<ArrayList<MenuCategoryBean>>() {
            @Override
            public void onfailed(String msg) {
                DialogUtil.toastMsg(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(ArrayList<MenuCategoryBean> data) {
                if (data != null) {
                    menuCategoryBeans.clear();
                    menuCategoryBeans.addAll(data);
                    cateAdapter.notifyDataSetChanged(data);
                }

            }
        });
    }

    private void getMenuData(int category) {
        String urlStr = HttpConstant.getProductList;
        ZJYFRequestParmater parmater = new ZJYFRequestParmater(getApplicationContext());
        parmater.put("category_id", category);
        parmater.put("page", page);
        parmater.put("pagesize", pageSize);

        ShowMenuRequset.getListData(this, urlStr, parmater, ProductBean.class, new RequestCallback<ArrayList<ProductBean>>() {
            @Override
            public void onfailed(String msg) {
                DialogUtil.toastMsg(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(ArrayList<ProductBean> data) {
                if (data != null) {
                    productBeans.clear();
                    productBeans.addAll(data);
                    menuListAdapter.notifyDataSetChanged(data);
                }

            }
        });

    }

    /**
     * 初始化view。
     *
     * @param containerView
     */
    private void initView(View containerView) {

        menucatlv = (RadioListView) containerView.findViewById(R.id.menu_cat_lv);
        menulv = (ListView) containerView.findViewById(R.id.menu_lv);
        cateAdapter = new MenuAdapter(getApplicationContext());
        menucatlv.setAdapter(cateAdapter);
        menucatlv.setCheckedChangeListener(this);

        menuListAdapter = new MenuListAdapter(getApplicationContext());
        menulv.setAdapter(menuListAdapter);
        menulv.setOnItemClickListener(this);

        setTitle(getResources().getString(R.string.menu_title));
        addShoppingCart();

        getData();

    }

    private void addShoppingCart() {
        ShoppingCartView shoppingCartView = new ShoppingCartView(getApplicationContext());
        addRightItem(shoppingCartView);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        getMenuData(menuCategoryBeans.get(checkedId).getId());
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MenuActivity.this, MenuDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MenuDetailActivity.PRO_ID_KEY, String.valueOf(productBeans.get(position).getId()));
        intent.putExtras(bundle);
        transNextPage(intent, true);

    }
}
