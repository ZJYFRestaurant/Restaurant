package com.promo.zjyfrestaurant.book.bookActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.book.BookFragment;

/**
 * Created by ACER on 2014/12/30.
 */
public class BookActivity extends FragmentActivity implements BookFragCallBack {


    private FrameLayout bookcontainer;

    @Override
    public void bookFragcall() {
        onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_layout);

        initialize();
    }


    private void initialize() {

        BookFragment bookFragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(BookFragment.FROMKEY, true);
        bookFragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.book_container, bookFragment, null);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }


}
