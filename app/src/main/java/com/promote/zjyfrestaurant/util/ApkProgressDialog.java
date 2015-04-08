package com.promote.zjyfrestaurant.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.promote.zjyfrestaurant.R;


/**
 * Created by acer on 2014/10/15.
 */
public class ApkProgressDialog extends Dialog {
    ProgressBar bar;

    public ApkProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.download_progress);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        findViewById(R.id.btn_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.btn_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onPausePressed();
            }
        });
    }

    public void setProgress(int progress) {

        TextView progressText = (TextView) findViewById(R.id.progressText);
        bar.setProgress(progress);
        progressText.setText(getContext().getResources().getString(R.string.downloaded, progress));
    }

    public void onPausePressed() {

    }
}
