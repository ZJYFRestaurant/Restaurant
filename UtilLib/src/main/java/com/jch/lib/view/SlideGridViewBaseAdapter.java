package com.jch.lib.view;

import android.view.View;
import android.widget.BaseAdapter;

/**
 * Created by ACER on 2014/11/10.
 */
public abstract class SlideGridViewBaseAdapter extends BaseAdapter {

    /**
     * 获取子分类的view。
     *
     * @param itemPosition 父分级的序列号.
     * @return 子分类的view。
     */
    public abstract View getSubItemView(int itemPosition, int subPosition);

    /**
     * 获取position父分类下的子分类的数量。
     *
     * @param itemPosition
     * @return
     */
    public abstract int getSubItemCountByPosition(int itemPosition);


}
