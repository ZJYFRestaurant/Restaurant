package com.jch.lib.util;

import android.os.Handler;
import android.os.Message;

/**
 * Created by ACER on 2014/11/26.
 * <p/>
 * 单线程循环。
 */
public class SingleLooper {

    private static final int MSG_WHAT = 555;
    private int duration = 0;
    /**
     * 发送消息的时间间隔。
     */
    private int interval = 0;
    private LooperCallback mLooperCallBack = null;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mLooperCallBack != null) {
                mLooperCallBack.looerCall();
            }
        }
    };

    public SingleLooper(int duration) {

        this.duration = duration;

    }

    /**
     * 开始循环。
     */
    public void startLooper() {

        sendLooperMsg(interval);
    }

    public void setLooperCallBack(LooperCallback mLooperCallBack) {
        this.mLooperCallBack = mLooperCallBack;
    }

    private void sendLooperMsg(int interval) {

        handler.removeMessages(MSG_WHAT);
        handler.sendEmptyMessageDelayed(MSG_WHAT, interval);
    }

    public interface LooperCallback {
        public void looerCall();
    }

}
