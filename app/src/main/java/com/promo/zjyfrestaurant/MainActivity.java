package com.promo.zjyfrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jch.lib.util.DoubleExit;
import com.promo.zjyfrestaurant.book.bookActivity.BookFragCallBack;
import com.promo.zjyfrestaurant.book.subFragment.ToRestFragment;
import com.promo.zjyfrestaurant.exception.FragmentException;
import com.promo.zjyfrestaurant.personal.AddressActivity;


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, BookFragCallBack {

    public static final String INTENT_TYPE_KEY = "main_intent";
    public static final int FROM_ADDR = 26;
    public static final int FROM_DEFAULT = -1;

    private FrameLayout contentfl;
    private RadioButton mainrb;
    private RadioButton findrb;
    private RadioButton publishrb;
    private RadioButton centerrb;
    private RadioGroup mainrg;

    private BaseFragment mCurFragment = null;
    private BaseFragment mPreFragmetn = null;
    private android.support.v4.app.FragmentManager mFM = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initialize();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK)
            DoubleExit.instance(getApplicationContext()).exit();
        return true;

    }


    private void initialize() {

        contentfl = (FrameLayout) findViewById(R.id.content_fl);
        mainrb = (RadioButton) findViewById(R.id.home_rb);
        findrb = (RadioButton) findViewById(R.id.book_rb);
        publishrb = (RadioButton) findViewById(R.id.map_rb);
        centerrb = (RadioButton) findViewById(R.id.personal_rb);
        mainrg = (RadioGroup) findViewById(R.id.main_rg);
        mainrg.setOnCheckedChangeListener(this);

        initFragment();
    }

    /**
     * 初始化fragment.
     */
    private void initFragment() {

        //启动程序是默认加载第一个tab.
        mCurFragment = MainFragmentFactory.fragCreate(MainFragmentFactory.HOME_FRG);
        mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.add(R.id.main_content_fl, mCurFragment, "main");
        ft.commitAllowingStateLoss();
    }

    /**
     * 隐藏显示相应的frag，并将设置当前的fragment。
     *
     * @param showFrag 将要显示的frag
     * @param hideFrag 要隐藏的frag。
     */
    private void changeFrag(BaseFragment showFrag, BaseFragment hideFrag) {
        if (showFrag == hideFrag) {
            return;
        }
        FragmentTransaction ft = mFM.beginTransaction();
        if (showFrag.isAdded()) {
            ft.show(showFrag);

        } else {
            ft.add(R.id.main_content_fl, showFrag, null);

        }
        if (hideFrag != null && hideFrag.isAdded()) {
            ft.hide(hideFrag);
        }
        ft.commitAllowingStateLoss();
        mCurFragment = showFrag;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) { //通过单例模式获得每个fragment.
            case R.id.home_rb: {
                mPreFragmetn = MainFragmentFactory.fragCreate(MainFragmentFactory.HOME_FRG);
                changeFrag(mPreFragmetn, mCurFragment);
                break;
            }
            case R.id.book_rb: {
                mPreFragmetn = MainFragmentFactory.fragCreate(MainFragmentFactory.BOOK_FRG);
                changeFrag(mPreFragmetn, mCurFragment);

                break;
            }

            case R.id.map_rb: {
                mPreFragmetn = MainFragmentFactory.fragCreate(MainFragmentFactory.MAP_FRG);
                changeFrag(mPreFragmetn, mCurFragment);
                break;
            }
            case R.id.personal_rb: {

                mPreFragmetn = MainFragmentFactory.fragCreate(MainFragmentFactory.PERSONAL_FRG);
                changeFrag(mPreFragmetn, mCurFragment);
                break;
            }

            default: {
                try {
                    throw new FragmentException();
                } catch (FragmentException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK)
//
//        if (resultCode == RESULT_CANCELED) {
//            mainrb.setChecked(true);
//        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int resultCode = intent.getIntExtra(INTENT_TYPE_KEY, FROM_DEFAULT);
        if (resultCode == FROM_ADDR) {      //地址选择。
            mCurFragment.onActivityResult(ToRestFragment.REQ_CODE, RESULT_OK, intent);
        }
        //判断购买成功后返回的intent是从BookActivity还是MainActivity.
        int fromActivityCode = getIntent().getIntExtra(AddressActivity.FROM_ACIVITY_KEY, FROM_DEFAULT);
        if (fromActivityCode == AddressActivity.FROM_BOOK_CODE) {
            mainrb.setChecked(true);
        }


    }

    @Override
    public void bookFragcall() {
    }
}
