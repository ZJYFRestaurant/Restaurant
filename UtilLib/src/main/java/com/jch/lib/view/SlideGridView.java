package com.jch.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.jch.lib.R;

/**
 * Created by ACER on 2014/11/10.
 * <p/>
 * 滚动九宫格。
 */
public class SlideGridView extends LinearLayout {

    private static final int DEFAUTGRAYCOLOR = 0xcccccc;
    private static final int DEFULTSUBCOLOR = 0x72a2f2;
    private static final int DEFAUTDIVIDEHEIGHT = 0;
    private static final int DEFULTCOLUM = 3;
    // @formatter:off
    private static final int[] ATTRS = new int[]{android.R.attr.textSize, android.R.attr.textColor};
    private OnItemClickListener itemClickListener;
    //每一行的列数.
    private int colulmeNum = 3;
    //子分类中每行的列数。
    private int subColumNum = 3;
    //子分类的背景
    private int subBg;
    private float divideHeight;
    private int divideColor;
    private SlideGridViewBaseAdapter mAdapter = null;
    private SlideGridViewController mController = null;
    private int arroIndexIds;
    private int arroIndexIdsNomal;
    private int DEFAUTE_BG = R.drawable.slidgrid_index;

    public SlideGridView(Context context) {
        super(context);
        init(context);
    }

    public SlideGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideGridView, 0, 0);
        colulmeNum = a.getInteger(R.styleable.SlideGridView_columNum, DEFULTCOLUM);
        subBg = a.getColor(R.styleable.SlideGridView_subViewBg, DEFULTSUBCOLOR);
        subColumNum = a.getInteger(R.styleable.SlideGridView_subColumNum, DEFULTCOLUM);
        divideHeight = a.getDimension(R.styleable.SlideGridView_divideHeight, DEFAUTDIVIDEHEIGHT);
        divideColor = a.getColor(R.styleable.SlideGridView_divideColor, DEFAUTGRAYCOLOR);
        arroIndexIds = a.getResourceId(R.styleable.SlideGridView_checked_index, DEFAUTE_BG);
        arroIndexIdsNomal = a.getResourceId(R.styleable.SlideGridView_checked_namal, DEFAUTE_BG);

        a.recycle();
        init(context);
    }

    public int getDivideColor() {
        return divideColor;
    }

    public void setDivideColor(int divideColor) {
        this.divideColor = divideColor;
    }

    public float getDivideHeight() {
        return divideHeight;
    }

    public void setDivideHeight(float divideHeight) {
        this.divideHeight = divideHeight;
    }

    public int getArroIndexIdsNomal() {
        return arroIndexIdsNomal;
    }

    public void setArroIndexIdsNomal(int arroIndexIdsNomal) {
        this.arroIndexIdsNomal = arroIndexIdsNomal;
    }

    public int getArroIndexIds() {
        return arroIndexIds;
    }

    // @formatter:on

    public void setArroIndexIds(int arroIndexIds) {
        this.arroIndexIds = arroIndexIds;
    }

    public void init(Context context) {
        mController = new SlideGridViewController(context, this);
        setOrientation(VERTICAL);
    }

    public int getColulmeNum() {
        return colulmeNum;
    }

    public int getSubBg() {
        return subBg;
    }

    public int getSubColumNum() {
        return subColumNum;
    }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SlideGridViewBaseAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(SlideGridViewBaseAdapter adapter) {
        this.mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mController);
        }
    }

    public interface OnItemClickListener {
        /**
         * 父分类点击事件.
         *
         * @param position
         * @param view
         */
        public void onItemClick(int position, View view);

        /**
         * 子分类点击事件.
         *
         * @param subPosition
         * @param view
         */
        public void onSubItemClick(int position, int subPosition, View view);
    }
}
