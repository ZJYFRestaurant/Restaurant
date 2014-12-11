package com.jch.lib.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

/**
 * 根据屏幕比例，计算控件显示大小。
 *
 * @author jch.
 */
public class DisplayUtil {


    @SuppressLint("NewApi")
    public static void getSize(Display display, Point outSize) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            display.getSize(outSize);
        } else {
            outSize.x = display.getWidth();
            outSize.y = display.getHeight();
        }
    }

    public static int getCentWidthByView(View view) {

        final int mMeasuredWidth = View.MeasureSpec.getSize(view.getMeasuredWidth());

        Log.i("width", "mywidth--" + mMeasuredWidth);

        final int mWidth = View.MeasureSpec.getSize(view.getWidth());

        Log.i("width", "mywidth--" + mWidth);

        return (int) ((float) mWidth / 2 + 0.5f);

    }

    /**
     * @param wm
     * @param baseY
     * @param baseX
     * @return
     */
    public static float sizeYByX(WindowManager wm, float baseY, float baseX) {

        final Display disp = wm.getDefaultDisplay();
        Point mPoint = new Point();
        getSize(disp, mPoint);
        float displayWidth = mPoint.x;

        float scaleX = displayWidth / baseX;

        return baseY * scaleX + 0.5f;
    }

    /**
     * @param windowManager
     * @param baseY
     * @param basex
     * @param exceptDis     去除的宽度。
     * @return
     */
    public static float sizeYByX(WindowManager windowManager, float baseY, float basex, int exceptDis) {

        final Display display = windowManager.getDefaultDisplay();
        Point mPoint = new Point();
        getSize(display, mPoint);
        if (exceptDis > mPoint.x) {
            return 0.0f;
        }

        float displayWidth = mPoint.x - exceptDis;
        float scaleX = displayWidth / basex;

        return baseY * scaleX;

    }

    /**
     * 获得去除的两边留白后的宽度.
     *
     * @param windowManager
     * @param baseY
     * @param basex
     * @param exceptDis
     * @return
     */
    public static float getScaledWidth(WindowManager windowManager, float baseY, float basex, int exceptDis) {

        final Display display = windowManager.getDefaultDisplay();
        Point mPoint = new Point();
        getSize(display, mPoint);
        if (exceptDis > mPoint.x) {
            return 0.0f;
        }

        return mPoint.x - exceptDis;
    }

    /**
     * 根据基本组件的宽度计算组件的放缩比例。
     *
     * @param windowManager
     * @param basex         组件的原始宽。
     * @param exceptDis     组件外留空间。
     * @return
     */
    public static float getScaledByWidth(WindowManager windowManager, float basex, int exceptDis) {

        final Display display = windowManager.getDefaultDisplay();
        Point mPoint = new Point();
        getSize(display, mPoint);
        return (float) (mPoint.x - exceptDis) / basex;

    }

    /**
     * 根据屏幕宽度设置view的大小.(去除view的margin。
     *
     * @param view       要改變尺寸的view。
     * @param baseWidth  viwe的基准宽.
     * @param baseHeight view的基准高。
     */
    public static void reSizeViewByWidth(View view, int baseWidth, int baseHeight, Activity activity) {

        LinearLayout.LayoutParams pagerParams = (LinearLayout.LayoutParams) view
                .getLayoutParams();

        int marginDis = pagerParams.leftMargin + pagerParams.rightMargin;
        pagerParams.height = (int) DisplayUtil.sizeYByX(activity
                .getWindowManager(), baseHeight, baseWidth, marginDis);
        pagerParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(pagerParams);
    }

    /**
     * 根据屏幕宽度设置view的大小,(出去给定的widht)
     *
     * @param view
     * @param baseWidth
     * @param baseHeight
     * @param exceptWidth
     * @param activity
     */
    public static void resizeViewByScreenWidth(View view, int baseWidth, int baseHeight, int exceptWidth, Activity activity) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view
                .getLayoutParams();
        params.height = (int) DisplayUtil.sizeYByX(activity
                .getWindowManager(), baseHeight, baseWidth, exceptWidth);
        params.width = (int) getScaledWidth(activity
                .getWindowManager(), baseHeight, baseWidth, exceptWidth);
        view.setLayoutParams(params);
    }

    /**
     * 根据屏幕宽度设置view的大小,(出去给定的widht)
     *
     * @param view
     * @param baseWidth
     * @param baseHeight
     * @param exceptWidth
     * @param activity
     */
    public static void resizeViewByScreenWidth(View view, int baseWidth, int baseHeight, int exceptWidth, Activity activity, int viewNum) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view
                .getLayoutParams();
        params.height = (int) DisplayUtil.sizeYByX(activity
                .getWindowManager(), baseHeight, baseWidth, exceptWidth) / viewNum;
        params.width = (int) getScaledWidth(activity
                .getWindowManager(), baseHeight, baseWidth, exceptWidth) / viewNum;
        view.setLayoutParams(params);
    }

    /**
     * 根据屏幕宽度设置view的大小.
     *
     * @param view       要改變尺寸的view。
     * @param baseWidth  viwe的基准宽.
     * @param baseHeight view的基准高。
     */
    public static void reSizeViewByScreenWidth(View view, int baseWidth, int baseHeight, Activity activity) {

        ViewGroup.LayoutParams pagerParams = (ViewGroup.LayoutParams) view
                .getLayoutParams();

        pagerParams.height = (int) DisplayUtil.sizeYByX(activity
                .getWindowManager(), baseHeight, baseWidth);
        pagerParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(pagerParams);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 隐藏键盘.
     *
     * @param context Activity
     * @param v       EditText
     */
    public static void hideSystemKeyBoard(Activity context, View v) {

        //1、对隐藏软键盘有用的函数为：
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * @param wm
     * @param baseY 基准height.
     * @param baseX 基准width.
     */
    private void calcuateDisplayScale(WindowManager wm, float baseY, float baseX) {
        final Display disp = wm.getDefaultDisplay();
        Point mPoint = new Point();
        getSize(disp, mPoint);
        float displayWidth = mPoint.x;
        float displayHeight = mPoint.y;
    }

}
