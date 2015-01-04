package com.promo.zjyfrestaurant.personal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.bean.AddressBean;

import java.util.ArrayList;

/**
 * Created by ACER on 2015/1/4.
 */
public class AddressAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<AddressBean> addresses = new ArrayList<>();
    private ImageView itemdelbtn;
    private TextView addritemnametv;
    private TextView addritemphonetv;
    private TextView addritemtv;
    private DelAddrCallBack delAddrCallBack;


    public AddressAdapter(Context context) {
        this.mContext = context;
    }

    public void notifyDataSetChanged(ArrayList<AddressBean> addresses) {

        this.addresses.clear();
        this.addresses.addAll(addresses);

        super.notifyDataSetChanged();
    }

    public void setDelAddrCallBack(DelAddrCallBack delAddrCallBack) {
        this.delAddrCallBack = delAddrCallBack;
    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return addresses.get(position);
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
            convertView = View.inflate(mContext, R.layout.addr_manage_item, null);

            viewHolder.delBtn = (ImageView) convertView.findViewById(R.id.item_del_btn);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.addr_item_name_tv);
            viewHolder.phoneTv = (TextView) convertView.findViewById(R.id.addr_item_phone_tv);
            viewHolder.addTv = (TextView) convertView.findViewById(R.id.addr_item_tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AddressBean address = addresses.get(position);
        viewHolder.addTv.setText(address.getContent());
        viewHolder.nameTv.setText(address.getContact());
        viewHolder.phoneTv.setText(address.getTel());
        viewHolder.delBtn.setOnClickListener(new DelOnCLS(position));

        return convertView;
    }

    /**
     * 删除地址监听。
     */
    private class DelOnCLS implements View.OnClickListener {

        int position = -1;

        public DelOnCLS(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (delAddrCallBack != null) {
                delAddrCallBack.delAddr(position);
            }
        }
    }

    private static class ViewHolder {

        ImageView delBtn;
        TextView nameTv;
        TextView phoneTv;
        TextView addTv;
    }
}

interface DelAddrCallBack {
    public void delAddr(int pointion);

}
