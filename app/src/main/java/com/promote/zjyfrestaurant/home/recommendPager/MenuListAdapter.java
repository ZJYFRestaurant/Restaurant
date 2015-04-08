package com.promote.zjyfrestaurant.home.recommendPager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jch.lib.util.ImageManager;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.bean.ProductBean;
import com.promote.zjyfrestaurant.home.HomeStartView;
import com.promote.zjyfrestaurant.util.ContextUtil;
import com.promote.zjyfrestaurant.view.AddDealPicker;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/29.
 */
public class MenuListAdapter extends BaseAdapter {


    private ArrayList<ProductBean> productBeans = new ArrayList<>();
    private Context context;

    public MenuListAdapter(Context context) {
        this.context = context;
    }

    public void notifyDataSetChanged(ArrayList<ProductBean> productBeans) {
        this.productBeans.clear();
        this.productBeans.addAll(productBeans);
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return productBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return productBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.menu_item_layout, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.menu_item_img);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.menu_item_name_tv);
            viewHolder.startView = (HomeStartView) convertView.findViewById(R.id.menu_item_star);
            viewHolder.priceTv = (TextView) convertView.findViewById(R.id.menu_item_price_tv);
            viewHolder.addDealPicker = (AddDealPicker) convertView.findViewById(R.id.menu_deal_picker);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ProductBean productBean = productBeans.get(position);
        ImageManager.load(productBean.getCover(), viewHolder.imageView, ContextUtil.getCircleImgOptions());
        viewHolder.nameTv.setText(productBean.getName());
        viewHolder.startView.setStartNum(productBean.getStar());
        viewHolder.priceTv.setText(String.valueOf(productBean.getNew_price()));
        viewHolder.addDealPicker.setDishBean(productBean);

        return convertView;
    }


    private static class ViewHolder {
        ImageView imageView;
        TextView nameTv;
        HomeStartView startView;
        TextView priceTv;
        AddDealPicker addDealPicker;
    }
}
