package com.promo.zjyfrestaurant.home.recommendPager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.jch.lib.util.DialogUtil;
import com.jch.lib.view.CurlView.CurlPage;
import com.jch.lib.view.CurlView.CurlView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;

import java.util.ArrayList;

/**
 * 渔府风采。
 */
public class MienActivity extends BaseActivity {

    private CurlView mCurlView;
    private ArrayList<String> photoes = new ArrayList<String>();
    private int color = 0xFF202830;

    /**
     * adapter. *
     */

    @Override
    protected View initContentView() {
        setTitle(getString(R.string.mien_title));

        View view = View.inflate(getApplicationContext(), R.layout.activity_mien, null);
        mCurlView = (CurlView) view.findViewById(R.id.curl);

        getData();
        return view;
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
                initCrulPager();

            }
        });
    }

    private void initCrulPager() {
        mCurlView.setPageProvider(new PageProvider());
        mCurlView.setSizeChangedObserver(new SizeChangedObserver());
        int index = 0;
        if (getLastNonConfigurationInstance() != null) {
            index = (Integer) getLastNonConfigurationInstance();
        }

        mCurlView.setCurrentIndex(index);
        mCurlView.setBackgroundColor(color);

    }


    /**
     * Bitmap provider.
     */
    private class PageProvider implements CurlView.PageProvider {

        // Bitmap resources.
//        private int[] mBitmapIds = {R.drawable.obama, R.drawable.world, R.drawable.road_rage,
//                R.drawable.taipei_101};

        @Override
        public int getPageCount() {

            return photoes.size();
        }

        private Bitmap loadBitmap(int width, int height, int index, CurlPage page) {
            Bitmap b = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            b.eraseColor(0xFFFFFFFF);
            Canvas c = new Canvas(b);
            Drawable d = getResources().getDrawable(R.drawable.loading);
            loadfromUrl(photoes.get(index), b, c, page, width, height);
            resizeBitmap(d, c, width, height);
            return b;
        }


        @Override
        public void updatePage(CurlPage page, int width, int height, int index) {

            Log.i("CurActivity", " page index :" + index);
            Bitmap front = loadBitmap(width, height, index, page);
//            page.setTexture(front, CurlPage.SIDE_BOTH);
//            page.setColor(Color.argb(127, 255, 255, 255),
//                    CurlPage.SIDE_BACK);
        }

    }

    private void resizeBitmap(Drawable d, Canvas c, int width, int height) {

        int margin = 7;
        int border = 3;
        Rect r = new Rect(margin, margin, width - margin, height - margin);

        int imageWidth = r.width() - (border * 2);
        int imageHeight = imageWidth * d.getIntrinsicHeight()
                / d.getIntrinsicWidth();
        if (imageHeight > r.height() - (border * 2)) {
            imageHeight = r.height() - (border * 2);
            imageWidth = imageHeight * d.getIntrinsicWidth()
                    / d.getIntrinsicHeight();
        }

        r.left += ((r.width() - imageWidth) / 2) - border;
        r.right = r.left + imageWidth + border + border;
        r.top += ((r.height() - imageHeight) / 2) - border;
        r.bottom = r.top + imageHeight + border + border;

        Paint p = new Paint();
        p.setColor(0xFFC0C0C0);
        c.drawRect(r, p);
        r.left += border;
        r.right -= border;
        r.top += border;
        r.bottom -= border;

        d.setBounds(r);
        d.draw(c);
    }

    private void loadfromUrl(String url, final Bitmap b, final Canvas c, final CurlPage page, final int width, final int height) {

        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                bitmap.prepareToDraw();
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                resizeBitmap(bitmapDrawable, c, width, height);
                page.setTexture(b, CurlPage.SIDE_BOTH);
                page.setColor(Color.argb(127, 255, 255, 255),
                        CurlPage.SIDE_BACK);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }


    /**
     * CurlView size changed observer.
     */
    private class SizeChangedObserver implements CurlView.SizeChangedObserver {
        @Override
        public void onSizeChanged(int w, int h) {
            if (w > h) {
                mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
                mCurlView.setMargins(.1f, .05f, .1f, .05f);
            } else {
                mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
                mCurlView.setMargins(.1f, .1f, .1f, .1f);
            }
        }
    }

}
