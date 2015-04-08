package com.promote.zjyfrestaurant.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.promote.zjyfrestaurant.application.Constant;
import com.promote.zjyfrestaurant.util.LogCat;

/**
 * Created by ACER on 2014/12/29.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    //数据库
    public static final String DBNAME = "zjyf.db";

    public static final String CART_TBL = "tbl_cart";


    public DbOpenHelper(Context context) {
        super(context, DBNAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStr = "CREATE TABLE if not exists " + CART_TBL + "(" + "'id' INTEGER PRIMARY KEY, 'name' TEXT, 'category_id' INTEGER, 'old_price' float, 'new_price' float, 'cover' TEXT, 'description' TEXT, 'star' INTEGER, 'is_hot' INTEGER, 'is_show' INTEGER, 'is_order' INTEGER, 'num' INTEGER);";
        LogCat.i("create tbl sql:-=----" + sqlStr);
        db.execSQL(sqlStr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + CART_TBL);
        onCreate(db);
    }
}
