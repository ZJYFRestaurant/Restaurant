package com.promo.zjyfrestaurant.home;

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
import com.promo.zjyfrestaurant.bean.HotProductBean;
import com.promo.zjyfrestaurant.util.ContextUtil;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/11.
 */
public class HomeSpecialtyAdapter extends BaseAdapter {

    private Activity mContext;
    private static final float IMGWIDTHPERCENT = 0.48f;

    private final static int BASEINDEX = 0x1000;

    private ArrayList<HotProductBean> hotProductBeans = new ArrayList<HotProductBean>();

    public HomeSpecialtyAdapter(Activity context, ArrayList<HotProductBean> hotProductBeans) {
        this.mContext = context;

        changeData(hotProductBeans);
    }

    public void changeData(ArrayList<HotProductBean> hotProductBeans) {
        if (hotProductBeans != null) {
            this.hotProductBeans.clear();
            this.hotProductBeans.addAll(hotProductBeans);
        }
    }

    @Override
    final public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    /**
     * 重写数据改变方法，防止数据数据改变线程安全问题。
     *
     * @param hotProductBeans
     */
    public void notifyDataSetChanged(ArrayList<HotProductBean> hotProductBeans) {
        changeData(hotProductBeans);
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return hotProductBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return hotProductBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        int viewType = position % 2;    //两种ViewType。

        if (convertView == null) {
            convertView = getView(convertView, viewHolder, viewType, position);
            viewHolder = (ViewHolder) convertView.getTag();
        } else {

            int convertType = convertView.getId() % 2;
            if (viewType == convertType) {      //是需要的layout类型
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                convertView = getView(convertView, viewHolder, viewType, position);
                viewHolder = (ViewHolder) convertView.getTag();
            }
        }


        HotProductBean hotProductBean = hotProductBeans.get(position);

        if (hotProductBean != null) {

            ImageManager.load(hotProductBean.getCover(), viewHolder.img, ContextUtil.getRectangleImgOptions(), (int) mContext.getResources().getDimension(R.dimen.img_circle_corner));
            viewHolder.nameTv.setText(hotProductBean.getName() == null ? "" : hotProductBean.getName());
            viewHolder.priceTv.setText(String.valueOf((int) hotProductBean.getNew_price()));
            viewHolder.levelView.setStartNum(hotProductBean.getStar());
        }

        return convertView;
    }

    private View getView(View convertview, ViewHolder viewHolder, int viewType, int position) {
        convertview = getViewByType(viewType);
        viewHolder = new ViewHolder();
        viewHolder.img = (ImageView) convertview.findViewById(R.id.home_specialty_item_img);
        int exceptWidth = (int) (mContext.getResources().getDimension(R.dimen.home_list_item_margin_r_l) * 2 + mContext.getResources().getDimension(R.dimen.home_list_item_pad_r_l) * 2);
        DisplayUtil.resizeViewByScreenWidth(viewHolder.img, Constant.HOME_IMG_POINT.x, Constant.HOME_IMG_POINT.y, exceptWidth, mContext, IMGWIDTHPERCENT);
        viewHolder.levelView = (HomeStartView) convertview.findViewById(R.id.home_specialty_item_star);
        viewHolder.nameTv = (TextView) convertview.findViewById(R.id.home_specialty_item_name);
        viewHolder.priceTv = (TextView) convertview.findViewById(R.id.home_specialty_item_price);
        convertview.setId(BASEINDEX + position);
        convertview.setTag(viewHolder);

        return convertview;
    }


    /**
     * 根据type的类型获取相应的view
     *
     * @param ViewType
     * @return
     */
    private View getViewByType(int ViewType) {

        View convertView = null;
        if (ViewType == 1) {
            convertView = View.inflate(mContext, R.layout.home_specialty_lv_item1, null);
        } else {
            convertView = View.inflate(mContext, R.layout.home_specialty_lv_item0, null);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView img;
        TextView nameTv;
        TextView priceTv;
        HomeStartView levelView;

    }
}
