package com.promote.zjyfrestaurant.book.subFragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jch.lib.util.DisplayUtil;
import com.jch.lib.util.TextUtil;
import com.jch.lib.util.VaildUtil;
import com.promote.zjyfrestaurant.MainActivity;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.bean.AddressBean;
import com.promote.zjyfrestaurant.bean.OrderType;
import com.promote.zjyfrestaurant.book.bookActivity.BookActivity;
import com.promote.zjyfrestaurant.personal.AddressActivity;
import com.promote.zjyfrestaurant.util.ContextUtil;
import com.promote.zjyfrestaurant.view.NumberView;

/**
 * 外卖服务。
 */
public class ToRestFragment extends BookBaseFragment implements View.OnClickListener {

    public static final int REQ_CODE = 99;
    public static final String ADDR_KEY = "address";

    private NumberView bookcontactnum;
    private EditText bookcontactet;
    private TableRow bookcontacttr;
    private NumberView bookphonenum;
    private EditText bookphoneet;
    private TableRow bookphonetr;
    private NumberView bookaddrnum;
    private ImageView bookaddressicon;
    private ImageButton bookseladdressbtn;
    private EditText bookaddrtv;
    private RelativeLayout booaddrrl;
    private NumberView booktimenum;
    private TextView booktimeet;
    private NumberView bookothernum;
    private EditText bookotheret;
    private Button booksubmitbtn;

    private AddressBean address;

    private View containerView;
    private static ToRestFragment fragment = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ToRestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToRestFragment newInstance() {
        fragment = new ToRestFragment();
        return fragment;
    }

    public ToRestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        containerView = inflater.inflate(R.layout.fragment_to_rest, container, false);
        initialize(containerView);
        return containerView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * @param containerView
     */
    private void initialize(View containerView) {

        bookcontactnum = (NumberView) containerView.findViewById(R.id.book_contact_num);
        bookcontactet = (EditText) containerView.findViewById(R.id.book_contact_et);
        bookcontacttr = (TableRow) containerView.findViewById(R.id.book_contact_tr);
        bookphonenum = (NumberView) containerView.findViewById(R.id.book_phone_num);
        bookphoneet = (EditText) containerView.findViewById(R.id.book_phone_et);
        bookphonetr = (TableRow) containerView.findViewById(R.id.book_phone_tr);
        bookaddrnum = (NumberView) containerView.findViewById(R.id.book_addr_num);
        bookaddressicon = (ImageView) containerView.findViewById(R.id.book_address_icon);
        bookseladdressbtn = (ImageButton) containerView.findViewById(R.id.book_sel_address_btn);
        bookaddrtv = (EditText) containerView.findViewById(R.id.book_addr_tv);
        booaddrrl = (RelativeLayout) containerView.findViewById(R.id.boo_addr_rl);
        booktimenum = (NumberView) containerView.findViewById(R.id.book_time_num);
        booktimeet = (TextView) containerView.findViewById(R.id.book_time_et);
        bookothernum = (NumberView) containerView.findViewById(R.id.book_other_num);
        bookotheret = (EditText) containerView.findViewById(R.id.book_other_et);
        booksubmitbtn = (Button) containerView.findViewById(R.id.book_submit_btn);

        booktimeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booktimeet.setTag(booktimenum);
                selectTime(booktimeet);
            }
        });

        setInputFocusChange(bookothernum, bookotheret);
//        setInputFocusChange(bookaddrnum, booaddrrl);
        setInputFocusChange(bookcontactnum, bookcontactet);
        setInputFocusChange(bookphonenum, bookphoneet);

        if (address != null) {
            onAddrSelected(address);
        }

        bookseladdressbtn.setOnClickListener(this);
        booksubmitbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.book_sel_address_btn: {

                Intent intent = new Intent(getActivity(), AddressActivity.class);
                intent.putExtra(AddressActivity.ITEM_PRESS_KEY, AddressActivity.SEL_ITEM);

                if (getParentFragment().getActivity() instanceof BookActivity)      //区分不同的跳转activity。
                    intent.putExtra(AddressActivity.FROM_ACIVITY_KEY, AddressActivity.FROM_BOOK_CODE);
                else if (getParentFragment().getActivity() instanceof MainActivity)
                    intent.putExtra(AddressActivity.FROM_ACIVITY_KEY, AddressActivity.FROM_MAIN_CODE);
                getParentFragment().getActivity().startActivityForResult(intent, REQ_CODE);

                break;
            }

            case R.id.book_submit_btn: {

                String checkResult = checkOrder();
                if (!TextUtil.stringIsNull(checkResult)) {
                    ContextUtil.toast(getActivity().getApplicationContext(), checkResult);
                    return;
                }
                orderBean.setType(OrderType.SEND);
                submit(orderBean);
                break;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK)
            if (requestCode == REQ_CODE) {
                address = data.getParcelableExtra(ADDR_KEY);
                onAddrSelected(address);
            }

    }


    /**
     * 地址被选择后，显示联系人姓名，联系人电话。设置内容。
     */
    private void onAddrSelected(AddressBean addresss) {

        bookcontacttr.setVisibility(View.VISIBLE);
        bookphonetr.setVisibility(View.VISIBLE);
        bookaddrnum.setTextNum("3");
        bookcontactnum.setChecked(true);
        bookaddrnum.setChecked(true);
        bookphonenum.setChecked(true);
        booktimenum.setTextNum("4");
        bookothernum.setTextNum("5");

        bookcontactet.setText(addresss.getContact());
        DisplayUtil.setBackground(bookcontactet, getResources().getDrawable(R.drawable.book_enter_focused));
        bookphoneet.setText(addresss.getTel());
        DisplayUtil.setBackground(bookphoneet, getResources().getDrawable(R.drawable.book_enter_focused));

        bookaddrtv.setText(addresss.getContent());
        DisplayUtil.setBackground(booaddrrl, getResources().getDrawable(R.drawable.book_enter_focused));
        bookcontactet.setPadding((int) getResources().getDimension(R.dimen.book_draw_pad_left), 0, 0, 0);
        bookphoneet.setPadding((int) getResources().getDimension(R.dimen.book_draw_pad_left), 0, 0, 0);
        ((RelativeLayout.LayoutParams) bookaddressicon.getLayoutParams()).leftMargin = (int) getResources().getDimension(R.dimen.book_draw_pad_left);
    }

    /**
     * 检测输入。
     *
     * @return
     */
    private String checkOrder() {
        String checkResult = "";

        String contactStr = bookcontactet.getText().toString().trim();     //联系人。
        if (TextUtil.stringIsNull(contactStr)) {
            checkResult = getResources().getString(R.string.add_name_warning);
            return checkResult;
        } else if (contactStr.length() < 2) {
            checkResult = getResources().getString(R.string.name_length_warning);
            return checkResult;
        } else {
            orderBean.setContact(contactStr);
        }

        String phoneStr = bookphoneet.getText().toString().trim();     //联系电话。
        checkResult = VaildUtil.validPhone(phoneStr);
        if (!TextUtil.stringIsNull(checkResult)) {
            return checkResult;
        }

        orderBean.setTel(phoneStr);

        String bookAddr = bookaddrtv.getText().toString().trim();      //地址。
        if (TextUtil.stringIsNull(bookAddr)) {
            checkResult = getResources().getString(R.string.book_addr_warning);
            return checkResult;
        } else {
            orderBean.setAddress_content(bookAddr);
            orderBean.setAddress_id(address.getId());
        }

        String timeStr = booktimeet.getText().toString().trim();       //取餐时间。
        if (TextUtil.stringIsNull(timeStr)) {
            checkResult = getResources().getString(R.string.book_time_warning);
            return checkResult;
        } else {
            orderBean.setUse_time(timeStr);
        }

        String otherStr = bookotheret.getText().toString().trim();      //备注。
        orderBean.setRemark(otherStr);

        return checkResult;
    }

}
