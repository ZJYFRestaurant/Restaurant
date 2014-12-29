package com.promo.zjyfrestaurant.home.recommendPager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.bean.MenuCategoryBean;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/29.
 */
public class MenuAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MenuCategoryBean> menus = new ArrayList<>();

    public MenuAdapter(Context context) {
        this.context = context;
    }

    public void notifyDataSetChanged(ArrayList<MenuCategoryBean> menus) {

        this.menus.clear();
        this.menus.addAll(menus);
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RadioButton rb = (RadioButton) View.inflate(context, R.layout.menu_cate_btn, null);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.title_height));
        rb.setLayoutParams(params);
        String btn = this.menus.get(position).getName();
        rb.setText(btn);
        return rb;
    }


}
