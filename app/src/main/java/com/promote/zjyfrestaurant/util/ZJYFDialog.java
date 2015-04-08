package com.promote.zjyfrestaurant.util;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.promote.zjyfrestaurant.R;

/**
 * 自定义dialog
 * <p/>
 * Created by ACER on 2015/1/6.
 */
public class ZJYFDialog extends AlertDialog {

    public interface ZJYFOnclickListener {
        public void onclick();
    }

    private TextView mydialogtitle;
    private TextView mydialogmsg;
    private TextView btnpositive;
    private TextView buttonnegative;

    private static final int NETIVECLICK = 5;
    private static final int POSITIVECLICK = 6;

    Params p;

    protected ZJYFDialog(Context context) {
        super(context);
        p = new Params();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zjyf_dialog_layout);

        initialize();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (p.title != null)
            mydialogtitle.setText(p.title);
        if (p.contentStr != null)
            mydialogmsg.setText(p.contentStr);
        if (p.netiveStr != null) {
            buttonnegative.setText(p.netiveStr);
            buttonnegative.setOnClickListener(new OnCLS(NETIVECLICK));
        } else {
            buttonnegative.setVisibility(View.GONE);
        }
        if (p.positiveStr != null) {
            btnpositive.setText(p.positiveStr);
            btnpositive.setOnClickListener(new OnCLS(POSITIVECLICK));
        } else {
            btnpositive.setVisibility(View.GONE);
        }
    }

    private void initialize() {

        mydialogtitle = (TextView) findViewById(R.id.mydialog_title);
        mydialogmsg = (TextView) findViewById(R.id.mydialog_msg);
        btnpositive = (TextView) findViewById(R.id.btn_positive);
        buttonnegative = (TextView) findViewById(R.id.button_negative);
    }

    private class Params {
        CharSequence title;
        CharSequence positiveStr;
        CharSequence netiveStr;
        CharSequence contentStr;
        ZJYFOnclickListener positivOCL;
        ZJYFOnclickListener negtivOCL;
    }

    /**
     * temp click listener.
     */
    class OnCLS implements View.OnClickListener {
        private int clickType;

        private OnCLS(int clickType) {
            this.clickType = clickType;
        }

        @Override
        public void onClick(View v) {
            if (clickType == NETIVECLICK) {
                if (p.negtivOCL != null)
                    p.negtivOCL.onclick();
                dismiss();
            } else if (clickType == POSITIVECLICK) {
                if (p.positivOCL != null)
                    p.positivOCL.onclick();
                dismiss();
            }

        }

    }

    /**
     * dialog builder.
     */
    public static class Builder {
        private ZJYFDialog zJYFDialog;

        private Builder(Context context) {
            zJYFDialog = new ZJYFDialog(context);
        }


        public static Builder zJYFDialog(Context context) {

            return new Builder(context);
        }

        public Builder setTitle(String title) {
            zJYFDialog.p.title = title;
            return this;
        }

        public Builder setTitle(int title) {
            zJYFDialog.p.title = zJYFDialog.getContext().getString(title);
            return this;
        }

        public Builder setContentMsg(CharSequence content) {
            zJYFDialog.p.contentStr = content;
            return this;
        }

        public Builder setContentMsg(int content) {

            zJYFDialog.p.contentStr = zJYFDialog.getContext().getString(content);
            return this;
        }

        public Builder setPositiveBtn(int btnTextIds, ZJYFOnclickListener cll) {
            zJYFDialog.p.positiveStr = zJYFDialog.getContext().getString(btnTextIds);
            zJYFDialog.p.positivOCL = cll;
            return this;
        }

        public Builder setPositiveBtn(CharSequence btnText, ZJYFOnclickListener cll) {
            zJYFDialog.p.positiveStr = btnText;
            zJYFDialog.p.positivOCL = cll;
            return this;
        }

        public Builder setNectiveBtn(int btnNectiveIds, ZJYFOnclickListener cll) {
            zJYFDialog.p.netiveStr = zJYFDialog.getContext().getString(btnNectiveIds);
            zJYFDialog.p.negtivOCL = cll;
            return this;
        }

        public Builder setNectiveBtn(CharSequence btnNectiveIds, ZJYFOnclickListener cll) {
            zJYFDialog.p.netiveStr = btnNectiveIds;
            zJYFDialog.p.negtivOCL = cll;
            return this;
        }


        public ZJYFDialog build() {
            return zJYFDialog;
        }

        public void show() {
            zJYFDialog.show();
        }
    }
}
