package com.mad.homeworkgroup20.hw3;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/*
* Assignment #: Homework 3
* File Name: Homework3_Group20
* Students: Ankit Kelkar, SHubhra Mishra
* */

public class Question implements Serializable {
    String TAG ="hw3";
    public Question(String inp) {
        String[] response = inp.split(";");
        int noOfOptions= response.length-4;
        Log.d(TAG, "Question: "+noOfOptions);
        ArrayList<String> op = new   ArrayList<String> ();
        for (int i = 0; i<noOfOptions;i++){
        op.add(response[3+i]);
        }

        Index =  Integer.parseInt(response[0]);
        Question =  response[1];
        ImageURL = response[2];
        Options.addAll(op);
        Answer = Integer.parseInt(response[response.length-1]);
    }

    public int Index ;
    public String Question;
    public  String ImageURL;
    public ArrayList<String> Options = new ArrayList<String>() ;
    public int Answer;
    public int UserAnswer=-1;

    public boolean Result(){

        return this.Answer ==this.UserAnswer;
    }


}
