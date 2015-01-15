package com.promo.zjyfrestaurant.personal;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.MyBookBean;
import com.promo.zjyfrestaurant.bean.MyBooksBean;
import com.promo.zjyfrestaurant.bean.OrderType;
import com.promo.zjyfrestaurant.util.LogCat;

/**
 * Created by ACER on 2014/12/31.
 */
public class MyBookAdapter extends BaseExpandableListAdapter {
    DisplayImageOptions squareImageOptions = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .showImageForEmptyUri(R.drawable.my_book_empty_img)
            .showImageOnLoading(R.drawable.square_loading)
            .showImageOnFail(R.drawable.square_load_failed)
            .cacheInMemory(true).cacheOnDisc(true).build();
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
        int groupCount = myBooksBean == null ? 0 : myBooksBean.getBookTypeNum();
        LogCat.i("group count:" + groupCount);
        return groupCount;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (myBooksBean.getBookTypeNum() == 1) {
            if (myBooksBean.getOneChildIndex() == 0)
                childCount = myBooksBean.getNormal().size();
            else childCount = myBooksBean.getOverdue().size();
        } else if (groupPosition == 1) {               //过期
            childCount = myBooksBean.getOverdue() == null ? 0 : myBooksBean.getOverdue().size();
        } else {         //未过期。
            childCount = myBooksBean.getNormal() == null ? 0 : myBooksBean.getNormal().size();
        }

        LogCat.i("child count:" + childCount);
        return childCount;
    }


    @Override
    public Object getGroup(int groupPosition) {
        LogCat.i("getGroup index : " + myBooksBean.getOneChildIndex());
        if (myBooksBean.getBookTypeNum() == 1) {    //有一种订单类型。
            if (myBooksBean.getOneChildIndex() == 0)
                return myBooksBean.getNormal();
            else return myBooksBean.getOverdue();
        } else if (groupPosition == 1) {               //过期
            return myBooksBean.getOverdue();
        } else {         //未过期。
            return myBooksBean.getNormal();
        }


    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        if (myBooksBean.getBookTypeNum() == 1) {
            if (myBooksBean.getOneChildIndex() == 0)
                return myBooksBean.getNormal().get(childPosition);
            else return myBooksBean.getOverdue().get(childPosition);
        } else if (groupPosition == 1) {
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
        LogCat.i("getGroupView index : " + myBooksBean.getOneChildIndex() + "　getgroup position : " + groupPosition);
        if (myBooksBean.getBookTypeNum() == 1) {    //有一种订单类型。
            if (groupPosition > 0)          //当extendListView group绘制第二种时。
                viewIds = R.layout.nulllayout;
            else if (myBooksBean.getOneChildIndex() == 0) {
                viewIds = R.layout.mybook_title_pre;
            } else viewIds = R.layout.mybook_title_ed;
        } else if (groupPosition == 0) {
            viewIds = R.layout.mybook_title_pre;
        } else {
            viewIds = R.layout.mybook_title_ed;
        }
        convertView = View.inflate(context, viewIds, null);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder childViewHolder = null;

        LogCat.i("getChildView index : " + myBooksBean.getOneChildIndex() + "　getgroup position : " + groupPosition + " getchildPosition : " + childPosition);
        if (convertView == null || convertView.getTag() == null) {
            childViewHolder = new ChildViewHolder();
            convertView = View.inflate(context, R.layout.mybook_pre_item, null);

            childViewHolder.img = (ImageView) convertView.findViewById(R.id.mybook_item_img);
            childViewHolder.type = (TextView) convertView.findViewById(R.id.mybook_type_tv);
            childViewHolder.people = (TextView) convertView.findViewById(R.id.custome_num_tv);
            childViewHolder.time = (TextView) convertView.findViewById(R.id.mybook_time_tv);
            childViewHolder.numLl = (LinearLayout) convertView.findViewById(R.id.custome_num_ll);

            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        MyBookBean myBookBean;

        if (myBooksBean.getBookTypeNum() == 1) {        //有一种订单类型。
            if (groupPosition > 0)        //当extendListView group绘制第二种时。
                return View.inflate(context, R.layout.nulllayout, null);
            if (myBooksBean.getOneChildIndex() == 1)
                myBookBean = myBooksBean.getOverdue().get(childPosition);
            else myBookBean = myBooksBean.getNormal().get(childPosition);
        } else if (groupPosition == 0) {
            myBookBean = myBooksBean.getNormal().get(childPosition);
        } else {
            myBookBean = myBooksBean.getOverdue().get(childPosition);
        }

        loadImg(myBookBean.getCover(), childViewHolder.img, squareImageOptions, context, myBookBean.getType());
        childViewHolder.type.setText(context.getResources().getStringArray(R.array.book_tab)[myBookBean.getType()]);
        if (myBookBean.getType() == 0) {
            childViewHolder.people.setText(String.valueOf(myBookBean.getPeople_num()));
        } else if (myBookBean.getType() == 1) {
            childViewHolder.numLl.setVisibility(View.GONE);
        } else {
            childViewHolder.numLl.setVisibility(View.GONE);
        }

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
        LinearLayout numLl;
    }


    private void loadImg(final String imgUrl, ImageView imgView, final DisplayImageOptions options, final Context context, final int type) {

        ImageLoader.getInstance().displayImage(imgUrl, imgView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

                if (imgUrl.equals(HttpConstant.ROOT_EMPTY_URL) && type == OrderType.ARRIVE.getValue())
                    if (view instanceof ImageView) {
                        ((ImageView) view).setImageDrawable(options.getImageForEmptyUri(context.getResources()));
                    }
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

                if (imgUrl.equals(HttpConstant.ROOT_EMPTY_URL) && type == OrderType.ARRIVE.getValue())
                    if (view instanceof ImageView) {
                        ((ImageView) view).setImageDrawable(options.getImageForEmptyUri(context.getResources()));
                    }
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                if (imgUrl.equals(HttpConstant.ROOT_EMPTY_URL) && type == OrderType.ARRIVE.getValue())
                    if (view instanceof ImageView) {
                        ((ImageView) view).setImageDrawable(options.getImageForEmptyUri(context.getResources()));
                    }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }
}
