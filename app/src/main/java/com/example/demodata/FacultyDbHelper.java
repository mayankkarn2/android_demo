package com.example.demodata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FacultyDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "Helper";
    JSONArray JA = new JSONArray();
    private static final String DATABASE_NAME = "faculty.db";
    public static  int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


    public FacultyDbHelper(Context context, JSONArray JA) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.JA = JA;
        Log.v(TAG, String.valueOf(JA));
        db = this.getWritableDatabase();
    }

    public FacultyDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        context = this.context;
        db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        final String SQL_CREATE_BUGS_TABLE = "CREATE TABLE " + FacultyDbContract.FacultyEntry.TABLE_NAME + " (" +
//                FacultyDbContract.FacultyEntry.COLUMN_SNO + " TEXT PRIMARY KEY," +
//                FacultyDbContract.FacultyEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
//                FacultyDbContract.FacultyEntry.COLUMN_ROLE + " TEXT NOT NULL, " +
////                FacultyDbContract.FacultyEntry.COLUMN_DATE + " TEXT NOT NULL, " +
//                FacultyDbContract.FacultyEntry.COLUMN_EXTENSION + " TEXT NOT NULL, " +
//                FacultyDbContract.FacultyEntry.COLUMN_RESIDENCE + " TEXT NOT NULL, " +
//                FacultyDbContract.FacultyEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
//                FacultyDbContract.FacultyEntry.COLUMN_ROOM + " TEXT NOT NULL " + " );";
//
//        db.execSQL(SQL_CREATE_BUGS_TABLE);
//        Log.d(TAG, "Faculty Database Created Successfully" );
//
//        try {
//            Log.v(TAG,"Calling readDataToDb");
//            readDataToDb(db);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("TAG","Upgrade method called");
    }

    private void readDataToDb(SQLiteDatabase db) throws IOException, JSONException {
        Log.v(TAG,"First Step");
        final String FAC_SNO = "S.No.";
        final String FAC_NAME = "Name";
        final String FAC_ROLE = "Role";
        final String FAC_EXTENSION = "Extension";
        final String FAC_RESIDENCE = "Residence";
        final String FAC_EMAIL = "Email";
        final String FAC_ROOM = "Room";
        final String FAC_DATE = date;
        Log.v(TAG, String.valueOf(JA.length()));
        try {
//            JSONArray menuItemsJsonArray = new JSONArray(JA);
            for (int i = 0; i < JA.length(); i++) {
                Log.v(TAG,"readToDB");
                String sno;
                String name;
                String role;
                String extension;
                String residence;
                String mail;
                String room;
                JSONObject menuItemObject = JA.getJSONObject(i);
                sno = menuItemObject.getString(FAC_SNO);
                name = menuItemObject.getString(FAC_NAME);
                role = menuItemObject.getString(FAC_ROLE);
                extension = menuItemObject.getString(FAC_EXTENSION);
                residence = menuItemObject.getString(FAC_RESIDENCE);
                mail = menuItemObject.getString(FAC_EMAIL);
                room = menuItemObject.getString(FAC_ROOM);


                ContentValues menuValues = new ContentValues();

                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_SNO, sno);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_NAME, name);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_ROLE, role);
//                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_DATE, date);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_EXTENSION, extension);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_RESIDENCE, residence);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_EMAIL, mail);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_ROOM, room);

                db.insert(FacultyDbContract.FacultyEntry.TABLE_NAME, null, menuValues);
                Log.d(TAG, "Inserted Successfully " + menuValues );
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

    }


    public Cursor getNames() {
        Cursor cursor = db.rawQuery("select name from faculty ORDER BY sno",null);
        return cursor;
    }

    public void updateDb(JSONArray j) throws IOException, JSONException {
        Log.v(TAG,"Dropping Table");
        String query = "DROP TABLE IF EXISTS faculty";
        db.execSQL(query);
        readDataToDb(db,j);
    }

    private void readDataToDb(SQLiteDatabase db,JSONArray J){
        final String SQL_CREATE_BUGS_TABLE = "CREATE TABLE " + FacultyDbContract.FacultyEntry.TABLE_NAME + " (" +
                FacultyDbContract.FacultyEntry.COLUMN_SNO + " TEXT PRIMARY KEY," +
                FacultyDbContract.FacultyEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                FacultyDbContract.FacultyEntry.COLUMN_ROLE + " TEXT NOT NULL, " +
                FacultyDbContract.FacultyEntry.COLUMN_EXTENSION + " TEXT NOT NULL, " +
                FacultyDbContract.FacultyEntry.COLUMN_RESIDENCE + " TEXT NOT NULL, " +
                FacultyDbContract.FacultyEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                FacultyDbContract.FacultyEntry.COLUMN_ROOM + " TEXT NOT NULL " + " );";

        db.execSQL(SQL_CREATE_BUGS_TABLE);
        Log.v(TAG,"Creating table again from update");
        final String FAC_SNO = "S.No.";
        final String FAC_NAME = "Name";
        final String FAC_ROLE = "Role";
        final String FAC_EXTENSION = "Extension";
        final String FAC_RESIDENCE = "Residence";
        final String FAC_EMAIL = "Email";
        final String FAC_ROOM = "Room";
        try {
//            JSONArray menuItemsJsonArray = new JSONArray(JA);
            for (int i = 0; i < J.length(); i++) {
                String sno;
                String name;
                String role;
                String extension;
                String residence;
                String mail;
                String room;
                JSONObject menuItemObject = J.getJSONObject(i);
                sno = menuItemObject.getString(FAC_SNO);
                name = menuItemObject.getString(FAC_NAME);
                role = menuItemObject.getString(FAC_ROLE);
                extension = menuItemObject.getString(FAC_EXTENSION);
                residence = menuItemObject.getString(FAC_RESIDENCE);
                mail = menuItemObject.getString(FAC_EMAIL);
                room = menuItemObject.getString(FAC_ROOM);

                ContentValues menuValues = new ContentValues();

                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_SNO, sno);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_NAME, name);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_ROLE, role);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_EXTENSION, extension);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_RESIDENCE, residence);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_EMAIL, mail);
                menuValues.put(FacultyDbContract.FacultyEntry.COLUMN_ROOM, room);

                db.insert(FacultyDbContract.FacultyEntry.TABLE_NAME, null, menuValues);
                Log.d(TAG, "Inserted Successfully " + menuValues );
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

    }
}
