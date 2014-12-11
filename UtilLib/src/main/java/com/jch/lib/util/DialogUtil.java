package com.jch.lib.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.TextView;

import com.jch.lib.R;
import com.jch.lib.view.MyMsgAlertDialog;

/**
 * 自定义公共的dialog.
 *
 * @author Administrator
 */
public class DialogUtil {

    /**
     * 网络连接提示.
     *
     * @param context
     * @return
     */
    public static Dialog netLineWarning(Context context) {

        Dialog dialog = new Dialog(context);

        return dialog;

    }

    /**
     * 旋转等待dialog.
     *
     * @param context
     * @return
     */
    public static ProgressDialog waitingDialog(Context context) {
        return waitingDialog(context, "数据访问中");
    }

    public static ProgressDialog waitingDialog(Context context, boolean visible) {
        return waitingDialog(context, visible, "数据访问中");
    }

    public static ProgressDialog waitingDialog(Context context, boolean visible, String msg) {
        LoadingDialog dialog = new LoadingDialog(context);
        dialog.setMessage(msg);
        dialog.setCancelable(true);
        if (visible)
            dialog.show();
        return dialog;
    }


    public static ProgressDialog waitingDialog(Context context, String msg) {
        LoadingDialog dialog = new LoadingDialog(context);
        dialog.setMessage(msg);
        dialog.setCancelable(true);
        dialog.show();
        return dialog;
    }

//    public static AlertDialog myMsgDialog(Context )

    public static AlertDialog msgSinglBtnAlertDialog(Context context, String msg) {


        AlertDialog dialog = new AlertDialog.Builder(context).setMessage(msg)
                .setPositiveButton(R.string.ok, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        return dialog;

    }

    /**
     * 显示带信息的dialog.
     *
     * @param activity
     * @param msg
     * @return
     */
    public static Dialog buildMyMsgAlertDialog(Activity activity, String msg) {

        MyMsgAlertDialog dialog = new MyMsgAlertDialog(activity);
        dialog.setMsg(msg);

        return dialog;
    }

    public static void showListDialog(Context context, OnClickListener l, String... items) {
        new AlertDialog.Builder(context).setItems(items, l).show();
    }

    public static void showDeleteDialog(Context context, OnClickListener l) {
        showListDialog(context, l, context.getString(R.string.delete), context.getString(R.string.cancel));
    }

    public static class LoadingDialog extends ProgressDialog {
        private TextView tv_message;
        private CharSequence message;

        public LoadingDialog(Context context) {
            super(context);
        }

        public LoadingDialog(Context context, int theme) {
            super(context, theme);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.loading_dialog);
            tv_message = (TextView) findViewById(R.id.message);
        }

        @Override
        public void onStart() {
            super.onStart();
            tv_message.setText(message);
        }

        @Override
        public void setMessage(CharSequence message) {
            this.message = message;
            if (tv_message != null) tv_message.setText(message);
        }
    }

}
