package com.promo.zjyfrestaurant.personal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jch.lib.util.ImageManager;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.bean.MyBookBean;
import com.promo.zjyfrestaurant.bean.MyBooksBean;
import com.promo.zjyfrestaurant.util.ContextUtil;

/**
 * Created by ACER on 2014/12/31.
 */
public class MyBookAdapter extends BaseExpandableListAdapter {

    private MyBooksBean myBooksBean = null;
    private Context context = null;

    public MyBookAdapter(Context context) {
        this.context = context;
    }

    public void notifyDataSetChanged(MyBooksBean myBooksBean) {
        this.myBooksBean = myBooksBean;
        super.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 1) {               //过期
            return myBooksBean.getOverdue().size();
        } else {         //未过期。
            return myBooksBean.getNormal().size();
        }

    }

    @Override
    public Object getGroup(int groupPosition) {
        if (groupPosition == 1) {               //过期
            return myBooksBean.getOverdue();
        } else {         //未过期。
            return myBooksBean.getNormal();
        }

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition == 1) {
            return myBooksBean.getOverdue().get(childPosition);
        } else {
            return myBooksBean.getNormal().get(childPosition);
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        int viewIds;
        if (groupPosition == 0) {
            viewIds = R.layout.mybook_title_ed;
        } else {
            viewIds = R.layout.mybook_title_ed;
        }
        convertView = View.inflate(context, viewIds, null);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = View.inflate(context, R.layout.mybook_pre_item, null);

            childViewHolder.img = (ImageView) convertView.findViewById(R.id.mybook_item_img);
            childViewHolder.type = (TextView) convertView.findViewById(R.id.mybook_type_tv);
            childViewHolder.people = (TextView) convertView.findViewById(R.id.custome_num_tv);
            childViewHolder.time = (TextView) convertView.findViewById(R.id.mybook_time_tv);

            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        MyBookBean myBookBean;
        if (groupPosition == 0) {
            myBookBean = myBooksBean.getNormal().get(childPosition);
        } else {
            myBookBean = myBooksBean.getOverdue().get(childPosition);
        }

        ImageManager.load(myBookBean.getCover(), childViewHolder.img, ContextUtil.getRectangleImgOptions(), (int) context.getResources().getDimension(R.dimen.img_circle_corner));
        childViewHolder.type.setText(context.getResources().getStringArray(R.array.book_tab)[myBookBean.getType()]);
        childViewHolder.people.setText(String.valueOf(myBookBean.getPeople_num()));
        childViewHolder.time.setText(myBookBean.getUse_time());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ChildViewHolder {

        ImageView img;
        TextView type;
        TextView people;
        TextView time;
    }


}
