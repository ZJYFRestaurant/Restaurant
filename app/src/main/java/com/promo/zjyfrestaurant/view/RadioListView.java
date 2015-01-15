package com.promo.zjyfrestaurant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;

/**
 * Created by ACER on 2014/12/26.
 */
public class RadioListView extends ScrollView {

    private RadioGroup mRadioGroup;


    private RadioGroup.OnCheckedChangeListener checkedChangeListener;

    private RadioListManager radioListManager;

    private BaseAdapter adapter;

    public RadioListView(Context context) {
        super(context);
        init();
    }

    public RadioListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadioListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public BaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        radioListManager = new RadioListManager(adapter, mRadioGroup);
        this.adapter.registerDataSetObserver(radioListManager);
    }

    /**
     * 初始化。
     */
    private void init() {

        mRadioGroup = new RadioGroup(getContext());
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRadioGroup.setOrientation(LinearLayout.VERTICAL);
        mRadioGroup.setGravity(Gravity.LEFT);
        mRadioGroup.setLayoutParams(params);

        addView(mRadioGroup);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public RadioGroup.OnCheckedChangeListener getCheckedChangeListener() {
        return checkedChangeListener;
    }

    public void setCheckedChangeListener(RadioGroup.OnCheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
        mRadioGroup.setOnCheckedChangeListener(checkedChangeListener);

    }

}
