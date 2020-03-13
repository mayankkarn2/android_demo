package com.example.demodata;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Contract extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dummy.db";
    private static final int DATABASE_VERSION = 1;
    Context c;
    SQLiteDatabase db;
    public Contract(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getAllTableNames() {
        Cursor c=db.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name!='android_metadata' order by name",null);
        return c;
    }
}
