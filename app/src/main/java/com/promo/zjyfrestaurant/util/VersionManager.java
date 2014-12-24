package com.promo.zjyfrestaurant.util;

/**
 * Created by acer on 2014/10/11.
 */
public class VersionManager {

//    /**
//     * 请求版本信息
//     * 1.如果文件已存在就检查文件是否下载完成
//     * 2.如果文件不存在就提示下载
//     * 3.文件名是用Ebingoo+版本名来命名的，如Ebingoo3.1.1.apk
//     *
//     * @param context
//     * @param showDialogIfNewest true如果最新就弹出提示框，false 不弹出提示框
//     */
//    public static void requestVersionCode(Context context, boolean showDialogIfNewest) {
//
//        final Context mContext = context.getApplicationContext();
//        final boolean showNewest = showDialogIfNewest;
//        EbingoRequestParmater param = new EbingoRequestParmater(context);
//
//        HttpUtil.post(HttpConstant.getNewestVersion, param, new JsonHttpResponseHandler("utf-8") {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                LogCat.i(response + "");
//                try {
//                    response = response.getJSONObject("response");
//                    int remoteVersion = response.getInt("version_code");
//                    if (remoteVersion > getLocaleVersion(mContext)) {
//
//                        String url = response.getString("url");
//                        String versionName = response.getString("version");
//                        File apkFile = getDownloadFile(mContext, versionName);
//                        //检查是否已经下载
//                        if (apkFile.exists()) {
//                            checkFileSize(mContext, url, versionName);
//                        } else {//已经下载，用户可以直接安装
//                            EbingoDialog versionDialog = new EbingoDialog(mContext);
//                            versionDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                            versionDialog.setTitle(mContext.getString(R.string.find_new_version, apkFile.getName()));
//                            versionDialog.setMessage(response.getString("msg"));
//                            versionDialog.setPositiveButton(R.string.update_right_now, new DownloadListener(mContext, url, versionName));
//                            versionDialog.setNegativeButton(R.string.cancel, versionDialog.DEFAULT_LISTENER);
//                            versionDialog.show();
//                        }
//                    } else if (showNewest) {
//                        EbingoDialog versionDialog = EbingoDialog.newInstance(mContext, EbingoDialog.DialogStyle.STYLE_I_KNOW);
//                        versionDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                        versionDialog.setMessage("已经是最新版本了！");
//                        versionDialog.show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//                ContextUtil.toast(R.string.net_error);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                ContextUtil.toast(R.string.net_error);
//            }
//
//
//        });
//    }
//
//    /**
//     * 比较本地文件和远程文件大小
//     * 如果本地文件小于远程文件大小，则断点续传
//     *
//     * @param url
//     */
//    private static void checkFileSize(final Context context, final String url, final String versionName) {
//        final File file = getDownloadFile(context, versionName);
//        final Handler handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//
//                final long localeSize = file.length();
//                final long remoteSize = (Long) msg.obj;
//                LogCat.d("remoteSize = " + remoteSize + "  localeSize = " + localeSize);
//                if (remoteSize <= localeSize) {
//                    showInstallDialog(context, file);
//                } else {
//                    EbingoDialog dialog = new EbingoDialog(context);
//                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                    dialog.setTitle(R.string.warn);
//                    int rate = (int) (localeSize / (float) remoteSize * 100);
//                    dialog.setMessage(context.getString(R.string.continue_download, rate));
//                    dialog.setPositiveButton(R.string.continue_, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            new APKDownloadTask(context, localeSize).execute(url, versionName);
//                        }
//                    });
//                    dialog.setNegativeButton(R.string.cancel, dialog.DEFAULT_LISTENER);
//                    dialog.show();
//                }
//                return false;
//            }
//        });
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                handler.sendMessage(handler.obtainMessage(1, getRemoteFileSize(url)));
//            }
//        }).start();
//
//    }
//
//    /**
//     * 获取本地版本号
//     *
//     * @param context
//     * @return
//     */
//
//    private static int getLocaleVersion(Context context) {
//        int localeVersion = 0;
//        try {
//            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
//            localeVersion = info.versionCode;
//            LogCat.i("--->", "versionCode=" + info.versionCode);
//            LogCat.i("--->", "versionName=" + info.versionName);
//            LogCat.i("--->", "packageName=" + info.packageName);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return localeVersion;
//    }
//
//    /**
//     * 根据版本名创建文件
//     *
//     * @param context
//     * @return
//     */
//    public static File getDownloadFile(Context context, String version) {
//        File dir;
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            dir = new File(Environment.getExternalStorageDirectory(), "ebingoo");
//        } else {
//            dir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "ebingoo");
//        }
//
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        String fileName = "Ebingoo" + version + ".apk";
//        return new File(dir, fileName);
//    }
//
//    /**
//     * 获取远程文件大小
//     *
//     * @param url
//     * @return
//     */
//    private static long getRemoteFileSize(String url) {
//        long size = 0;
//        try {
//            HttpURLConnection httpUrl = (HttpURLConnection) (new URL(url)).openConnection();
//            size = httpUrl.getContentLength();
//            httpUrl.disconnect();
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return size;
//    }
//
//    private static Intent getInstallIntent(File file) {
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(file),
//                "application/vnd.android.package-archive");
//        return intent;
//    }
//
//    /**
//     * 安装
//     *
//     * @param context
//     * @param file
//     */
//    private static void showInstallDialog(Context context, File file) {
//        final File f = file;
//        final Context mContext = context.getApplicationContext();
//        EbingoDialog installDialog = new EbingoDialog(context);
//        installDialog.setTitle(R.string.warn);
//        installDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        installDialog.setMessage(context.getString(R.string.click_install, file.getName()));
//        installDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = getInstallIntent(f);
//                mContext.startActivity(intent);
//                dialog.dismiss();
//            }
//        });
//        installDialog.show();
//    }
//
//    /**
//     * 下载并安装
//     */
//    static class DownloadListener implements DialogInterface.OnClickListener {
//
//        private Context mContext;
//        private String url;
//        private String versionName;
//
//        DownloadListener(Context context, String url, String versionName) {
//            this.mContext = context;
//            this.url = url;
//            this.versionName = versionName;
//        }
//
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//
//            DialogInterface.OnClickListener l = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    switch (which) {
//                        case DialogInterface.BUTTON_POSITIVE: {
//                            APKDownloadTask mTask = new APKDownloadTask(mContext, 0l);
//                            mTask.execute(url, versionName);
//                            break;
//                        }
//                        case DialogInterface.BUTTON_NEGATIVE: {
//                            dialog.dismiss();
//                            break;
//                        }
//                    }
//                }
//            };
//            //检查wifi状态，在非wifi状态下提醒用户，让用户确定下载
//            if (!isWifi()) {
//                EbingoDialog wifiDialog = new EbingoDialog(mContext);
//                wifiDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                wifiDialog.setTitle(R.string.warn);
//                wifiDialog.setMessage("检测到当前不是wifi状态，仍然继续下载吗？");
//                wifiDialog.setPositiveButton(R.string.continue_, l);
//                wifiDialog.setNegativeButton(R.string.cancel, l);
//                wifiDialog.show();
//                return;
//            } else {
//                APKDownloadTask mTask = new APKDownloadTask(mContext, 0l);
//                mTask.execute(url, versionName);
//            }
//
//        }
//
//        /**
//         * 检测wifi是否可用
//         *
//         * @return
//         */
//        private boolean isWifi() {
//            WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
//            return manager.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
//        }
//
//    }
//
//    /**
//     * 下载apk，需要传入url和版本名
//     */
//    public static class APKDownloadTask extends AsyncTask<String, Long, File> implements Dialog.OnDismissListener {
//        NotificationManager manager;
//        private Context mContext;
//        private ApkProgressDialog dialog;
//        private NotificationCompat.Builder builder;
//        private Boolean pause = false;
//        /*下载开始位置*/
//        private Long start;
//
//        /**
//         * @param context
//         * @param start   文件流的开始位置
//         */
//        public APKDownloadTask(Context context, Long start) {
//            this.mContext = context.getApplicationContext();
//            this.start = start;
//            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            dialog = new ApkProgressDialog(mContext) {
//                @Override
//                public void onPausePressed() {
//                    synchronized (pause) {
//                        pause = true;
//                    }
//                }
//            };
//            dialog.setOnDismissListener(this);
//            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//            dialog.show();
//        }
//
//        @Override
//        protected File doInBackground(String... params) {
//            String url = params[0];
//            String fileName = params[1];
//            File apkFile = getDownloadFile(mContext, fileName);
//            byte[] bytes = new byte[10 * 1024];
//            int len = 0;
//            Long downloadLength = start;
//            Long remoteSize = getRemoteFileSize(url);
//            publishProgress(downloadLength, remoteSize);
//            String range = "bytes=" + start + "-" + remoteSize;
//
//            try {
//                RandomAccessFile randomAccessFile = new RandomAccessFile(apkFile, "rw");
//                randomAccessFile.seek(start);
//                HttpURLConnection cnn = (HttpURLConnection) new URL(url).openConnection();
//                cnn.setRequestProperty("User-Agent", "Net");
//                cnn.setRequestProperty("RANGE", range);
//                InputStream is = cnn.getInputStream();
//                LogCat.i("install apk name:" + apkFile.getName() + " range=" + range);
//
//                while ((len = is.read(bytes)) != -1 && !isPause()) {
//                    randomAccessFile.write(bytes, 0, len);
//                    downloadLength += len;
//                    publishProgress(downloadLength, remoteSize);
//
//                }
//                is.close();
//                cnn.disconnect();
//            } catch (IOException e) {
//                e.printStackTrace();
//                if (apkFile != null) {
//                    apkFile.delete();
//                }
//                apkFile = null;
//            }
//            return apkFile;
//        }
//
//        private boolean isPause() {
//            synchronized (pause) {
//                return pause;
//            }
//        }
//
//        /**
//         * 三个参数：
//         * values[0]已经下载的大小
//         * values[1]总大小
//         *
//         * @param values
//         */
//        @Override
//        protected void onProgressUpdate(Long... values) {
//            if (!dialog.isShowing()) {
//                dialog.show();
//            }
//            Long download = values[0];
//            Long total = values[1];
//            int rate = (int) ((download / (float) total) * 100);
//            dialog.setProgress(rate);
//            showNotification(rate);
//        }
//
//        @Override
//        protected void onCancelled(File file) {
//            if (file != null) file.delete();
//        }
//
//        private void showNotification(int rate) {
//            NotificationCompat.Builder build = getNotification();
//            build.setContentTitle(String.format("已经下载%d%%", rate));
//            build.setProgress(100, rate, false);
//            //发出通知
//            manager.notify(0, build.build());
//        }
//
//        private NotificationCompat.Builder getNotification() {
//
//            if (builder == null) {
//                builder = new NotificationCompat.Builder(mContext);
//                Intent updateIntent = new Intent(mContext, MainActivity.class);
//                PendingIntent updatePendingIntent = PendingIntent.getActivity(mContext, 0, updateIntent, 0);
//                builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.app_icon));
//                builder.setSmallIcon(R.drawable.app_icon);
//                builder.setTicker("正在下载");
//                builder.setAutoCancel(false);
//                builder.setWhen(0);
//                builder.setSound(null);
//                builder.setContentIntent(updatePendingIntent);
//                //设置通知栏显示内容
//            }
//            return builder;
//        }
//
//        @Override
//        protected void onPostExecute(File file) {
//            dialog.dismiss();
//            if (file == null) {
//                ContextUtil.toast(R.string.download_fialed);
//            } else if (!pause) {
//                NotificationCompat.Builder build = getNotification();
//                build.setContentTitle(mContext.getString(R.string.click_install, file.getName()));
//                Intent intent = getInstallIntent(file);
//                build.setContentIntent(PendingIntent.getActivity(mContext, 0, intent, 0));
//                build.setAutoCancel(true);
//                build.setProgress(100, 100, false);
//                NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//                manager.notify(0, build.build());
//                showInstallDialog(mContext, file);
//            }
//        }
//
//        @Override
//        public void onDismiss(DialogInterface dialog) {
//        }
//    }

}
