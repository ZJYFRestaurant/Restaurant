package com.promote.zjyfrestaurant.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.jch.lib.util.MD5;
import com.jch.lib.util.SharedPreferenceUtil;
import com.promote.zjyfrestaurant.application.HttpConstant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * 獲取手機的基本信息。用於服務交互時。
 *
 * @author Administrator
 */

public class DeviceInfo {


    public static final String MIEI = "miei";
    private static String mCurTime = null;

    /**
     * 机型 。
     *
     * @return
     */
    public static String getUa() {
        return android.os.Build.MODEL;
    }

//	/**
//	 * 屏幕尺寸。
//	 *
//	 * @return
//	 */
//	public static String getScreenSize() {
//
//		Point point = GlobalParameterApplication.instance().getScreenSize();
//		StringBuffer ScreenSb = new StringBuffer();
//		ScreenSb.append(point.x).append("x").append(point.y);
//
//		return ScreenSb.toString();
//	}

    /**
     * 检车是否有simi卡。
     *
     * @return
     */
    public static boolean checkSimCard(Context context) {
        String simi = getImsi(context);

        if (simi == null || simi.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * sim卡号。
     *
     * @param context
     * @return
     */
    public static String getImsi(Context context) {
        String imsi = null;
        TelephonyManager telephonemanage = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonemanage.getSimState() != telephonemanage.SIM_STATE_READY) {
            return null;
        } else {
            imsi = telephonemanage.getSubscriberId();
        }

        return imsi;
    }

    /**
     * 设备id。如果device获取为空，测从preference取出. 如果preference为空就随机生成一个，并保存到preference.
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {

        TelephonyManager telephonemanage = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String miei = telephonemanage.getDeviceId();

        if (miei != null && miei.length() == 14) {
            miei += "1";
        } else if (miei == null) {
            miei = SharedPreferenceUtil.getString(context, MIEI);
        }
        if (miei == null) {
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 15; i++) {
                sb.append(random.nextInt(10));
            }
            miei = sb.toString();
            SharedPreferenceUtil.saveString(context, MIEI, miei);
        }
        return miei;
    }

    /**
     * 国家代码。
     *
     * @param context
     * @return
     */
    public static String getSimCountryIso(Context context) {
        TelephonyManager telephonemanage = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String countryIso = telephonemanage.getSimCountryIso();
        return countryIso;
    }

    /**
     * 获取应用程序版本号。
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {

        int versionCode = 0;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 根据屏幕尺寸判断设备是pad还是phone。
     *
     * @param context
     * @return int:2--pad, 1--phone.
     */
    public static int getDeviceType(Context context) {

        int typeCode = 0;

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        // manager.getDefaultDisplay().getMetrics(dm);
        // int widthPixels =
        // context.getResources().getDisplayMetrics().widthPixels;
        // int heightPixels =
        // context.getResources().getDisplayMetrics().heightPixels;

        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2.0)
                + Math.pow(dm.heightPixels, 2.0));

        double screenSize = diagonalPixels / (160 * dm.density);

        LogCat.v("screenSize----" + Double.toString(screenSize));

        if (screenSize > 7) {
            typeCode = 2;
        } else {
            typeCode = 1;
        }
        return typeCode;
    }

    /**
     * get sys local language.
     *
     * @param context
     * @return str ,
     */
    public static String getLanguage() {

        return Locale.getDefault().getLanguage();
    }

    /**
     * get currrent time。
     * <p/>
     * 将得到的curTime缓存起来，使得与加密是取得的time一致。
     *
     * @return current time. http://116.228.89.242/palmmusic/changeuser
     */
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mCurTime = sdf.format(date);
        return mCurTime;
    }

    /**
     * get the current time token in the first time of a request.
     * <p/>
     * 将得到的curTime缓存起来，使得与加密是取得的time一致。
     *
     * @return
     */
    public static String getmCurTimeToken() {

        Date date = new Date();
        mCurTime = String.valueOf(date.getTime());
        return mCurTime;

    }


    /**
     * 获取加密验证。You must use EbingoRequestParmater at first .
     *
     * @param context
     * @return 加密結果。
     */
    public static String getAuth(Context context) {

        StringBuffer sb = new StringBuffer(getImei(context));

        sb.append(mCurTime);
        sb.append(HttpConstant.KEY);
        MD5 md5 = new MD5();
        return md5.getStrToMD5(sb.toString());
    }


//	/**
//	 * 获取 加密验证。
//	 *
//	 * @param context
//	 * @return ciphertext.
//	 */
//	public static String getAuth(Context context, AuthType type) {
//
//		StringBuffer sb = new StringBuffer("G^dn%($8le-0");
//		sb.append(getUa());
//		sb.append(getImei(context));
//		if (type == AuthType.HOMEAUTH) {
//			sb.append(getVersionCode(context));
//		}
//		sb.append(mCurTime); // 前一次获得getCurrentTime()所获得时间。
//		MD5 md5 = new MD5();
//		LogCat.i("previous auth string---" + sb.toString());
//		return md5.getStrToMD5(sb.toString());
//	}
//
//	/**
//	 * 获取 utf-8编码后的加密验证。
//	 *
//	 * @param context
//	 * @return ciphertext.
//	 */
//	public static String getU8Auth(Context context, AuthType type) {
//
//		StringBuffer sb = new StringBuffer("G^dn%($8le-0");
//		MD5 md5 = new MD5();
//		try {
//			sb.append(URLEncoder.encode(getUa(), "utf-8"));
//			sb.append(getImei(context));
//			sb.append(URLEncoder.encode(mCurTime, "utf-8")); // 前一次获得getCurrentTime()所获得时间。
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		LogCat.i("previous auth string---" + sb.toString());
//		return md5.getStrToMD5(sb.toString());
//	}
}
