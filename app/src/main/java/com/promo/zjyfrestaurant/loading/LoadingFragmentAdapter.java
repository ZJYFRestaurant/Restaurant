package com.promo.zjyfrestaurant.loading;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by ACER on 2015/1/7.
 */
public class LoadingFragmentAdapter extends FragmentPagerAdapter {

    private int[] pagerBgs;
    private int[] pagerTexts;

    public LoadingFragmentAdapter(FragmentManager fm, int[] pagerBgs, int[] pagerTexts) {
        super(fm);
        this.pagerBgs = pagerBgs;
        this.pagerTexts = pagerTexts;
    }

    @Override
    public Fragment getItem(int position) {
        if (position < getCount() - 1)
            return GuidPageFragment.newInstance(pagerBgs[position], pagerTexts[position], false);
        else
            return GuidPageFragment.newInstance(pagerBgs[position], pagerTexts[position], true);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return pagerBgs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
