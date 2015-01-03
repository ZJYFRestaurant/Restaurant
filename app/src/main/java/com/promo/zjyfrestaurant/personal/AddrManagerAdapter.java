package com.promo.zjyfrestaurant.personal;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.promo.zjyfrestaurant.bean.AddressBean;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/31.
 */
public class AddrManagerAdapter extends BaseAdapter {

    private ArrayList<AddressBean> addressBeans = new ArrayList<AddressBean>();

    public void notifyDataSetChanged(ArrayList<AddressBean> addressBeans) {
        this.addressBeans.clear();
        this.addressBeans.addAll(addressBeans);
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return addressBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return addressBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        return null;
    }


}
