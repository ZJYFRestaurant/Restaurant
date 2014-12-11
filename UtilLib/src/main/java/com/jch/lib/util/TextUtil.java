package com.jch.lib.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * 文本工具类
 *
 * @author ty
 */
public class TextUtil {
    /**
     * 得到文件二进制流
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static byte[] getFileContent(String fileName) throws Exception {
        File file = new File(fileName);
        if (file.exists()) {
            InputStream is = new FileInputStream(file);
            return getFileByte(is);
        }
        return null;
    }

    /**
     * 输入流转byte数组
     *
     * @param is
     * @return
     */
    public static byte[] getFileByte(InputStream is) {
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            outStream.close();
            is.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串不能空
     *
     * @param value 要验证的字符串
     * @return
     */
    public static boolean stringIsNotNull(String value) {
        return value != null && value.trim().length() > 0;
    }

    /**
     * 字符串为空
     *
     * @param value 要验证的字符串
     * @return
     */
    public static boolean stringIsNull(String value) {
        return value == null || value.trim().length() <= 0;
    }

    /**
     * @param xml
     * @return
     * @author ty
     * @createdate 2012-6-26 下午10:38:11
     * @Description: 字符串转InputStream
     */
    public static InputStream stringToInputStream(String xml) {
        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
        return stream;
    }

    /**
     * 获取文本内容的长度，中文算一个字符，英文算半个字符，包括标点符号
     *
     * @param str
     * @return
     */
    public static int getTextLengthes(String str) {
        int number = getTextLength(str);
        int length = number / 2;
        if (number % 2 != 0) {
            length += 1;
        }
        str = null;
        return length;
    }

    /**
     * 获取文本内容的长度(中文算两个字符，英文算一个字符)
     *
     * @param str
     * @return
     */
    public static int getTextLength(String str) {
        int length = 0;
        try {
            str = new String(str.getBytes("GBK"), "ISO8859_1");
            length = str.length();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 将输入流解析为String
     *
     * @param is
     * @return
     * @throws Exception
     */
    public static String InputStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                line = null;
            }
        } finally {
//            FileLocalCache.closeBufferedReader(reader);
        }
        line = sb.toString();
        sb = null;
        return line;
    }

    public static String InputStreamToString(InputStream is, String code) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, code));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                line = null;
            }
        } finally {
//            FileLocalCache.closeBufferedReader(reader);
        }
        line = sb.toString();
        sb = null;
        return line;
    }

    /**
     * @param price 需要转换的价格
     * @autor bo.wang
     * @createdate 2012-11-28 上午10:14:47
     * @Description 酒店价格转换
     * 1，100.1转换为101
     * 2，100.000装换为100
     */
    public static String formatPrice(String price) {
        if (price.contains(".")) {
            String price2 = String.valueOf(price).substring(price.lastIndexOf(".") + 1, price.length());
            if (Integer.valueOf(price2) > 0) {
                String price3 = String.valueOf(Integer.valueOf(price.substring(0, price.lastIndexOf("."))) + 1);
                return price3;

            } else {
                String price4 = price.substring(0, price.lastIndexOf("."));
                return price4;
            }
        } else {
            return price;
        }

    }
}
