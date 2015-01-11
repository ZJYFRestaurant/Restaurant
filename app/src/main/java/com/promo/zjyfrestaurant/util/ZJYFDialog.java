package com.promo.zjyfrestaurant.util;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.promo.zjyfrestaurant.R;

/**
 * Created by ACER on 2015/1/6.
 */
public class ZJYFDialog extends AlertDialog {

    private TextView zdialogtitle;

    protected ZJYFDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zjyf_dialog_layout);

        initialize();
    }

    private void initialize() {

        zdialogtitle = (TextView) findViewById(R.id.z_dialog_title);
    }


}
