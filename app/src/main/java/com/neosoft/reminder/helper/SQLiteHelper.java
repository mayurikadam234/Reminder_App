package com.neosoft.reminder.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;
import android.util.Log;

import com.neosoft.reminder.model.EventDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by webwerks1 on 5/4/17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "androidsqlite.db";
    private static final String TABLE_EVENT = "Event";
    private static final String KEY_DATE = "date";
    private static final String KEY_DAY = "day";
    private static final String KEY_MONTH = "month";
    private static final String KEY_YEAR = "year";
    private static final String KEY_TITLE="task_title";
    private static final String KEY_TASK_DETAIL="task_detail";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT
                + "("  + KEY_DATE + " TEXT,"
                + KEY_DAY + " INTEGER, "
                + KEY_MONTH + " TEXT, "
                + KEY_YEAR + " TEXT, "
                + KEY_TITLE + " TEXT, "
                + KEY_TASK_DETAIL + " TEXT"
                +")";
        db.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addEvent(String title, String date,String details,String day,String month,String year){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_TITLE, title);
        values.put(KEY_TASK_DETAIL, details);
        values.put(KEY_DAY, day);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        // Inserting Row
        long id = db.insert(TABLE_EVENT, null, values);
        db.close();

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public List<EventDetails> getEventDeatils(int currentMonth) {
        List<EventDetails> eventDetails=new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_EVENT+" WHERE "+KEY_MONTH+"="+(currentMonth)+" ORDER BY "+ KEY_DAY + " ASC";
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();

        if (c.moveToFirst()) {
            do {
                EventDetails list=new EventDetails();
                list.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                list.setDay(c.getString(c.getColumnIndex(KEY_DAY)));
                list.setMonth(c.getString(c.getColumnIndex(KEY_MONTH)));
                list.setYear(c.getString(c.getColumnIndex(KEY_YEAR)));
                list.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                list.setDetails(c.getString(c.getColumnIndex(KEY_TASK_DETAIL)));

                eventDetails.add(list);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return eventDetails;
    }

}
