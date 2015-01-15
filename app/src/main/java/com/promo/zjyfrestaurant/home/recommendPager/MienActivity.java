package com.promo.zjyfrestaurant.home.recommendPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;

import com.jch.lib.util.DialogUtil;
import com.jch.lib.util.DisplayUtil;
import com.jch.lib.view.TurnPage.PageLoadCallback;
import com.jch.lib.view.TurnPage.PageWidget;
import com.jch.lib.view.TurnPage.TurnPageAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;
import com.promo.zjyfrestaurant.util.LogCat;

import java.util.ArrayList;

/**
 * 渔府风采。
 */
public class MienActivity extends BaseActivity {

    private ArrayList<String> photoes = new ArrayList<String>();
    /**
     * adapter. *
     */
    private MienPageAdapter adapter;

    @Override
    protected View initContentView() {

        PageWidget pageWidget = new PageWidget(getApplicationContext());
        Point screenSize = new Point();
        DisplayUtil.getSize(getWindowManager().getDefaultDisplay(), screenSize);
        pageWidget.setScreen(screenSize.x, (int) (screenSize.y - getResources().getDimension(R.dimen.title_height)), BitmapFactory.decodeResource(getResources(), R.drawable.loading));
        initView(pageWidget);

        return pageWidget;
    }

    @Override
    protected void getData() {
        String urlStr = HttpConstant.getPhotoList;
        ZJYFRequestParmater parma = new ZJYFRequestParmater(this);
        ShowMenuRequset.getListData(this, urlStr, parma, String.class, new RequestCallback<ArrayList<String>>() {

            @Override
            public void onfailed(String msg) {
                DialogUtil.toastMsg(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(ArrayList<String> data) {
                photoes.clear();
                photoes.addAll(data);
                adapter.notifyChanged();
            }
        });
    }

    private void initView(PageWidget containerView) {

        setTitle(getResources().getString(R.string.mien_title));
        adapter = new MienPageAdapter();
        containerView.setAdapter(adapter);
        getData();
    }


    private class MienPageAdapter extends TurnPageAdapter {

        @Override
        public void instantiateItem(View container, int position, final PageLoadCallback callback) {
            LogCat.d("turns pager adapter psition--:" + position);
            ImageLoader.getInstance().loadImage(photoes.get(position), ContextUtil.getCircleImgOptions(), new ImageLoadingListener() {

                @Override
                public void onLoadingStarted(String s, View view) {
                    callback.onLoadStart(BitmapFactory.decodeResource(getResources(), R.drawable.loading));
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    callback.onLoadComplited(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                }
            });

        }

        @Override
        public int getCount() {
            return photoes.size();
        }

        @Override
        public void destroyItem(View container, int position) {

        }

    }

}
