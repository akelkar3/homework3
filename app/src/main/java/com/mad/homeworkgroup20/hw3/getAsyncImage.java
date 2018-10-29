package com.mad.homeworkgroup20.hw3;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/*
* Assignment #: Homework 3
* File Name: Homework3_Group20
* Students: Ankit Kelkar, SHubhra Mishra
* */
public class getAsyncImage extends AsyncTask<String, Void,Bitmap>
{   String TAG="hw3";
    IData iData;
    Bitmap result=null;
    HttpURLConnection connection=null;
    private ProgressDialog dialog;

    public getAsyncImage(IData idata, TriviaActivity activity) {
        this.iData = idata;
        this.dialog= new ProgressDialog(activity);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            Log.d(TAG, "connection open");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "result");
                result = BitmapFactory.decodeStream(connection.getInputStream());
                Log.d(TAG, "result 2");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        } finally {
            //Close open connections and reader
            if (connection != null) {
                connection.disconnect();
            }

        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading");
        dialog.show();
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        //  ImageView iv=(ImageView)findViewById(R.id.imageView);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if(result!=null)
        {
            //  iv.setImageBitmap(bitmap);
            Log.d(TAG, "inpost");
            if (iData!=null) {
                iData.handleImage(result);
            }
        }
    }
    public static  interface IData{
        public void handleImage(Bitmap data);
    }
}