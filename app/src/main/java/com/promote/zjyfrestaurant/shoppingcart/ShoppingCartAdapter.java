package com.promote.zjyfrestaurant.shoppingcart;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jch.lib.util.ImageManager;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.bean.DishBean;
import com.promote.zjyfrestaurant.util.ContextUtil;
import com.promote.zjyfrestaurant.view.AddDealPicker;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/30.
 */
public class ShoppingCartAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DishBean> dishBeans = new ArrayList<DishBean>();

    public ShoppingCartAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void notifyDataSetChanged() {
        ShoppingCart shoppingCart = ShoppingCart.newInstance();
        ArrayList<DishBean> data = shoppingCart.getDishBeans();
        if (data != null && data.size() != 0) {
            this.dishBeans.clear();
            this.dishBeans.addAll(data);
        }
        super.notifyDataSetChanged();
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
            convertView = View.inflate(context, R.layout.cart_item, null);

            viewHolder.img = (ImageView) convertView.findViewById(R.id.cart_item_img);
            viewHolder.addDealPicker = (AddDealPicker) convertView.findViewById(R.id.cart_item_addpick);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.cart_item_name_tv);
            viewHolder.price = (TextView) convertView.findViewById(R.id.cart_item_price_tv);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.cart_item_cb);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cb.setOnCheckedChangeListener(new ItemOCLSN(position));
        DishBean dishBean = dishBeans.get(position);

        viewHolder.cb.setChecked(dishBean.getIsOrder() == 1);
        ImageManager.load(dishBean.getCover(), viewHolder.img, ContextUtil.getCircleImgOptions(), (int) context.getResources().getDimension(R.dimen.img_circle_corner));
        viewHolder.nameTv.setText(dishBean.getName());
        viewHolder.price.setText(String.valueOf(dishBean.getNew_price()));
        viewHolder.addDealPicker.setDishBean(dishBean);
        viewHolder.addDealPicker.setNotifyCartFlag(true);

        return convertView;
    }


    private static class ViewHolder {
        CheckBox cb;
        ImageView img;
        TextView price;
        TextView nameTv;
        AddDealPicker addDealPicker;
    }

    /**
     * 菜品订购监听。
     */
    private class ItemOCLSN implements CompoundButton.OnCheckedChangeListener {

        private int position;

        public ItemOCLSN(int position) {

            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            DishBean dishBean = dishBeans.get(position);
            dishBean.dataChanged();         //跟新购物车订购数据。
            dishBean.setIsOrder(isChecked ? 1 : 0);
            dishBean.nodifyDataChanged();
        }
    }
}
