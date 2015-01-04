package com.promo.zjyfrestaurant.book.subFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jch.lib.util.TextUtil;
import com.jch.lib.util.VaildUtil;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.bean.OrderType;
import com.promo.zjyfrestaurant.util.ContextUtil;
import com.promo.zjyfrestaurant.view.NumberView;

/**
 * 外带取餐
 */
public class ToBringFragment extends BookBaseFragment {

    private NumberView bookcontactnum;
    private EditText bookcontactet;
    private NumberView bookphonenum;
    private EditText bookphoneet;
    private NumberView booktimenum;
    private EditText booktimeet;
    private NumberView bookothernum;
    private EditText bookotheret;
    private Button booksubmitbtn;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ToBringFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToBringFragment newInstance() {
        ToBringFragment fragment = new ToBringFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ToBringFragment() {
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
        View containerView = inflater.inflate(R.layout.fragment_to_bring, container, false);

        initialize(containerView);

        return containerView;
    }


    private void initialize(View containerView) {

        bookcontactnum = (NumberView) containerView.findViewById(R.id.book_contact_num);
        bookcontactet = (EditText) containerView.findViewById(R.id.book_contact_et);
        bookphonenum = (NumberView) containerView.findViewById(R.id.book_phone_num);
        bookphoneet = (EditText) containerView.findViewById(R.id.book_phone_et);
        booktimenum = (NumberView) containerView.findViewById(R.id.book_time_num);
        booktimeet = (EditText) containerView.findViewById(R.id.book_time_et);
        bookothernum = (NumberView) containerView.findViewById(R.id.book_other_num);
        bookotheret = (EditText) containerView.findViewById(R.id.book_other_et);
        booksubmitbtn = (Button) containerView.findViewById(R.id.book_submit_btn);

        setInputFocusChange(bookcontactnum, bookcontactet);
        setInputFocusChange(bookphonenum, bookphoneet);
        setInputFocusChange(booktimenum, booktimeet);
        setInputFocusChange(bookothernum, bookotheret);

        booktimeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(booktimeet);
            }
        });

        booksubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkResult = checkOrder();
                if (!TextUtil.stringIsNull(checkResult)) {
                    ContextUtil.toast(getActivity().getApplicationContext(), checkResult);
                    return;
                }
                orderBean.setType(OrderType.BRING);

                submit(orderBean);
            }
        });
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
        } else {
            orderBean.setContact(contactStr);
        }

        String phoneStr = bookphoneet.getText().toString().trim();     //联系电话。
        checkResult = VaildUtil.validPhone(phoneStr);
        if (!TextUtil.stringIsNull(checkResult)) {
            return checkResult;
        }

        orderBean.setTel(phoneStr);


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
