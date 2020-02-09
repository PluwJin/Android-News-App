package com.packages.haberler;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64InputStream;
import android.util.JsonReader;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import org.json.*;


public class Recevier extends AsyncTask<String, Void, String> {

    private Context mContext;
    private OnTaskDoneListener onTaskDoneListener;
    private String urlStr = "";

    public Recevier(Context context, String url, OnTaskDoneListener onTaskDoneListener) {
        this.mContext = context;
        this.urlStr = url;
        this.onTaskDoneListener = onTaskDoneListener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL mUrl = new URL(urlStr);
            HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-length", "0");
            httpConnection.setUseCaches(false);
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setConnectTimeout(3000);
            httpConnection.setReadTimeout(3000);
            httpConnection.connect();

            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line="";
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (onTaskDoneListener != null && s != null) {
            onTaskDoneListener.onTaskDone(s);
        } else
            onTaskDoneListener.onError();
    }
    public interface OnTaskDoneListener {
        void onTaskDone(String responseData);
        void onError();
    }

}