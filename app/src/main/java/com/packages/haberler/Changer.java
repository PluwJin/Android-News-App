package com.packages.haberler;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Changer extends AsyncTask<String, Void, String> {


    private Context mContext;
    private Changer.OnTaskDoneListener onTaskDoneListener;
    private String urlStr = "";
    String content="";

    public Changer(Context context, String content, Changer.OnTaskDoneListener onTaskDoneListener) {
        this.mContext = context;
        this.urlStr = "http://192.168.1.35/WebService/Save.php";
        this.onTaskDoneListener = onTaskDoneListener;
        this.content=content;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            out.write(content);
            out.close();
            httpCon.getInputStream();
            int responseCode = httpCon.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
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
