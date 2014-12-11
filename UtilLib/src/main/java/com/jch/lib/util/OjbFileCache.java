package com.jch.lib.util;

import android.os.Environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ACER on 2014/11/28.
 * <p/>
 * ObjectCache . 将object类型存储到以key_value存储到文件。
 */
public class OjbFileCache {

    private static final String FILEPATH = Environment.getExternalStorageState() + "/" + "OjbCache";

    public static boolean write(Object obj, String key) {
        String path = FILEPATH + "/" + key;
        return writeObj(obj, path);
    }

    public static <T> T read(Class<T> clz, String key) {

        String path = FILEPATH + "/" + key;

        return readObj(clz, path);
    }


    /**
     * Write the obj into the special path file.
     *
     * @param obj
     * @param path
     * @return
     */
    private static boolean writeObj(Object obj, String path) {

        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oo = new ObjectOutputStream(fos);
            oo.writeObject(obj);

            oo.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
        return true;
    }

    /**
     * Read obj from the special path.
     *
     * @param clz
     * @param path
     * @param <T>
     * @return
     */
    private static <T> T readObj(Class<T> clz, String path) {
        T obj = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

}
