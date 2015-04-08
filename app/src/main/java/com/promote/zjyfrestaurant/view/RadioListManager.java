package com.promote.zjyfrestaurant.view;

import android.database.DataSetObserver;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by ACER on 2014/12/29.
 */
public class RadioListManager extends DataSetObserver {

    private BaseAdapter adapter;
    private RadioGroup radioGroup;

    public RadioListManager(BaseAdapter adapter, RadioGroup group) {

        this.adapter = adapter;
        this.radioGroup = group;

    }

    @Override
    public void onChanged() {
        super.onChanged();
        addView();
    }

    private void addView() {

        for (int i = 0; i < adapter.getCount(); i++) {

            View view = adapter.getView(i, null, null);
            view.setId(i);      //将序号设置为id.
            radioGroup.addView(view);
        }
        checkBtn(0);    //第一个Button被选中。
    }

    /**
     * @param index
     */
    public void checkBtn(int index) {

        if (index < adapter.getCount()) {

            ((RadioButton) radioGroup.getChildAt(index)).setChecked(true);

        }

    }


}
