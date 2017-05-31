package com.calendartest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/5/26.
 */

//與資料庫互動類別

public class itemDAO {

    public static final String TABEL_NAME = "EventLog";

    //所有欄位
    public static final String KEY_ID = "_id";
    public static final String NAME_COLUMN = "Name";
    public static final String LOCATION_COLUMN = "Location";
    public static final String DATE_FROM_COLUMN = "Date_from";
    public static final String DATE_TO_COLUMN = "Date_to";
    public static final String TIME_FROM_COLUMN = "Time_from";
    public static final String TIME_TO_COLUMN = "Time_to";
    public static final String REPEATFREQUENCY_COLUMN = "RepeatFrequency";
    public static final String PRIVACY_COLUMN = "Privacy";
    public static final String REMIND_COLUMN = "Remind";
    public static final String DESCRIPTION_COLUMN = "Description";
    public static final String COLOR_COLUMN = "Color";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABEL_NAME
                    + " ("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NAME_COLUMN + " TEXT,"
                    + LOCATION_COLUMN + " TEXT,"
                    + DATE_FROM_COLUMN + " TEXT,"
                    + DATE_TO_COLUMN + " TEXT,"
                    + TIME_FROM_COLUMN + " TEXT,"
                    + TIME_TO_COLUMN + " TEXT,"
                    + REPEATFREQUENCY_COLUMN + " TEXT,"
                    + PRIVACY_COLUMN + " TEXT,"
                    + REMIND_COLUMN + " TEXT,"
                    + DESCRIPTION_COLUMN + " TEXT,"
                    + COLOR_COLUMN + " INTEGER)";

    private SQLiteDatabase database;

    public itemDAO(Context context) {   //取得資料庫
        database = DBHelper.getDatabase(context);
    }

    public void close() {
        database.close();
    }

    public Item insert(Item item) { //新增資料

        //存入資料
        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, item.getName());
        cv.put(LOCATION_COLUMN, item.getLocation());
        cv.put(DATE_FROM_COLUMN, item.getDate_from());
        cv.put(DATE_TO_COLUMN, item.getDate_to());
        cv.put(TIME_FROM_COLUMN, item.getTime_from());
        cv.put(TIME_TO_COLUMN, item.getTime_to());
        cv.put(REPEATFREQUENCY_COLUMN, item.getRepeatFrequency());
        cv.put(PRIVACY_COLUMN, item.getPrivacy());
        cv.put(REMIND_COLUMN, item.getRemind());
        cv.put(DESCRIPTION_COLUMN, item.getDescription());
        cv.put(COLOR_COLUMN, item.getColor());

        long id = database.insert(TABEL_NAME, null, cv);
        item.setId(id);

        return item;
    }

    public ArrayList<Item> get(Calendar cal) { //查詢資料
        String date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);

        Item item = new Item();

        // 使用日期為查詢條件
        String where = DATE_FROM_COLUMN + "=\"" + date + "\"";
        Log.d("ItemDao", "get: where " + where);
        // 執行查詢
        Cursor result = database.query(TABEL_NAME, null,
                where, null, null, null, null);


//  Debug State
//        if (result.getCount() > 0) {
//            Log.d("ItemDao", "get: " + result.getCount());
//            result.moveToFirst();
//            Log.d("ItemDao", "move to first");
//        } else {
//            Log.d("ItemDao", "result.getCount() == 0");
//        }

        // 如果有資料
        ArrayList<Item> item_get_all = new ArrayList<>();

        if (result.moveToFirst()) {
            while (result.moveToNext()) {
                item = getRecord(result);
                item_get_all.add(item);
            }
        }

        result.close();

        // 回傳結果
        return item_get_all;
    }

    public Item getRecord(Cursor cursor) {
        Item result = new Item();

        result.setId(cursor.getLong(0));
        result.setName(cursor.getString(1));
        result.setLocation(cursor.getString(2));
        result.setDate_from(cursor.getString(3));
        result.setDate_to(cursor.getString(4));
        result.setTime_from(cursor.getString(5));
        result.setTime_to(cursor.getString(6));
        result.setRepeatFrequency(cursor.getString(7));
        result.setPrivacy(cursor.getString(8));
        result.setRemind(cursor.getString(9));
        result.setDescription(cursor.getString(10));
        result.setColor(cursor.getInt(11));

        return result;
    }

}
