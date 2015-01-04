package com.promo.zjyfrestaurant.book;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.promo.zjyfrestaurant.book.subFragment.SendFragment;
import com.promo.zjyfrestaurant.book.subFragment.ToBringFragment;
import com.promo.zjyfrestaurant.book.subFragment.ToRestFragment;

/**
 * Created by ACER on 2014/12/22.
 */
public class BookPagerAdapter extends FragmentPagerAdapter {


    private String[] tabs = null;

    private Context context = null;

    public BookPagerAdapter(FragmentManager fm, String[] tabs, Context context) {
        super(fm);

        this.tabs = tabs;
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabs[position];
    }

    @Override
    public int getCount() {

        return tabs.length;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        int index = position % tabs.length;
        if (index == 0) {
            fragment = SendFragment.newInstance();
        } else if (index == 1) {
            fragment = ToBringFragment.newInstance();
        } else {
            fragment = ToRestFragment.newInstance();
        }

        return fragment;
    }

}
