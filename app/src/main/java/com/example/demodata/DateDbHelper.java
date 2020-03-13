package com.example.demodata;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import static android.app.Service.START_STICKY;

public class DateDbHelper extends SQLiteOpenHelper {

    public static String TAG = "Date";
    private static final String DATABASE_NAME = "date.db";
    private static final int DATABASE_VERSION = 1;
    String date;
    Context context;
    SQLiteDatabase db;

    public DateDbHelper(Context context,String date) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.date = date;
        db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_BUGS_TABLE = "CREATE TABLE " + DateDbContract.DateEntry.TABLE_NAME + " (" +
                DateDbContract.DateEntry.COLUMN_TODAY + " TEXT PRIMARY KEY" + " );";
        db.execSQL(SQL_CREATE_BUGS_TABLE);
        Log.d(TAG, "Date Database Created Successfully" );

        readDataToDb(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private void readDataToDb(SQLiteDatabase db) {
        ContentValues menuValues = new ContentValues();
        menuValues.put(DateDbContract.DateEntry.COLUMN_TODAY, date);
        db.insert(DateDbContract.DateEntry.TABLE_NAME, null, menuValues);
        Log.d(TAG, "Date Inserted Successfully " + menuValues );
    }

    public boolean doesTableExist(String tableName) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }


}
