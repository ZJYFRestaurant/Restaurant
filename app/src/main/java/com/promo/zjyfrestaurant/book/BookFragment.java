package com.promo.zjyfrestaurant.book;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jch.lib.view.PagerSlidingTabStrip;
import com.promo.zjyfrestaurant.BaseFragment;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCartView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link com.promo.zjyfrestaurant.book.BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends BaseFragment {

    private static BookFragment fragment = null;

    private View mContainerView = null;
    private PagerSlidingTabStrip bookslidetab;
    private ViewPager bookpager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookFragment.
     */
    public static BookFragment newInstance() {
        if (fragment == null) {
            fragment = new BookFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
        }

        return fragment;

    }

    public BookFragment() {

    }

    @Override
    protected View addContentView(LayoutInflater inflater) {
        mContainerView = inflater.inflate(R.layout.fragment_book, null);

        initView(mContainerView);
        return mContainerView;
    }

    private void initView(View containerView) {
        addShoppingCart();

        bookslidetab = (PagerSlidingTabStrip) containerView.findViewById(R.id.book_slide_tab);
        bookpager = (ViewPager) containerView.findViewById(R.id.book_pager);
        BookPagerAdapter bookPagerAdapter = new BookPagerAdapter(getChildFragmentManager(), getResources().getStringArray(R.array.book_tab), getActivity().getApplicationContext());
        bookpager.setAdapter(bookPagerAdapter);
        bookslidetab.setViewPager(bookpager);

    }

    /**
     * 添加购物车按钮。
     */
    private void addShoppingCart() {
        setTitle(getResources().getString(R.string.book));
        ShoppingCartView shoppingCartView = new ShoppingCartView(getActivity().getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        shoppingCartView.setLayoutParams(params);
        addRightItem(shoppingCartView);
    }


}
