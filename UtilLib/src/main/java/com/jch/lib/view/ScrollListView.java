package com.jch.lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by jch on 2014/8/25.
 * <p/>
 * 適用于嵌套到scrollView中
 */
public class ScrollListView extends ListView {

    private static final String TAG = "SCROLLLISTVIEW";
    private ScrollView mParentScrollView;

    public ScrollListView(Context context) {
        super(context);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


    public void setParentScrollView(ScrollView parentScrollView) {
        this.mParentScrollView = parentScrollView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch
                (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                setParentScrollAble(true);//当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview 停住不能滚动
                Log.d(TAG, "onInterceptTouchEvent down");
            case MotionEvent.ACTION_MOVE:

                Log.d(TAG, "onInterceptTouchEvent move");
                break;

            case MotionEvent.ACTION_UP:

                Log.d(TAG, "onInterceptTouchEvent up");
            case MotionEvent.ACTION_CANCEL:

                Log.d(TAG, "onInterceptTouchEvent cancel");

                setParentScrollAble(true);//当手指松开时，让父ScrollView重新拿到onTouch权限

                break;
            default:
                break;

        }
        return super.onInterceptTouchEvent(ev);

    }


    /**
     * 是否把滚动事件交给父scrollview
     *
     * @param flag
     */
    private void setParentScrollAble(boolean flag) {

//        mParentScrollView.requestDisallowInterceptTouchEvent(!flag);//这里的parentScrollView就是listview外面的那个scrollview


    }

}
