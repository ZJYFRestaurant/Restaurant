package com.promo.zjyfrestaurant.home;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jch.lib.util.DisplayUtil;
import com.promo.zjyfrestaurant.R;

/**
 * Created by ACER on 2014/12/11.
 */
public class HomeSupAdapter extends BaseAdapter {

    private Activity mContext;
    private int[] home_sups = null;
    private Point mPoint = new Point();

    public HomeSupAdapter(Activity context, int[] home_sups) {
        this.mContext = context;
        this.home_sups = home_sups;
        getSupDrawableSize();

    }

    private void getSupDrawableSize() {

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.home_sup_fav);
        mPoint.x = bitmap.getWidth();
        mPoint.y = bitmap.getHeight();
    }

    @Override
    public int getCount() {
        return home_sups.length;
    }

    @Override
    public Object getItem(int position) {
        return home_sups[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout frameLayout = (LinearLayout) View.inflate(mContext, R.layout.home_sup_item, null);
        ImageView iv = (ImageView) frameLayout.findViewById(R.id.home_sup_item_iv);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        int margin = (int) (mContext.getResources().getDimension(R.dimen.home_item_pad) * 6);       //两个item的留白为2*2+2（gradview的留白）
        DisplayUtil.resizeViewByScreenWidth(iv, mPoint.x, mPoint.y, margin, mContext, 2);

        iv.setBackgroundResource(home_sups[position]);
        return frameLayout;
    }

}
