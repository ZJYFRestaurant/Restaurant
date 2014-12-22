package com.promo.zjyfrestaurant.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jch.lib.util.DisplayUtil;
import com.promo.zjyfrestaurant.R;

/**
 * TODO: document your custom view class.
 */
public class NumberView extends LinearLayout {

    private static final int DEF_BG = Color.GRAY;
    private static final int DEF_TVCOLOR = Color.WHITE;
    private static final float DEF_TVSIZE = 22;
    private View mContentView = null;
    private View mTopLine = null;
    private View mBtmLine = null;
    private TextView numTv = null;

    private Drawable mTvBg;
    private Drawable mTvCurBg;
    private float mTvSize;
    private int mTvColor;
    private int mTvCurColor;
    private int mLineColor;
    private int mCurLineColor;
    private String mTvStr;
    /**
     * 当前图标。
     */
    private boolean curflag = false;


    public NumberView(Context context) {
        super(context);
        init(null, 0);

    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

    }


    private void init(AttributeSet attrs, int defStyle) {

        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(VERTICAL);

        mLineColor = DEF_BG;
        mCurLineColor = DEF_BG;
        mTvSize = DEF_TVSIZE;
        mTvColor = DEF_TVCOLOR;
        mTvCurColor = DEF_TVCOLOR;

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.NumberView, defStyle, 0);

        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {

                case R.styleable.NumberView_num_line_color: {
                    mLineColor = a.getColor(attr, DEF_BG);
                    break;
                }
                case R.styleable.NumberView_num_line_cur_color: {
                    mCurLineColor = a.getColor(attr, DEF_BG);
                    break;
                }
                case R.styleable.NumberView_num_tv_bg: {
                    mTvBg = a.getDrawable(attr);
                    break;
                }
                case R.styleable.NumberView_num_tv_cur_bg: {
                    mTvCurBg = a.getDrawable(attr);
                    break;
                }
                case R.styleable.NumberView_num_tv_text_color: {
                    mTvColor = a.getColor(attr, DEF_TVCOLOR);
                    break;
                }
                case R.styleable.NumberView_num_tv_text_cur_color: {
                    mTvCurColor = a.getColor(attr, DEF_TVCOLOR);
                    break;
                }
                case R.styleable.NumberView_num_tv_text_size: {
                    mTvSize = a.getDimension(attr, DEF_TVSIZE);
                    break;
                }

                case R.styleable.NumberView_num_tv_text: {
                    mTvStr = a.getString(attr);
                    break;
                }

                case R.styleable.NumberView_num_tv_cur: {
                    curflag = a.getBoolean(attr, false);
                    break;
                }

            }
        }

        a.recycle();
        mContentView = View.inflate(getContext(), R.layout.number_view_layout, null);
        mTopLine = mContentView.findViewById(R.id.top_line);
        mBtmLine = mContentView.findViewById(R.id.btm_line);
        initView();
        if (mTvStr != null) {
            numTv.setText(mTvStr);
        }
        addView(mContentView);

    }

    private void initView() {
        numTv = (TextView) mContentView.findViewById(R.id.num_tv);
        numTv.setTextSize(mTvSize);

        if (curflag) {
            mTopLine.setBackgroundColor(mCurLineColor);
            mBtmLine.setBackgroundColor(mCurLineColor);
            numTv.setTextColor(mTvCurColor);
            DisplayUtil.setBackground(numTv, mTvCurBg);
        } else {
            mTopLine.setBackgroundColor(mLineColor);
            mBtmLine.setBackgroundColor(mLineColor);
            numTv.setTextColor(mTvColor);
            DisplayUtil.setBackground(numTv, mTvBg);
        }
    }

    /**
     * 设置序号序列。
     *
     * @param num
     */
    public void setTextNum(String num) {

        if (num != null) {
            numTv.setText(num);
        }
    }

    public void setChecked(boolean checked) {

        curflag = checked;
        initView();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);


    }
}
