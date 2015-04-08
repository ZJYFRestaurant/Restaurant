package com.promote.zjyfrestaurant.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.promote.zjyfrestaurant.application.Constant;
import com.promote.zjyfrestaurant.util.LogCat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ACER on 2015/1/21.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {


    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static final String V_NAME_KEY = "Versionname";
    private static final String V_CODE_KEY = "VersionCode";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");

    private static CrashHandler instance;

    private Context mContext;

    private Map<String, String> infos = new HashMap<>();


    private CrashHandler() {

    }

    /**
     * 单例模式。
     *
     * @return
     */
    public static CrashHandler newInstance() {

        if (instance == null)
            instance = new CrashHandler();
        return instance;

    }

    public void init(Context context) {

        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        if (!caughtException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);

        } else {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("exception", "error : ", e);
            }

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean caughtException(Throwable ex) {

        if (ex == null)
            return false;

        new Thread() {
            @Override
            public void run() {

                Looper.prepare();
                Toast.makeText(mContext, "很抱歉程序出现异常，即将退出。", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        collectionDeviceInfo(mContext);
        saveCrashInfo2File(ex);

        return true;
    }

    /**
     * 收集错误信息。
     *
     * @param cxt
     */
    private void collectionDeviceInfo(Context cxt) {

        PackageManager pm = cxt.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(cxt.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName;
                String versionCode = String.valueOf(pi.versionCode);

                infos.put(V_NAME_KEY, versionName);
                infos.put(V_CODE_KEY, versionCode);
            }

        } catch (PackageManager.NameNotFoundException e) {
            Log.e("exception", "Error while collect package info", e);
        }

        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        // 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                //setAccessible( boolean flag)
                // 将此对象的 accessible 标志设置为指示的布尔值。
                // 通过设置Accessible属性为true,才能对私有变量进行访问，不然会得到一个IllegalAccessException的异常
                field.setAccessible(true);

                infos.put(field.getName(), field.get(null).toString());

                LogCat.d("exception", field.getName() + ":" + field.get(null));
            } catch (IllegalAccessException e) {
                LogCat.e("exception", "an error occured when collect crash info", e);
            }


        }
    }

    /**
     * 保存错误信息到文件中。
     *
     * @param ex
     * @return 返回文件名称，便于将文件传输到服务器。
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {

            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + " = " + value);
        }

        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = ex.getCause();
        }

        pw.close();
        String result = writer.toString();
        sb.append(result);

        long timestamp = System.currentTimeMillis();
        String time = sdf.format(new Date());
        String fileName = "crash-" + time + "-" + timestamp + ".log";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            String path = Constant.CACHE_DIR + "/crash/";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdir();
            try {
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();

                return fileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

}
