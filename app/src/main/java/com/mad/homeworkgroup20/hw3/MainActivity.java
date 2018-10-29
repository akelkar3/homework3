package com.mad.homeworkgroup20.hw3;
/*
* Assignment #: Homework 3
* File Name: Homework3_Group20
* Students: Ankit Kelkar, SHubhra Mishra
* */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTriviaContent.IData {
ArrayList<Question> triviaContent;
static final String KEY_CONTENT="content";

    String TAG="hw3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start= (Button)findViewById(R.id.btnStart);
        Button exit = (Button)findViewById(R.id.btnExit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        start.setEnabled(false);
        // TODO: 2/16/2018 make Questions class --done
        // TODO: 2/16/2018 setup async get data task to get data -- done
        // TODO: 2/16/2018 make list of Questions and display image and button --done
        // TODO: 2/16/2018 call another activity and pass the list of image--done
        //exit listener
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent   intent = new Intent(MainActivity.this, TriviaActivity.class);
                intent.putExtra(KEY_CONTENT,triviaContent);
                startActivity(intent);
            }
        });

        //call async method
        new GetTriviaContent(MainActivity.this,MainActivity.this).execute("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");

    }

    @Override
    public void handleData(ArrayList<Question> data) {
        if(data.size()>0){
            triviaContent=data;
            Log.d(TAG, "handleData: "+data.toString());
            //change UI
            Button start= (Button)findViewById(R.id.btnStart);
            start.setEnabled(true);
           TextView tv= (TextView) findViewById(R.id.txtReady);
           tv.setVisibility(View.VISIBLE);
           ImageView iv = (ImageView) findViewById(R.id.imgTrivia);
           iv.setVisibility(View.VISIBLE);
          /*  for (Question temp :data
                 ) {
                Log.d(TAG, "handleData: "+ temp.ImageURL);
            }*/
        //pass data to another activity and start that activity

        }else{
            Log.d(TAG, "handleData: error");
        }

    }
}
