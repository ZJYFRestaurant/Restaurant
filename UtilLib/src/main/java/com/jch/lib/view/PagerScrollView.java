package com.jch.lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by jch on 2014/8/22.
 * <p/>
 * This scrollView can contained viewpager.
 */
public class PagerScrollView extends ScrollView {

    private GestureDetector mGestureDetector;

    public PagerScrollView(Context context) {
        super(context);
        init();
    }


    public PagerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {

        mGestureDetector = new GestureDetector(getContext(),
                new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean onTouchResult = false;

        try {
            onTouchResult = mGestureDetector.onTouchEvent(ev);
        } catch (Exception e) {

        }

        return super.onInterceptTouchEvent(ev)
                && onTouchResult;
    }

    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {

            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }
}
