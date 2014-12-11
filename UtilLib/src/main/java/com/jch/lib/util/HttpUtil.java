package com.jch.lib.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

    public static final int OK = 0x111111;
    public static final int FAILE = 0x222222;
    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * 判断网络是否可用.
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    static {
        client.setTimeout(10000); // 链接超时。
    }

    /**
     * 不带参的获取string对象的get方法。
     *
     * @param urlStr
     * @param res
     */
    public static void get(String urlStr, AsyncHttpResponseHandler res) {
        client.get(urlStr, res);
    }

    /**
     * 带参的获取string对象的get方法。
     *
     * @param urlStr
     * @param res
     */
    public static void get(String urlStr, RequestParams params, AsyncHttpResponseHandler res) {
        client.get(urlStr, params, res);
    }

    /**
     * 不带参的获取json对象或数组的get方法。
     *
     * @param urlStr
     * @param res
     */
    public static void get(String urlStr, JsonHttpResponseHandler res) {
        client.get(urlStr, res);
    }

    /**
     * 带参的获取json对象或数组的get方法。
     *
     * @param urlStr
     * @param res
     */
    public static void get(String urlStr, RequestParams params, JsonHttpResponseHandler res) {
        client.get(urlStr, params, res);
    }

    /**
     * 下载数据时，不带参的获取json对象或数组的get方法。
     *
     * @param urlStr
     */
    public static void get(String urlStr, BinaryHttpResponseHandler bHandler) {
        client.get(urlStr, bHandler);
    }

    /**
     * 不带参的获取string对象的get方法。
     *
     * @param urlStr
     * @param res
     */
    public static void post(String urlStr, AsyncHttpResponseHandler res) {
        client.post(urlStr, res);
    }

    /**
     * 带参和contentType的获取string的post方法。
     *
     * @param context
     * @param urlStr
     * @param entity
     * @param contentType
     * @param res
     */
    public static void post(Context context, String urlStr, RequestParams params, String contentType,
                            AsyncHttpResponseHandler res) {
        client.post(context, urlStr, paramsToEntity(params, res), contentType, res);
    }

    /**
     * 带参的获取string对象的post方法。
     *
     * @param urlStr
     * @param res
     */
    public static void post(String urlStr, RequestParams params, AsyncHttpResponseHandler res) {
        client.post(urlStr, params, res);
    }

    /**
     * 不带参的获取json对象或数组的post方法。
     *
     * @param urlStr
     * @param res
     */
    public static void post(String urlStr, JsonHttpResponseHandler res) {
        client.post(urlStr, res);
    }

    /**
     * 带参的获取json对象或数组的post方法。
     *
     * @param urlStr
     * @param res
     */
    public static void post(String urlStr, RequestParams params, JsonHttpResponseHandler res) {
        Log.i("--->", params.toString());
        client.post(urlStr, params, res);
    }

    /**
     * 带参和contentType的获取json对象或数组的post方法。
     *
     * @param context
     * @param urlStr
     * @param entity
     * @param contentType
     * @param res
     */
    public static void post(Context context, String urlStr, RequestParams params, String contentType, JsonHttpResponseHandler res) {
        try {
            StringEntity entity = new StringEntity(params.toString());
            client.post(context, urlStr, entity, contentType, res);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

//	/**
//	 * 可传送contentype 和 params 的获取json 对象或数组的post方法。
//	 *
//	 * @param context
//	 * @param urlStr
//	 * @param params
//	 *            inputStream and String type params.
//	 * @param contentType
//	 * @param res
//	 */
//	public static void postStream(Context context, String urlStr, RequestParams params, String contentType,
//			JsonHttpResponseHandler res) {
//
//		client.post(context, urlStr, params, contentType, res);
//
//	}

    /**
     * Returns HttpEntity containing data from RequestParams included with
     * request declaration. Allows also passing progress from upload via
     * provided ResponseHandler
     *
     * @param params          additional request params
     * @param responseHandler ResponseHandlerInterface or its subclass to be notified on
     *                        progress
     */
    private static HttpEntity paramsToEntity(RequestParams params, ResponseHandlerInterface responseHandler) {

        HttpEntity entity = null;

        try {
            if (params != null) {
                entity = params.getEntity(responseHandler);
            }
        } catch (IOException e) {
            if (responseHandler != null) {
                responseHandler.sendFailureMessage(0, null, null, e);
            } else {
                e.printStackTrace();
            }
        }

        return entity;

    }

    /**
     * 下载数据时，不带参的获取json对象或数组的post方法。
     *
     * @param urlStr
     * @param res
     */
    public static void post(String urlStr, BinaryHttpResponseHandler bHandler) {
        client.post(urlStr, bHandler);
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    public static String SimpleHttp(String urlStr) throws Exception {

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        int resCode = conn.getResponseCode();
        InputStream input = null;
        String result = null;
        if (resCode == 200) {
            input = conn.getInputStream();
            result = toString(input);
            input.close();
            conn.disconnect();
        } else {
            throw new Exception("connect failed");
        }

        return result;
    }

    private static String toString(InputStream input) {

        String content = null;
        try {
            InputStreamReader ir = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(ir);

            StringBuilder sbuff = new StringBuilder();
            while (null != br) {
                String temp = br.readLine();
                if (null == temp)
                    break;
                sbuff.append(temp).append(System.getProperty("line.separator"));
            }

            content = sbuff.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * 下载数据.
     *
     * @param savePath
     * @param url
     * @param callback
     */
    public void download(final String saveDir, final String name, final String url, final Handler callbackHandler) {

        get(url, new BinaryHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {

                File dirFile = new File(saveDir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }

                File file = new File(dirFile, name);
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(binaryData);
                    outputStream.flush();
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Message msg = new Message();
                msg.obj = saveDir + name;
                msg.what = OK;
                callbackHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
//				LogCat.e("down load file error..." + error.toString());
                callbackHandler.sendEmptyMessage(FAILE);
            }
        });
    }

    /**
     * httpclient 请求
     */
    public void httpDownload(final String saveDir, final String name, final String url, final Handler callbackHandler) {

        URL CMPPSend;
        URLConnection URLConn = null;
        String sInputLine = null;
        String sResult = "";

        File dirFile = new File(saveDir);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }

        File file = new File(dirFile, name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Message msg = new Message();
        msg.obj = saveDir + name;
        try {
            FileOutputStream outputStream = new FileOutputStream(file);

            CMPPSend = new URL(url);
            URLConn = CMPPSend.openConnection();

            URLConn.setConnectTimeout(20000);
            URLConn.setReadTimeout(20000);

            byte[] bytes = new byte[1024];
            int c;
            InputStream in = URLConn.getInputStream();
            while ((c = in.read(bytes)) != -1) {
                for (int i = 0; i < c; i++) {
                    outputStream.write(bytes[i]);
                }
            }
            in.close();
            outputStream.close();

            msg.what = OK;

        } catch (IOException e) {
            msg.what = FAILE;
            e.printStackTrace();
        }
        callbackHandler.sendMessage(msg);
    }
}
