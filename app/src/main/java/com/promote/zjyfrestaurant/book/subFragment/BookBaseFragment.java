package com.promote.zjyfrestaurant.book.subFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jch.lib.util.DisplayUtil;
import com.promote.zjyfrestaurant.MainActivity;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.bean.OrderBean;
import com.promote.zjyfrestaurant.book.ConfirmBookActivity;
import com.promote.zjyfrestaurant.book.bookActivity.BookActivity;
import com.promote.zjyfrestaurant.personal.AddressActivity;
import com.promote.zjyfrestaurant.shoppingcart.ShoppingCart;
import com.promote.zjyfrestaurant.util.ContextUtil;
import com.promote.zjyfrestaurant.view.NumberView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

/**
 * 预约界面的父类，主要用于管理EditText焦点改变时的逻辑处理。
 * Created by ACER on 2014/12/23.
 */
public class BookBaseFragment extends Fragment implements View.OnFocusChangeListener {
    public static final int BOOKRERUEST_CODE = 56;

    /**
     * 用map保持editText和numberView一一对应的关系。
     */
    private Hashtable<EditText, NumberView> inputMap = new Hashtable<>();

    /**
     * 全局时间。 *
     */
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);

    private StringBuilder timeSb = new StringBuilder();

    private boolean dataFlag = false;
    private boolean timeFalg = false;

    private TextView timeEt = null;
    protected OrderBean orderBean = new OrderBean();

    final int DATE = 1;
    final int TIME = 2;

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!(v instanceof EditText))   //只检测edittext 的焦点监听。
            return;
        EditText editText = (EditText) v;
        NumberView numberView = inputMap.get(editText);
        if (hasFocus) {  //获得焦点，背景变蓝
            numberView.setChecked(true);
            DisplayUtil.setBackground(editText, getResources().getDrawable(R.drawable.book_enter_focused));
            InputMethodManager inputManager = ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            inputManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
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

    protected void setInputText(NumberView numberView, View view, String str) {

        if (str != null && !str.equals("")) {
            numberView.setChecked(true);
            if (view instanceof EditText) {
                EditText ev = (EditText) view;
                ev.setText(str);
                DisplayUtil.setBackground(ev, getResources().getDrawable(R.drawable.book_enter_focused));
                ev.setPadding((int) getResources().getDimension(R.dimen.book_draw_pad_left), 0, 0, 0);
            } else if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setText(str);
                DisplayUtil.setBackground(tv, getResources().getDrawable(R.drawable.book_enter_focused));
                tv.setPadding((int) getResources().getDimension(R.dimen.book_draw_pad_left), 0, 0, 0);
            }

        }
    }

    /**
     * 清空view中的text。
     *
     * @param numberView
     * @param view
     */
    protected void clearText(NumberView numberView, View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setText("");
        }
        DisplayUtil.setBackground(view, getResources().getDrawable(R.drawable.book_enter));
        view.setPadding((int) getResources().getDimension(R.dimen.book_draw_pad_left), 0, 0, 0);
        numberView.setChecked(false);
    }

    /**
     * 提交预约。
     */
    protected void submit(OrderBean orderBean) {
        ShoppingCart shoppingCart = ShoppingCart.newInstance();
        orderBean.addProducts(shoppingCart.getOrderDishes());
        orderBean.setPrice(shoppingCart.getTotalOrderPrice());
        Intent intent = new Intent(getActivity(), ConfirmBookActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConfirmBookActivity.ORDER_KEY, orderBean);
        intent.putExtras(bundle);
        if (getParentFragment().getActivity() instanceof BookActivity)      //区分不同的跳转activity。
            intent.putExtra(AddressActivity.FROM_ACIVITY_KEY, AddressActivity.FROM_BOOK_CODE);
        else if (getParentFragment().getActivity() instanceof MainActivity)
            intent.putExtra(AddressActivity.FROM_ACIVITY_KEY, AddressActivity.FROM_MAIN_CODE);
        startActivity(intent);
    }

    protected void selectTime(TextView timeTv) {

        timeEt = timeTv;
        NumberView numberView = (NumberView) timeTv.getTag();
        DisplayUtil.setBackground(timeTv, getResources().getDrawable(R.drawable.book_enter_focused));
        numberView.setChecked(true);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.support.v7.appcompat.R.style.Base_V7_Theme_AppCompat_Dialog, listener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.show();
        datePickerDialog.setOnDismissListener(new DataDismissOCL());
    }

    private DatePickerDialog.OnDateSetListener listener = //用于检测日期设定器的变化
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    calendar.set(Calendar.YEAR, arg1);//重置年份
                    calendar.set(Calendar.MONTH, arg2);//重置月份
                    calendar.set(Calendar.DAY_OF_MONTH, arg3);//重置日期
                }

            };
    private TimePickerDialog.OnTimeSetListener listener2 = //用于检测时间设定器的变化
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);//设置小时
                    calendar.set(Calendar.MINUTE, minute); //调用分钟
                }
            };

    private class DataDismissOCL implements DialogInterface.OnDismissListener {

        @Override
        public void onDismiss(DialogInterface dialog) {
            showTimePick();
        }
    }

    private class TimeDismissOCL implements DialogInterface.OnDismissListener {

        @Override
        public void onDismiss(DialogInterface dialog) {
            update(TIME);//调用更新时间显示
        }
    }

    private void update(int dt) {//case一下是改变日期还是时间
        switch (dt) {
            case DATE:
            case TIME:
                if (timeSb.length() > 0) {      //清空str
                    timeSb.delete(0, timeSb.length());
                }
                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//创建时间格式
                String timeStr = simpleTimeFormat.format(calendar.getTime());//按照最新的calendar更新时间显示
                timeSb.append(" ").append(timeStr);
                if (isBeforeNow(timeSb.toString())) {
                    ContextUtil.toast(getActivity(), getString(R.string.before_now_warn));
                    timeEt.setText("");
                    DisplayUtil.setBackground(timeEt, getResources().getDrawable(R.drawable.book_enter));
                    NumberView numberView = (NumberView) timeEt.getTag();
                    numberView.setChecked(false);
                } else {
                    timeEt.setText(timeSb.toString());
                }
                timeEt.setPadding((int) getResources().getDimension(R.dimen.book_draw_pad_left), 0, 0, 0);
                break;
            default:
                break;
        }
    }

    private void showTimePick() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), listener2,
                Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);//调用对话框的TimerPicker
        timePickerDialog.setCanceledOnTouchOutside(false);
        timePickerDialog.show();
        timePickerDialog.setOnDismissListener(new TimeDismissOCL());
    }

    /**
     * 检测是否在现在时间之前。
     *
     * @param timeStr
     * @return
     */
    private boolean isBeforeNow(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = null;
        try {
            date = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long time = date.getTime();     //推迟半个小时。
        date.setTime(time);

        Date nowDate = new Date();
        return date.before(nowDate);
    }

}
