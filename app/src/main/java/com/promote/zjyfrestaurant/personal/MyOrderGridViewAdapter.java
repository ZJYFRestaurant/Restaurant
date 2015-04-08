package com.promote.zjyfrestaurant.personal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jch.lib.util.ImageManager;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.bean.TimeOrderBean;
import com.promote.zjyfrestaurant.util.ContextUtil;

import java.util.ArrayList;

/**
 * Created by ACER on 2015/1/6.
 */
public class MyOrderGridViewAdapter extends BaseAdapter {

    private Context mContext;

    ArrayList<TimeOrderBean> timeOrderBeans;

    public MyOrderGridViewAdapter(Context context, ArrayList<TimeOrderBean> timeOrderBeans) {

        this.mContext = context;
        this.timeOrderBeans = timeOrderBeans;
    }

    @Override
    public int getCount() {
        return timeOrderBeans.size();
    }

    @Override
    public Object getItem(int position) {

        return timeOrderBeans.get(position);
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
            convertView = View.inflate(mContext, R.layout.myorder_dish_item, null);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.myorder_dish_item_img);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.myorder_dish_item_name_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TimeOrderBean timeOrderBean = timeOrderBeans.get(position);
        ImageManager.load(timeOrderBean.getCover(), viewHolder.img, ContextUtil.getRectangleImgOptions(), (int) mContext.getResources().getDimension(R.dimen.img_circle_corner));
        viewHolder.nameTv.setText(timeOrderBean.getName());

        return convertView;
    }


    private static class ViewHolder {

        ImageView img;
        TextView nameTv;

    }
}
