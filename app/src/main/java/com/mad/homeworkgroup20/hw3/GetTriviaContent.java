package com.mad.homeworkgroup20.hw3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/*
* Assignment #: Homework 3
* File Name: Homework3_Group20
* Students: Ankit Kelkar, SHubhra Mishra
* */

public class GetTriviaContent extends AsyncTask<String, Void, ArrayList<Question>> {
    String TAG="hw3";
    String line="";
    private ProgressDialog dialog;
    IData iData;

    public GetTriviaContent(IData idata, MainActivity activity) {
        this.iData = idata;
        this.dialog= new ProgressDialog(activity);
    }
        @Override
        protected  ArrayList<Question> doInBackground(String... params) {
           // StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            ArrayList<Question>result=new ArrayList<Question>();

           // String result = null;
            Log.d(TAG, "back");
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    while ((line = reader.readLine()) != null) {

                           // stringBuilder.append(line);
                            result.add(new Question(line));
                    }
                 //   result = stringBuilder.toString();

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        protected void onPostExecute(ArrayList<Question> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(result!=null)
            {
                Log.d(TAG, "inpost");
                if (iData!=null) {
                    iData.handleData(result);
                }
            } else {
                Log.d(TAG, "null result");
            }
        }
    public static  interface IData{
        public void handleData(ArrayList<Question> data);
    }
    }
