package com.jch.lib.view.TurnPage;

import android.database.DataSetObservable;
import android.view.View;

/**
 * 翻页效果adapter.
 * <p/>
 * Created by ACER on 2015/1/8.
 */
public abstract class TurnPageAdapter extends DataSetObservable {


    public TurnPageAdapter() {

    }

    /**
     * 初始化每一项。
     *
     * @param container
     * @param position
     * @param callback  异步加载更回调。
     */
    public abstract void instantiateItem(View container, int position, PageLoadCallback callback);

    /**
     * 获得所要加载的数量。
     *
     * @return
     */
    public abstract int getCount();

    /**
     * 消除指定向。
     *
     * @param container
     * @param position
     */
    public abstract void destroyItem(View container, int position);


}
