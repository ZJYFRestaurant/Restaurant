package com.jch.lib.view.TurnPage;

import android.database.DataSetObserver;

/**
 * Created by ACER on 2015/1/8.
 */
public class TurnPageManager extends DataSetObserver {
    /**
     * pageWidget. *
     */
    private PageWidget pageWidget;

    private TurnPageAdapter adapter;

    @Override
    public void onChanged() {
        super.onChanged();
    }

    public void setPageWidget(PageWidget widget) {
        this.pageWidget = widget;
    }

    public void setAapter(TurnPageAdapter adapter) {
        this.adapter = adapter;
    }


}
