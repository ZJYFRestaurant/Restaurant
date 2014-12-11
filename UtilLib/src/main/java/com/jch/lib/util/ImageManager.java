package com.jch.lib.util;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageManager {


    public static void load(String imgUrl, ImageView imgView) {
        ImageLoader.getInstance().displayImage(imgUrl, imgView);
    }

    public static void load(String imgUrl, ImageView imgView, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(imgUrl, imgView, options);
    }

//    public static void loadWithScaleType(String imgUrl, ImageView imgView, final DisplayImageOptions options) {
//
//        ImageLoader.getInstance().displayImage(imgUrl, imgView, options, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String s, View view) {
//                if (view instanceof ImageView) {
//                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//                    ((ImageView) view).setImageDrawable(options.getImageOnLoading());
//                }
//            }
//
//            @Override
//            public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//                if (view instanceof ImageView) {
//                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//                    ((ImageView) view).setImageDrawable(options.getImageOnFail(null));
//
//                }
//            }
//
//            @Override
//            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//
//                if (view instanceof ImageView) {
//                    ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    ((ImageView) view).setImageBitmap(bitmap);
//                }
//            }
//
//            @Override
//            public void onLoadingCancelled(String s, View view) {
//
//            }
//        });
//    }

}
