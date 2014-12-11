package com.promo.zjyfrestaurant.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.promo.zjyfrestaurant.R;

/**
 * TODO: document your custom view class.
 */
public class HomeStartView extends View {

    private static final int START_NUM = 5;
    private static final float START_PAD = 10;
    private static float mStartWidth = 0;
    private static float mStartHeight = 0;
    private static float PAD = 0;

    private Drawable mStartDrawable;
    private float mStartPadding;      //the destance between starts.
    private int mStartNum;      //start num.

    public HomeStartView(Context context) {
        super(context);
        init(null, 0);
    }

    public HomeStartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HomeStartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.HomeStartView, defStyle, 0);


        mStartNum = a.getInt(R.styleable.HomeStartView_start_num, START_NUM);
        mStartPadding = a.getDimension(R.styleable.HomeStartView_start_pading, START_PAD);

        if (a.hasValue(R.styleable.HomeStartView_start_drawable)) {
            mStartDrawable = a.getDrawable(
                    R.styleable.HomeStartView_start_drawable);
            mStartDrawable.setCallback(this);
        }


        a.recycle();


    }

    public Drawable getStartDrawable() {
        return mStartDrawable;
    }

    public void setStartDrawable(Drawable mStartDrawable) {
        this.mStartDrawable = mStartDrawable;
        this.invalidate();
    }

    public float getStartPadding() {
        return mStartPadding;
    }

    public void setStartPadding(float mStartPadding) {
        this.mStartPadding = mStartPadding;
        this.invalidate();
    }

    public int getmStartNum() {
        return mStartNum;
    }

    public void setStartNum(int mStartNum) {
        this.mStartNum = mStartNum;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Rect rect = new Rect();
//        this.getDrawingRect(rect);

        if (mStartDrawable != null) {

            BitmapDrawable bd = (BitmapDrawable) mStartDrawable;
            Bitmap bitmap = bd.getBitmap();
            mStartHeight = bitmap.getHeight();
            mStartWidth = bitmap.getWidth();
        }
        //计算起始位置。
//        int x = (int) ((rect.width() - (mStartWidth * mStartNum + mStartPadding * (mStartNum - 1))) / 2);
        int x = 0;
        for (int i = 0; i < mStartNum; i++) {

            Rect r1 = new Rect();
            r1.left = x;
            r1.top = getPaddingTop();
            r1.right = (int) (x + mStartWidth);
            r1.bottom = (int) (getPaddingTop() + mStartHeight);

            mStartDrawable.setBounds(r1);
            mStartDrawable.draw(canvas);

            x += mStartWidth + mStartPadding;
        }


    }

}
