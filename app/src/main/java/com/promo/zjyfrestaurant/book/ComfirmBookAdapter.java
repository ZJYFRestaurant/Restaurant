package com.promo.zjyfrestaurant.book;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.bean.DishBean;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/31.
 */
public class ComfirmBookAdapter extends BaseAdapter {

    private ArrayList<DishBean> dishBeans = null;
    private Context context;

    public ComfirmBookAdapter(Context context, ArrayList<DishBean> dishBeans) {

        this.dishBeans = dishBeans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dishBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return dishBeans.get(position);
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
            convertView = View.inflate(context, R.layout.confirm_order_gv_item, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.order_item_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DishBean dishBean = dishBeans.get(position);
        viewHolder.textView.setText(dishBean.getName() + "*" + dishBean.getNum());

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;

    }
}
