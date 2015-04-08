package com.promote.zjyfrestaurant;

import com.promote.zjyfrestaurant.book.BookFragment;
import com.promote.zjyfrestaurant.home.HomeFragment;
import com.promote.zjyfrestaurant.map.MapFragment;
import com.promote.zjyfrestaurant.personal.PersonalFragment;

/**
 * Created by ACER on 2014/12/10.
 */
public class MainFragmentFactory {

    public static final String HOME_FRG = "home_frag";
    public static final String BOOK_FRG = "book_frag";
    public static final String MAP_FRG = "map_frag";
    public static final String PERSONAL_FRG = "person_frag";

    public static BaseFragment fragCreate(String fragName) {

        BaseFragment baseFragment = null;

        if (HOME_FRG.equals(fragName)) {
            baseFragment = HomeFragment.newInstance();
        } else if (BOOK_FRG.equals(fragName)) {
            baseFragment = BookFragment.newInstance();
        } else if (MAP_FRG.equals(fragName)) {
            baseFragment = MapFragment.newInstance();
        } else if (PERSONAL_FRG.equals(fragName)) {
            baseFragment = PersonalFragment.newInstance();
        }

        return baseFragment;
    }

}
