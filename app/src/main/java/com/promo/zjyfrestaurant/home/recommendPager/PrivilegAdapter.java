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
import com.promo.zjyfrestaurant.bean.PrivilegeActionBean;
import com.promo.zjyfrestaurant.util.ContextUtil;

import java.util.ArrayList;

/**
 * 优惠活动adapter.
 * <p/>
 * Created by ACER on 2014/12/26.
 */
public class PrivilegAdapter extends BaseAdapter {

    private static final float point_screen = 0.4f;

    private Activity context;
    private ArrayList<PrivilegeActionBean> privileges = new ArrayList<>();

    public PrivilegAdapter(Activity context) {
        this.context = context;
    }

    public void notifyDataSetChanged(ArrayList<PrivilegeActionBean> privileges) {

        if (privileges != null) {
            this.privileges.clear();
            this.privileges.addAll(privileges);
        }
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return privileges.size();
    }

    @Override
    public Object getItem(int position) {
        return privileges.get(position);
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
            convertView = View.inflate(context, R.layout.privilege_item, null);

            viewHolder.img = (ImageView) convertView.findViewById(R.id.privilege_item_img);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.privilege_item_name_tvs);
            viewHolder.desTv = (TextView) convertView.findViewById(R.id.privilege_item_des_tv);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        PrivilegeActionBean privilege = privileges.get(position);
        int exceptSpace = (int) context.getResources().getDimension(R.dimen.common_pad_right) * 2;
        DisplayUtil.resizeViewByScreenWidth(viewHolder.img, Constant.PRIV_IMG_POINT.x, Constant.PRIV_IMG_POINT.y, exceptSpace, context, point_screen);
        ImageManager.load(privilege.getCover(), viewHolder.img, ContextUtil.getRectangleImgOptions());
        viewHolder.nameTv.setText(privilege.getTitle());
        viewHolder.desTv.setText(privilege.getContent());

        return convertView;
    }

    private static class ViewHolder {
        ImageView img;
        TextView nameTv;
        TextView desTv;
    }
}
