package com.promo.zjyfrestaurant.book.subFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.promo.zjyfrestaurant.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToBringFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToBringFragment extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_bring, container, false);
    }


}
