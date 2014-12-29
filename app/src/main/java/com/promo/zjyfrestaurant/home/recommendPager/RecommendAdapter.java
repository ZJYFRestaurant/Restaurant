package com.promo.zjyfrestaurant.home.recommendPager;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jch.lib.util.DisplayUtil;
import com.jch.lib.util.ImageManager;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.Constant;
import com.promo.zjyfrestaurant.bean.ProDetailBean;
import com.promo.zjyfrestaurant.home.HomeStartView;
import com.promo.zjyfrestaurant.util.ContextUtil;
import com.promo.zjyfrestaurant.view.AddDealPicker;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/25.
 */
public class RecommendAdapter extends BaseAdapter {

    private ArrayList<ProDetailBean> proDetails = new ArrayList<>();
    public Activity context;

    public RecommendAdapter(Activity context) {

        this.context = context;

    }

    public void notifyDataSetChanged(ArrayList<ProDetailBean> proDetails) {
        if (proDetails != null) {
            this.proDetails.clear();
            this.proDetails.addAll(proDetails);
        }
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return proDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return proDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.recommend_item, null);
            viewHolder = new ViewHolder();
            viewHolder.addDealPicker = (AddDealPicker) convertView.findViewById(R.id.rcmd_deal_picker);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.recmd_item_img);
            int exct = (int) ((int) (context.getResources().getDimension(R.dimen.common_pad_right) * 2) + 2 * context.getResources().getDimension(R.dimen.home_item_pad));
            DisplayUtil.resizeViewByScreenWidth(viewHolder.img, Constant.RECM_IMG_POINT.x, Constant.RECM_IMG_POINT.y, exct, context);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.rcmd_name_tv);
            viewHolder.priceTv = (TextView) convertView.findViewById(R.id.rcmd_price_tv);
            viewHolder.startView = (HomeStartView) convertView.findViewById(R.id.rcmd_star);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ProDetailBean proDetailBean = proDetails.get(position);
        ImageManager.load(proDetailBean.getCover(), viewHolder.img, ContextUtil.getRectangleImgOptions(), (int) context.getResources().getDimension(R.dimen.img_circle_corner));
        viewHolder.nameTv.setText(proDetailBean.getName());
        viewHolder.startView.setStartNum(proDetailBean.getStar());
        viewHolder.priceTv.setText(proDetailBean.getNew_price());
//        viewHolder.addDealPicker.

        return convertView;
    }

    static class ViewHolder {

        ImageView img;
        AddDealPicker addDealPicker;
        TextView nameTv;
        HomeStartView startView;
        TextView priceTv;
    }

}
