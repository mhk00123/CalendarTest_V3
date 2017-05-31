package com.calendartest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2017/5/25.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Event.db";
    public static final int DATABASE_VERSION = 1;

    private static SQLiteDatabase database;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(itemDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public static SQLiteDatabase getDatabase(Context context) { //建立並取得資料庫
        if (database == null || !database.isOpen()) {
            database = new DBHelper(context, DATABASE_NAME,
                    null, DATABASE_VERSION).getWritableDatabase();
        }
        return database;
    }
}
