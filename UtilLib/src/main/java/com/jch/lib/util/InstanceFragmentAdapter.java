package com.jch.lib.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * 利用此类添加fragment，可以避免fragment重执行oncreat().
 * Created by jch on 2014/9/19.
 */
public class InstanceFragmentAdapter {


    public static InstanceFragmentAdapter instanceFragmentAdapter = null;
    private FragmentManager mFragmentManager = null;

    private FragmentTransaction mFt = null;
    private FragmentFactory mFragmentFactory = null;

    public InstanceFragmentAdapter(FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    /**
     * 以单例模式获得本类实例。
     *
     * @param fm
     * @return
     */
    public static InstanceFragmentAdapter newInstance(FragmentManager fm) {
        if (instanceFragmentAdapter == null) {
            instanceFragmentAdapter = new InstanceFragmentAdapter(fm);
        }

        return instanceFragmentAdapter;
    }

    /**
     * 判断需被替换的fragment是否存在，如果存在就调用detach.取消被替换的fragment.
     * 类似判断将要替换的fragment.并显示。
     *
     * @param containerId
     * @param oldeFmName
     * @param newFmName
     * @param ff
     */
    public void replaceFramgent(int containerId, String oldeFmName, String newFmName, FragmentFactory ff) {

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Fragment oldeFragment = mFragmentManager.findFragmentByTag(oldeFmName);
        if (oldeFragment != null) {
            ft.detach(oldeFragment);
        }

        Fragment newFragment = mFragmentManager.findFragmentByTag(newFmName);
        if (newFragment != null) {
            ft.attach(newFragment);
            Log.d("ebingo", newFmName + "attack");
        } else {
            newFragment = ff.createFragment();
            Log.d("ebingo", newFmName + "add");
            ft.add(containerId, newFragment, newFmName);
        }

        ft.commitAllowingStateLoss();
        ft = null;
        mFragmentManager.executePendingTransactions();

    }

    public interface FragmentFactory {
        /**
         * 生成需要的fragment.
         *
         * @return
         */
        public Fragment createFragment();
    }

}
