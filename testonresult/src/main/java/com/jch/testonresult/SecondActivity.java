package com.jch.testonresult;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by ACER on 2015/1/4.
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

    }

    public void preview(View view) {
        setResult(153);
        finish();
    }
}
