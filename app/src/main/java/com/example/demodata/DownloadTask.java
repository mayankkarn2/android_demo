package com.example.demodata;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadTask extends AsyncTask<String,Void, ArrayList> {
    String line = "";
    String data = "";
    JSONArray JA;
//    Context context;
    private WeakReference<Context> contextRef;
//    private NetworkResponseListener networkResponseListener;

//    public DownloadTask(NetworkResponseListener networkResponseListener) {
//        this.networkResponseListener = networkResponseListener;
//    }

    final static String TAG = "DownloadTask";

    public DownloadTask(Context context) {
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected ArrayList<String> doInBackground(String... voids) {

        Context context = contextRef.get();
        List<String> list= new ArrayList<String>();
        try {
            URL url = new URL("https://28f0d81d.ngrok.io/faculty");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (line != null) {
                line = bufferedReader.readLine();
                data = data+line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v(TAG,data);
        try {
            JA = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FacultyDbHelper dbhelp = new FacultyDbHelper(context,JA);
        try {
            dbhelp.updateDb(JA);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Cursor cu = dbhelp.getNames();
        if(cu.getCount() !=0) {
            while(cu.moveToNext()) {
//                Log.v(TAG,cu.getString(0));
                list.add(cu.getString(0));
            }
        }
        dbhelp.close();
        return (ArrayList<String>) list;
    }
}
