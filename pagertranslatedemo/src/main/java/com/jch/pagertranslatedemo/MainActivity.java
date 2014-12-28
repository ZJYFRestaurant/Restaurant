package com.jch.pagertranslatedemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        PageWidget pageWidget = new PageWidget(this);

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        pageWidget.SetScreen(width, height);

        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.pic1);
        Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.pic2);

        Bitmap foreImage = Bitmap.createScaledBitmap(bm1, width, height, false);
        Bitmap bgImage = Bitmap.createScaledBitmap(bm2, width, height, false);

        pageWidget.setBgImage(bgImage);
        pageWidget.setForeImage(foreImage);

        setContentView(pageWidget);

        super.onCreate(savedInstanceState);

    }

}