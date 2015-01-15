package com.promo.zjyfrestaurant.book;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jch.lib.view.PagerSlidingTabStrip;
import com.promo.zjyfrestaurant.BaseFragment;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.book.bookActivity.BookFragCallBack;
import com.promo.zjyfrestaurant.book.subFragment.ToRestFragment;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCart;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCartView;
import com.promo.zjyfrestaurant.util.ContextUtil;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link com.promo.zjyfrestaurant.book.BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends BaseFragment {

    public static final String FROMKEY = "from";

    private static BookFragment fragment = null;
    private boolean isFromActivity = false;

    private BookPagerAdapter bookPagerAdapter;

    private BookFragCallBack fragCallBack;

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
            args.putBoolean(FROMKEY, false);
            fragment.setArguments(args);
        }

        return fragment;

    }

    public BookFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFromActivity = getArguments().getBoolean(FROMKEY);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ShoppingCart.newInstance().getOrderNum() == 0) {
            bookpager.setCurrentItem(0);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragCallBack = (BookFragCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragCallBack = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden && bookpager.getCurrentItem() != 0) {
//            showBookDialog();//检测购物车。
//        }
        bookpager.setCurrentItem(0);

    }

    /**
     * 购物车中没有菜品的提示。
     */
    private void showBookDialog() {
        int dishNum = ShoppingCart.newInstance().getDishNum();
        if (dishNum != 0) {
            return;
        }
        bookpager.setCurrentItem(0);
        ContextUtil.toast(getActivity().getApplicationContext(), getString(R.string.go_dish));

//        ZJYFDialog.Builder builder = ZJYFDialog.Builder.zJYFDialog(getActivity()).setTitle(getString(R.string.dilog_title)).setContentMsg(getString(R.string.no_dish_warn)).setPositiveBtn(R.string.dialog_go, new ZJYFDialog.ZJYFOnclickListener() {
//            @Override
//            public void onclick() {
//                Intent intent = new Intent(getActivity(), MenuActivity.class);
//                transNextPage(intent);
//            }
//        }).setNectiveBtn(R.string.dialog_come, new ZJYFDialog.ZJYFOnclickListener() {
//            @Override
//            public void onclick() {
//                bookpager.setCurrentItem(bookPagerAdapter.getCount() - 1);  //跳转到外卖
//            }
//        });
//        ZJYFDialog dialog = builder.build();
//        dialog.setOnCancelListener(new DialogCancel());
//        dialog.show();
    }

    /**
     * dialog 隐藏监听。
     */
    private class DialogCancel implements DialogInterface.OnCancelListener {

        @Override
        public void onCancel(DialogInterface dialog) {
            bookpager.setCurrentItem(bookPagerAdapter.getCount() - 1);  //跳转到外卖
        }
    }

    @Override
    protected View addContentView(LayoutInflater inflater) {
        mContainerView = inflater.inflate(R.layout.fragment_book, null);

        initView(mContainerView);
        return mContainerView;
    }

    private void initView(View containerView) {
        addShoppingCart();
        if (!isFromActivity) {
            hideBackBtn();
        }
        setFragCallBack(fragCallBack);

        bookslidetab = (PagerSlidingTabStrip) containerView.findViewById(R.id.book_slide_tab);
        bookpager = (ViewPager) containerView.findViewById(R.id.book_pager);
        bookPagerAdapter = new BookPagerAdapter(getChildFragmentManager(), getResources().getStringArray(R.array.book_tab), getActivity().getApplicationContext());
        bookpager.setAdapter(bookPagerAdapter);
        bookslidetab.setViewPager(bookpager);
        bookslidetab.setOnPageChangeListener(new ViewPagerChangerListener());
//        showBookDialog();
    }

    private class ViewPagerChangerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position != 0)
                showBookDialog();
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK)
            if (requestCode == ToRestFragment.REQ_CODE)
                ((Fragment) bookPagerAdapter.instantiateItem(bookpager, 2)).onActivityResult(requestCode, resultCode, data);        //获取fragment。
    }
}
