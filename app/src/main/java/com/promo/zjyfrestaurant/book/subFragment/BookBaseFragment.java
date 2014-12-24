package com.promo.zjyfrestaurant.book.subFragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import com.jch.lib.util.DisplayUtil;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.book.ConfirmBookActivity;
import com.promo.zjyfrestaurant.view.NumberView;

import java.util.Hashtable;

/**
 * 预约界面的父类，主要用于管理EditText焦点改变时的逻辑处理。
 * Created by ACER on 2014/12/23.
 */
public class BookBaseFragment extends Fragment implements View.OnFocusChangeListener {
    /**
     * 用map保持editText和numberView一一对应的关系。
     */
    private Hashtable<EditText, NumberView> inputMap = new Hashtable<>();

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        EditText editText = (EditText) v;
        NumberView numberView = inputMap.get(editText);
        if (hasFocus) {  //获得焦点，背景变蓝
            numberView.setChecked(true);
            DisplayUtil.setBackground(editText, getResources().getDrawable(R.drawable.book_enter_focused));
        } else { //失去焦点时，如果edit中有数据背景变蓝，没有数据背景变灰
            String text = editText.getText().toString();
            if (text != null && !text.equals("")) {
                numberView.setChecked(true);
                DisplayUtil.setBackground(editText, getResources().getDrawable(R.drawable.book_enter_focused));
            } else {
                numberView.setChecked(false);
                DisplayUtil.setBackground(editText, getResources().getDrawable(R.drawable.book_enter));
            }
        }
        editText.setPadding((int) getResources().getDimension(R.dimen.book_draw_pad_left), 0, 0, 0);
    }

    /**
     * 将输入框和前面的序号添加监听。
     *
     * @param numberView
     * @param et
     */
    protected void setInputFocusChange(NumberView numberView, EditText et) {
        inputMap.put(et, numberView);
        et.setOnFocusChangeListener(this);
    }

    /**
     * 提交预约。
     */
    protected void submit() {
        Intent intent = new Intent(getActivity(), ConfirmBookActivity.class);
        startActivity(intent);
    }

}
