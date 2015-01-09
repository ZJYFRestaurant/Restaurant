package com.jch.lib.view.TurnPage;

import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ACER on 2015/1/8.
 */
public class TurnPageManager extends DataSetObserver implements View.OnTouchListener {

    private Point size = new Point();
    /**
     * pageWidget. *
     */
    private PageWidget pageWidget;

    private TurnPageAdapter adapter;

    private Bitmap mCurPageBitmap, mNextPageBitmap, mDefaultBitmap;
    private Canvas mCurPageCanvas, mNextPageCanvas;

    private int pageIndex = 0;

    @Override
    public void onChanged() {
        super.onChanged();


    }

    @Override
    public void onInvalidated() {
        super.onInvalidated();
    }

    public TurnPageManager(PageWidget widget) {
        this.pageWidget = widget;

    }

    public void setPageWidget(PageWidget widget) {
        this.pageWidget = widget;
        if (this.pageWidget != null) {
            this.pageWidget.setOnTouchListener(this);
        }

    }

    public void setAapter(TurnPageAdapter adapter) {
        this.adapter = adapter;
        adapter.registerObserver(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent e) {
        boolean ret = false;
        if (v == pageWidget) {
            if (e.getAction() == MotionEvent.ACTION_DOWN && adapter != null) {
                pageWidget.abortAnimation();
                pageWidget.calcCornerXY(e.getX(), e.getY());
                if (pageWidget.DragToRight()) {
                    if (pageIndex > 0) {
                        getPrePagePage(pageIndex);
                        pageIndex--;
                    } else
                        return pageWidget.doTouchEvent(e);
                } else {

                    if (pageIndex < adapter.getCount() - 1) {
                        getNextPage(pageIndex);
                        pageIndex++;
                    } else
                        return pageWidget.doTouchEvent(e);
                }

                pageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
            }

            ret = pageWidget.doTouchEvent(e);
            return ret;
        }
        return false;
    }


    /**
     * get current and next bitmap.
     *
     * @param index
     */
    private void getNextPage(int index) {

        if (adapter == null)
            return;
        adapter.instantiateItem(pageWidget, index, new BitmapCallback(mCurPageCanvas));
        if (adapter.getCount() > index)
            adapter.instantiateItem(pageWidget, index + 1, new BitmapCallback(mNextPageCanvas));
    }

    /**
     * get current and preview bitmap.
     *
     * @param index
     */
    private void getPrePagePage(int index) {

        adapter.instantiateItem(pageWidget, index, new BitmapCallback(mCurPageCanvas));
        if (index > 0) {
            adapter.instantiateItem(pageWidget, index - 1, new BitmapCallback(mNextPageCanvas));
        }
    }

    /**
     * loading pager callback.
     */
    private class BitmapCallback implements PageLoadCallback {
        /**
         * track unique canvase.
         */
        private Canvas canvas;

        public BitmapCallback(Canvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void onLoadStart(Bitmap loadingBtp) {
            getBitmap(canvas, loadingBtp);
        }

        @Override
        public void onLoadComplited(Bitmap overBtp) {
            getBitmap(canvas, overBtp);
        }

        @Override
        public void onLoadFailed(Bitmap failedBtp) {
            getBitmap(canvas, failedBtp);
        }
    }

    /**
     * @param canvas
     * @param srcBitmap
     */
    private void getBitmap(Canvas canvas, Bitmap srcBitmap) {
        Rect srcRect = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
        Rect dstRect = new Rect(0, 0, size.x, size.y);
        canvas.drawBitmap(srcBitmap, srcRect, dstRect, null);
    }

    public Point getSize() {
        return size;
    }

    public void setSize(Point size) {
        this.size = size;

    }

    /**
     * 初始化控制bitmap.
     *
     * @param size
     */
    public void init(Point size, Bitmap defaultBmp) {
        this.size = size;

        this.mDefaultBitmap = defaultBmp;
        mCurPageBitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap
                .createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);

        mCurPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);

        getBitmap(mCurPageCanvas, defaultBmp);
        getBitmap(mNextPageCanvas, defaultBmp);

        pageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
        pageWidget.setOnTouchListener(this);
    }

}