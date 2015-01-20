package com.jch.lib.util;

import android.content.Context;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 双击点击退出。
 * <p/>
 * Created by ACER on 2015/1/16.
 */
public class DoubleExit {

    private static DoubleExit doubleExit = null;
    private Context context;
    private Timer timer = null;
    private boolean exitFlag = false;

    public static DoubleExit instance(Context context) {

        if (doubleExit == null)
            doubleExit = new DoubleExit(context);
        return doubleExit;
    }

    public void exit() {

        if (!exitFlag) {
            exitFlag = !exitFlag;
            Toast.makeText(context, "再次点击退出", Toast.LENGTH_SHORT).show();
            newTimerSchedule();
        } else
//            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());


    }

    private void newTimerSchedule() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                exitFlag = false;
            }
        }, 2000);

    }

    private DoubleExit(Context context) {
        this.context = context;
    }


}
