package com.yx.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yx.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    private DBHelper helper;

    public CustomerDao(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加
     *
     * @param data
     */
    public void add(CustomerModel data) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into " + DBHelper.TABLE_NAME + "(" + DBHelper.CUSTOMER_NUMBER + "," + DBHelper.CUSTOMER_NAME + ","
                        + DBHelper.CUSTOMER_TIME + "" + "," + DBHelper.CUSTOMER_TITLE + "," + DBHelper.CUSTOMER_TYPE + ") values(?,?,?,?,?)"
                , new String[]{ data.customer_number + "", data.customer_name, data.customer_time, data.customer_title, data.customer_type + ""});
        db.close();
    }

    /**
     * 批次名称查询
     *
     * @param keyword
     * @return
     */
    public List<CustomerModel> findById(String keyword) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.TABLE_NAME + " where " + DBHelper.CUSTOMER_TITLE + "=?", new String[]{keyword});
        List<CustomerModel> data = new ArrayList<>();

        while (cursor.moveToNext()) {
            CustomerModel d1 = new CustomerModel(cursor.getInt(cursor.getColumnIndex(DBHelper.CUSTOMER_NUMBER)), cursor.getInt(cursor.getColumnIndex(DBHelper.CUSTOMER_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.CUSTOMER_ID)), cursor.getString(cursor.getColumnIndex(DBHelper.CUSTOMER_NAME))
                    , cursor.getString(cursor.getColumnIndex(DBHelper.CUSTOMER_TIME)), cursor.getString(cursor.getColumnIndex(DBHelper.CUSTOMER_TITLE)));
            data.add(d1);
        }
        cursor.close();
        db.close();
        return data;
    }

    /**
     * 全部查询
     *
     * @return
     */
    public List<CustomerModel> findAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.TABLE_NAME + "", null);
        List<CustomerModel> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            CustomerModel d1 = new CustomerModel(cursor.getInt(cursor.getColumnIndex(DBHelper.CUSTOMER_NUMBER)), cursor.getInt(cursor.getColumnIndex(DBHelper.CUSTOMER_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.CUSTOMER_ID)), cursor.getString(cursor.getColumnIndex(DBHelper.CUSTOMER_NAME))
                    , cursor.getString(cursor.getColumnIndex(DBHelper.CUSTOMER_TIME)), cursor.getString(cursor.getColumnIndex(DBHelper.CUSTOMER_TITLE)));
            data.add(d1);
        }
        cursor.close();
        db.close();
        return data;
    }


}
