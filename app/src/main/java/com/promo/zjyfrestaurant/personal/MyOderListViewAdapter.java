package com.promo.zjyfrestaurant.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jch.lib.view.ScrollGridView;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.bean.OrderTimeBean;
import com.promo.zjyfrestaurant.bean.TimeOrderBean;
import com.promo.zjyfrestaurant.home.MenuDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ACER on 2015/1/5.
 */
public class MyOderListViewAdapter extends BaseAdapter {

    private Context mContext;

    private HashMap<String, ArrayList<TimeOrderBean>> orderDatas = new HashMap<String, ArrayList<TimeOrderBean>>();
    private ArrayList<String> dataStr = new ArrayList<>();

    public MyOderListViewAdapter(Context context) {
        this.mContext = context;
    }


    public void notifyDataSetChanged(OrderTimeBean data) {
        data.parseData();
        dataStr.addAll(data.sortTime());
        this.orderDatas.putAll(data.getOrderDatas());
        super.notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {


        return position % 2;
    }

    @Override
    public int getCount() {
        return orderDatas.size() * 2;
    }

    @Override
    public Object getItem(int position) {

        return orderDatas.get(dataStr.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TitleViewHolder titleHolder = null;
        ContainerViewHolder containerViewHolder = null;

        int type = getItemViewType(position);       //分类。
        if (convertView == null) {
            if (type == 0) {
                titleHolder = new TitleViewHolder();
                convertView = View.inflate(mContext, R.layout.myorder_time_item, null);
                titleHolder.titlTv = (TextView) convertView.findViewById(R.id.myorder_time_item_tv);
                convertView.setTag(titleHolder);
            } else {
                containerViewHolder = new ContainerViewHolder();
                convertView = View.inflate(mContext, R.layout.myorder_dishes_item, null);
                containerViewHolder.listView = (ScrollGridView) convertView.findViewById(R.id.myorder_dishes_lv);
                convertView.setTag(containerViewHolder);
            }
        } else {
            if (type == 0)
                titleHolder = (TitleViewHolder) convertView.getTag();
            else
                containerViewHolder = (ContainerViewHolder) convertView.getTag();
        }

        ArrayList<TimeOrderBean> timeOrderBeans = orderDatas.get(dataStr.get(position / 2));

        if (type == 0) {
            titleHolder.titlTv.setText(dataStr.get(position / 2));
        } else {
            MyOrderGridViewAdapter adapter = new MyOrderGridViewAdapter(mContext, timeOrderBeans);      //向gradveiw中添加数据。
            containerViewHolder.listView.setAdapter(adapter);
            containerViewHolder.listView.setOnItemClickListener(new ListOICSN(timeOrderBeans));
        }

        return convertView;
    }

    /**
     * gridview 的 item 点击事件。
     */
    private class ListOICSN implements AdapterView.OnItemClickListener {

        private ArrayList<TimeOrderBean> timeOrderBeans;

        public ListOICSN(ArrayList<TimeOrderBean> timeOrderBeans) {
            this.timeOrderBeans = timeOrderBeans;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(mContext, MenuDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(MenuDetailActivity.PRO_ID_KEY, String.valueOf(timeOrderBeans.get(position).getId()));
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    private static class TitleViewHolder {

        TextView titlTv;
    }

    private static class ContainerViewHolder {

        ScrollGridView listView;
    }

}
