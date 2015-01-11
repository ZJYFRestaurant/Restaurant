package com.jch.lib.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by ACER on 2015/1/10.
 */
public class FileUtil {


    public static Intent share(String subject, String content, Uri uri) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if (uri != null) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            //当用户选择使用短信是用sms_body，取得文字。
            shareIntent.putExtra("sms_body", content);
        } else {
            shareIntent.setType("text/plain");
        }

        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);


        return shareIntent;
    }

    public static Uri saveImage(String path, String name, Bitmap bitmap) {

        File file = new File(path + name + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return Uri.fromFile(file);
    }
}
