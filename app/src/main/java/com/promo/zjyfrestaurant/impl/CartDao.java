package com.promo.zjyfrestaurant.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.promo.zjyfrestaurant.bean.DishBean;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/12/29.
 */
public class CartDao {

    private static DbOpenHelper mHelper;
    private Context context;

    public CartDao(Context context) {

        this.context = context;
        mHelper = new DbOpenHelper(context);
    }

    /**
     * 向数据库中添加所点菜单。
     *
     * @param dish 菜品。
     */
    public synchronized void addDish(DishBean dish) {

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", dish.getId());
        values.put("name", dish.getName());
        values.put("category_id", dish.getCategory_id());
        values.put("old_price", dish.getOld_price());
        values.put("new_price", dish.getNew_price());
        values.put("cover", dish.getCover());
        values.put("description", dish.getDescription());
        values.put("star", dish.getStar());
        values.put("is_hot", dish.getIs_hot());
        values.put("is_show", dish.getIs_show());
        values.put("num", dish.getNum());
        values.put("is_order", dish.getIsOrder());

        db.insert(DbOpenHelper.CART_TBL, null, values);
        db.close();

    }

    /**
     * 跟新数据。
     *
     * @param dish
     */
    public synchronized void updateDish(DishBean dish) {

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("num", dish.getNum());
        values.put("is_order", dish.getIsOrder());

        String whereStr = "id = ?";
        db.update(DbOpenHelper.CART_TBL, values, whereStr, new String[]{String.valueOf(dish.getId())});
        db.close();
    }

    /**
     * 清空购物车。
     */
    public synchronized void clearCart() {

        SQLiteDatabase db = mHelper.getWritableDatabase();

        String sqlStr = "delete from " + DbOpenHelper.CART_TBL;
        db.execSQL(sqlStr);
        db.close();
    }

    /**
     * 查询。
     *
     * @return
     */
    public synchronized ArrayList<DishBean> getDishes() {

        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(DbOpenHelper.CART_TBL, null, null, null, null, null, null);
        ArrayList<DishBean> dishBeans = new ArrayList<>();
        while (cursor.moveToNext()) {
            DishBean dishBean = new DishBean();
            dishBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            dishBean.setNum(cursor.getInt(cursor.getColumnIndex("num")));
            dishBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            dishBean.setCategory_id(cursor.getInt(cursor.getColumnIndex("category_id")));
            dishBean.setCover(cursor.getString(cursor.getColumnIndex("cover")));
            dishBean.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            dishBean.setIs_hot(cursor.getInt(cursor.getColumnIndex("is_hot")));
            dishBean.setIs_show(cursor.getInt(cursor.getColumnIndex("is_show")));
            dishBean.setNew_price(cursor.getInt(cursor.getColumnIndex("new_price")));
            dishBean.setOld_price(cursor.getInt(cursor.getColumnIndex("old_price")));
            dishBean.setStar(cursor.getInt(cursor.getColumnIndex("star")));
            dishBean.setIsOrder(cursor.getInt(cursor.getColumnIndex("is_order")));
            dishBeans.add(dishBean);
        }

        cursor.close();
        db.close();

        return dishBeans;

    }
}
