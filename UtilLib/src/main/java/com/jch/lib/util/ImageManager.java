package com.jch.lib.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageManager {


    public static void load(String imgUrl, ImageView imgView, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(imgUrl, imgView, options);
    }

    /**
     * 图片圆角
     *
     * @param imgUrl
     * @param imgView
     * @param options
     * @param circlepix
     */
    public static void load(String imgUrl, ImageView imgView, final DisplayImageOptions options, final int circlepix) {
        ImageLoader.getInstance().displayImage(imgUrl, imgView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (view instanceof ImageView) {
                    Bitmap circleBmp = toRoundCorner(bitmap, circlepix);
                    ((ImageView) view).setImageBitmap(circleBmp);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });

    }


    public static void loadWithScaleType(String imgUrl, ImageView imgView, final DisplayImageOptions options) {

        ImageLoader.getInstance().displayImage(imgUrl, imgView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                if (view instanceof ImageView) {
                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    ((ImageView) view).setImageDrawable(options.getImageOnLoading(null));
                }
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

                if (view instanceof ImageView) {
                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    ((ImageView) view).setImageDrawable(options.getImageOnFail(null));

                }
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                if (view instanceof ImageView) {
                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ((ImageView) view).setImageBitmap(bitmap);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    /**
     * 设置bitmap圆角。
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    private static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);

        final float roundPx = pixels;

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;


    }

}
