package com.promo.zjyfrestaurant.book.subFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.promo.zjyfrestaurant.R;
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

        booksubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }
}
