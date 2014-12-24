package com.promo.zjyfrestaurant.book.subFragment;


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
import android.widget.TextView;

import com.jch.lib.util.DisplayUtil;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.personal.AddressActivity;
import com.promo.zjyfrestaurant.view.NumberView;

/**
 * 外卖服务。
 */
public class ToRestFragment extends BookBaseFragment implements View.OnClickListener {

    public static final int REQ_CODE = 5689;
    public static final String ADDR_KEY = "address";

    private NumberView bookaddrnum;
    private ImageView bookaddressicon;
    private ImageButton bookseladdressbtn;
    private TextView bookaddrtv;
    private RelativeLayout booaddrrl;
    private NumberView booktimenum;
    private EditText booktimeet;
    private NumberView bookothernum;
    private EditText bookotheret;
    private Button booksubmitbtn;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ToRestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToRestFragment newInstance() {
        ToRestFragment fragment = new ToRestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_to_rest, container, false);
        initialize(view);

        return view;
    }

    /**
     * @param containerView
     */
    private void initialize(View containerView) {

        bookaddrnum = (NumberView) containerView.findViewById(R.id.book_addr_num);
        bookaddressicon = (ImageView) containerView.findViewById(R.id.book_address_icon);
        bookseladdressbtn = (ImageButton) containerView.findViewById(R.id.book_sel_address_btn);
        bookaddrtv = (TextView) containerView.findViewById(R.id.book_addr_tv);
        booaddrrl = (RelativeLayout) containerView.findViewById(R.id.boo_addr_rl);
        booktimenum = (NumberView) containerView.findViewById(R.id.book_time_num);
        booktimeet = (EditText) containerView.findViewById(R.id.book_time_et);
        bookothernum = (NumberView) containerView.findViewById(R.id.book_other_num);
        bookotheret = (EditText) containerView.findViewById(R.id.book_other_et);
        booksubmitbtn = (Button) containerView.findViewById(R.id.book_submit_btn);

        setInputFocusChange(booktimenum, booktimeet);
        setInputFocusChange(bookothernum, bookotheret);

        bookseladdressbtn.setOnClickListener(this);
        booksubmitbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.book_sel_address_btn: {

                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivityForResult(intent, REQ_CODE);

                break;
            }

            case R.id.book_submit_btn: {
                submit();
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            String address = data.getStringExtra(ADDR_KEY);
            bookaddrtv.setText(address);
            if (address != null && !address.equals("")) {
                bookaddrnum.setChecked(true);
                DisplayUtil.setBackground(booaddrrl, getResources().getDrawable(R.drawable.book_enter_focused));
            } else {
                bookaddrnum.setChecked(false);
                DisplayUtil.setBackground(booaddrrl, getResources().getDrawable(R.drawable.book_enter));
            }
        }

    }
}
