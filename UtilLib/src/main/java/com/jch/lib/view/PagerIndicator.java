package com.jch.lib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.jch.lib.R;

@SuppressLint("DrawAllocation")
public class PagerIndicator extends View {

    private int mCurrentPage = -1;
    private int mTotalPage = 0;
    private int mDefaultResid;
    private int mCurrentResid;
    private Drawable mDefaultDrawabl;
    private Drawable mCurDrawble;

    public PagerIndicator(Context context) {
        super(context);
        init(null, 0);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {

        super(context, attrs);
        init(attrs, 0);
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.pagerindicator, defStyle, 0);
        if (array.hasValue(R.styleable.pagerindicator_nomal_icon)) {
            mDefaultDrawabl = array.getDrawable(R.styleable.pagerindicator_nomal_icon);
            mDefaultDrawabl.setCallback(this);
        }

        if (array.hasValue(R.styleable.pagerindicator_cur_icon)) {

            mCurDrawble = array.getDrawable(R.styleable.pagerindicator_cur_icon);
            mCurDrawble.setCallback(this);
        }

        array.recycle();
    }

    public void setTotalPage(int nPageNum) {
        mTotalPage = nPageNum;
        if (mCurrentPage >= mTotalPage)
            mCurrentPage = mTotalPage - 1;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int nPageIndex) {
        if (nPageIndex < 0 || nPageIndex >= mTotalPage)
            return;

        if (mCurrentPage != nPageIndex) {
            mCurrentPage = nPageIndex;
            this.invalidate();
        }
    }

    public void setDefaultResource(int defaultResid) {
        mDefaultResid = defaultResid;
    }

    public void setCurrentResource(int currentResid) {
        mCurrentResid = currentResid;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        Rect r = new Rect();
        this.getDrawingRect(r);

        int iconWidth = 9;
        int iconHeight = 9;
        int space = 12;

        int x = (r.width() - (iconWidth * mTotalPage + space * (mTotalPage - 1))) / 2;
        // int y = (r.height() - iconHeight) / 2;
        for (int i = 0; i < mTotalPage; i++) {

            // int resid = mDefaultResid;
            //
            // if (i == mCurrentPage) {
            // resid = mCurrentResid;
            // }

            Drawable pointDrawable = mDefaultDrawabl;
            if (i == mCurrentPage) {
                pointDrawable = mCurDrawble;
            }

            Rect r1 = new Rect();
            r1.left = x;
            r1.top = 15;
            r1.right = x + iconWidth;
            r1.bottom = 15 + iconHeight;

            // Bitmap bmp = BitmapFactory.decodeResource(getResources(), resid);
            // canvas.drawBitmap(bmp, null, r1, paint);

            pointDrawable.setBounds(r1);
            pointDrawable.draw(canvas);

            x += iconWidth + space;

        }

    }

}
