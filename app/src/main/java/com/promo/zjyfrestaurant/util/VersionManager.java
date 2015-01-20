package com.promo.zjyfrestaurant.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.WindowManager;

import com.promo.zjyfrestaurant.MainActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.VersionBean;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by acer on 2014/10/11.
 */
public class VersionManager {


    public static void requestVersionCode(final Context context) {

        ZJYFRequestParmater parmater = new ZJYFRequestParmater(context);
        parmater.put("version", DeviceInfo.getVersionCode(context));
        ShowMenuRequset.getData(context, HttpConstant.getNewestVersion, parmater, VersionBean.class, new RequestCallback<VersionBean>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(context, context.getResources().getString(R.string.update_erro));
            }

            @Override
            public void onSuccess(VersionBean data) {
                int localVersion_code = DeviceInfo.getVersionCode(context);

                if (localVersion_code < data.getVersion()) {        //更新

                    File apkFile = getDownloadFile(context, String.valueOf(data.getVersion()));

                    if (apkFile.exists()) {
                        checkFileSize(context, data.getUrl(), String.valueOf(data.getVersion()));
                    } else {        //下载。
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(context.getString(R.string.find_new_version, apkFile.getName()));
                        builder.setPositiveButton(R.string.update_right_now, new DownloadListener(context, data.getUrl(), String.valueOf(data.getVersion())));
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        dialog.show();
                    }

                } else {        //无需更新。
                    ContextUtil.toast(context, context.getResources().getString(R.string.update_no));
                }
            }
        });

    }

    /**
     * 根据版本名创建文件
     *
     * @param context
     * @return
     */
    public static File getDownloadFile(Context context, String version) {
        File dir;
        String appName = context.getResources().getString(R.string.app_name);

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = new File(Environment.getExternalStorageDirectory(), appName);
        } else {
            dir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), appName);
        }

        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = appName + version + ".apk";
        return new File(dir, fileName);
    }


    /**
     * 比较本地文件和远程文件大小
     * 如果本地文件小于远程文件大小，则断点续传
     *
     * @param url
     */
    private static void checkFileSize(final Context context, final String url, final String versionName) {
        final File file = getDownloadFile(context, versionName);
        final Handler handler = new Handler(new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                final long localeSize = file.length();
                final long remoteSize = (Long) msg.obj;
                LogCat.d("remoteSize = " + remoteSize + "  localeSize = " + localeSize);
                if (remoteSize <= localeSize) {
                    showInstallDialog(context, file);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    builder.setTitle(R.string.prompt);
                    int downloadPersent = (int) ((float) localeSize / (float) remoteSize * 100);
                    builder.setMessage(context.getString(R.string.continue_download, downloadPersent))
                            .setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new APKDownloadTask(context, localeSize).execute(url, versionName);
                        }
                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    alertDialog.show();
                }
                return false;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendMessage(handler.obtainMessage(1, getRemoteFileSize(url)));
            }
        }).start();

    }

    /**
     * 获取远程文件大小
     *
     * @param url
     * @return
     */
    private static long getRemoteFileSize(String url) {
        long size = 0;
        try {
            HttpURLConnection httpUrl = (HttpURLConnection) (new URL(url)).openConnection();
            size = httpUrl.getContentLength();
            httpUrl.disconnect();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return size;
    }

    private static Intent getInstallIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 安装
     *
     * @param context
     * @param file
     */
    private static void showInstallDialog(Context context, File file) {
        final File f = file;
        final Context mContext = context.getApplicationContext();


        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(R.string.prompt);
        builder.setMessage(context.getString(R.string.continue_download))
                .setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getInstallIntent(f);
                mContext.startActivity(intent);
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();

    }

    /**
     * 下载并安装
     */
    static class DownloadListener implements DialogInterface.OnClickListener {

        private Context mContext;
        private String url;
        private String versionName;

        DownloadListener(Context context, String url, String versionName) {
            this.mContext = context;
            this.url = url;
            this.versionName = versionName;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

            DialogInterface.OnClickListener l = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE: {
                            APKDownloadTask mTask = new APKDownloadTask(mContext, 0l);
                            mTask.execute(url, versionName);
                            break;
                        }
                        case DialogInterface.BUTTON_NEGATIVE: {
                            dialog.dismiss();
                            break;
                        }
                    }
                }
            };
            //检查wifi状态，在非wifi状态下提醒用户，让用户确定下载
            if (!isWifi()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle(R.string.prompt);
                builder.setMessage(mContext.getString(R.string.continue_download))
                        .setCancelable(false).setPositiveButton(R.string.ok, l).setNegativeButton(R.string.cancel, l);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alertDialog.show();

                return;
            } else {
                APKDownloadTask mTask = new APKDownloadTask(mContext, 0l);
                mTask.execute(url, versionName);
            }

        }

        /**
         * 检测wifi是否可用
         *
         * @return
         */
        private boolean isWifi() {
            WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            return manager.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
        }

    }

    /**
     * 下载apk，需要传入url和版本名
     */
    public static class APKDownloadTask extends AsyncTask<String, Long, File> implements Dialog.OnDismissListener {
        NotificationManager manager;
        private Context mContext;
        private ApkProgressDialog dialog;
        private NotificationCompat.Builder builder;
        private Boolean pause = false;
        /*下载开始位置*/
        private Long start;

        /**
         * @param context
         * @param start   文件流的开始位置
         */
        public APKDownloadTask(Context context, Long start) {
            this.mContext = context.getApplicationContext();
            this.start = start;
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        @Override
        protected void onPreExecute() {
            dialog = new ApkProgressDialog(mContext) {
                @Override
                public void onPausePressed() {
                    synchronized (pause) {
                        pause = true;
                    }
                }
            };
            dialog.setOnDismissListener(this);
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();
        }

        @Override
        protected File doInBackground(String... params) {
            String url = params[0];
            String fileName = params[1];
            File apkFile = getDownloadFile(mContext, fileName);
            byte[] bytes = new byte[10 * 1024];
            int len = 0;
            Long downloadLength = start;
            Long remoteSize = getRemoteFileSize(url);
            publishProgress(downloadLength, remoteSize);
            String range = "bytes=" + start + "-" + remoteSize;

            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(apkFile, "rw");
                randomAccessFile.seek(start);
                HttpURLConnection cnn = (HttpURLConnection) new URL(url).openConnection();
                cnn.setRequestProperty("User-Agent", "Net");
                cnn.setRequestProperty("RANGE", range);
                InputStream is = cnn.getInputStream();
                LogCat.i("install apk name:" + apkFile.getName() + " range=" + range);

                while ((len = is.read(bytes)) != -1 && !isPause()) {
                    randomAccessFile.write(bytes, 0, len);
                    downloadLength += len;
                    publishProgress(downloadLength, remoteSize);

                }
                is.close();
                cnn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                if (apkFile != null) {
                    apkFile.delete();
                }
                apkFile = null;
            }
            return apkFile;
        }

        private boolean isPause() {
            synchronized (pause) {
                return pause;
            }
        }

        /**
         * 三个参数：
         * values[0]已经下载的大小
         * values[1]总大小
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Long... values) {
            if (!dialog.isShowing()) {
                dialog.show();
            }
            Long download = values[0];
            Long total = values[1];
            int rate = (int) ((download / (float) total) * 100);
            dialog.setProgress(rate);
            showNotification(rate);
        }

        @Override
        protected void onCancelled(File file) {
            if (file != null) file.delete();
        }

        private void showNotification(int rate) {
            NotificationCompat.Builder build = getNotification();
            build.setContentTitle(String.format("已经下载%d%%", rate));
            build.setProgress(100, rate, false);
            //发出通知
            manager.notify(0, build.build());
        }

        private NotificationCompat.Builder getNotification() {

            if (builder == null) {
                builder = new NotificationCompat.Builder(mContext);
                Intent updateIntent = new Intent(mContext, MainActivity.class);
                PendingIntent updatePendingIntent = PendingIntent.getActivity(mContext, 0, updateIntent, 0);
                builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));
                builder.setSmallIcon(R.drawable.ic_launcher);
                builder.setTicker("正在下载");
                builder.setAutoCancel(false);
                builder.setWhen(0);
                builder.setSound(null);
                builder.setContentIntent(updatePendingIntent);
                //设置通知栏显示内容
            }
            return builder;
        }

        @Override
        protected void onPostExecute(File file) {
            dialog.dismiss();
            if (file == null) {
                ContextUtil.toast(mContext, mContext.getString(R.string.download_fialed));
            } else if (!pause) {
                NotificationCompat.Builder build = getNotification();
                build.setContentTitle(mContext.getString(R.string.click_install, file.getName()));
                Intent intent = getInstallIntent(file);
                build.setContentIntent(PendingIntent.getActivity(mContext, 0, intent, 0));
                build.setAutoCancel(true);
                build.setProgress(100, 100, false);
                NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, build.build());
                showInstallDialog(mContext, file);
            }
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
        }
    }

}
