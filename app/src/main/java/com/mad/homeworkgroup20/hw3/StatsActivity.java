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
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
String TAG="test";
    TextView percent;
    Button quit;
    Button tryAgain;
    ProgressBar pbPercent;
    ArrayList<Question> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Log.d(TAG, "onCreate: ");
        percent=findViewById(R.id.tvPercent);
        pbPercent=findViewById(R.id.progressBar);
        quit=findViewById(R.id.buttonQuit);
        tryAgain=findViewById(R.id.buttonTryAgain);
        Log.d(TAG, "onCreate: starting");
        // Setting TextView for percent
        int finalscore= this.getIntent().getExtras().getInt(TriviaActivity.SCORE);
        int totalQues=this.getIntent().getExtras().getInt(TriviaActivity.TOTAL_QUES);
        data=  (ArrayList<Question>) this.getIntent().getExtras().getSerializable(MainActivity.KEY_CONTENT);

        float totalPercent= Math.round ( finalscore *100 /totalQues );
        Log.d(TAG, "onCreate: persentage" + finalscore + "  " + totalQues);
         percent.setText(totalPercent+ "%");

        // Quit Button implementation
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StatsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Try Again button implementation
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StatsActivity.this, TriviaActivity.class);
                intent.putExtra(MainActivity.KEY_CONTENT,data);
                startActivity(intent);
            }
        });

        //Progressbar implementation
       pbPercent.setProgress(Math.round(totalPercent));



    }
}
