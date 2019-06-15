package com.yx.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "customer";
    public static final String CUSTOMER_NUMBER = "customer_number";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_TIME = "customer_time";
    public static final String CUSTOMER_TYPE = "customer_type";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_TITLE = "customer_title";

    public DBHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ( "
                + CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CUSTOMER_NUMBER + " INTEGER, "
                + CUSTOMER_NAME + " TEXT,"
                + CUSTOMER_TIME + " TEXT, "
                + CUSTOMER_TITLE + " TEXT, "
                + CUSTOMER_TYPE + " INTEGER)";
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
