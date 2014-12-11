package com.jch.lib.exception;

import android.util.Log;

/**
 * Created by ACER on 2014/11/27.
 * <p/>
 * 数据为空.
 */
public class NullException extends Exception {

    private String msg = "空指针异常.";

    public NullException(String msg) {
        if (msg != null && !msg.equals(""))
            this.msg = msg;
        Log.e("NullException", msg);
    }
}
