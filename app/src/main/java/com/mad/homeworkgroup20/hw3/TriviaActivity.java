package com.mad.homeworkgroup20.hw3;
/*
* Assignment #: Homework 3
* File Name: Homework3_Group20
* Students: Ankit Kelkar, SHubhra Mishra
* */
import android.app.ProgressDialog;
import android.content.ContentQueryMap;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity  implements  getAsyncImage.IData{

    static final String TRIVIA="trivia_key";

    ArrayList<Question>data;
    Question currentQuestion;
    TextView seconds;
    String TAG ="hw3";
     int score=0;
     static String SCORE="SCORE";
     static String TOTAL_QUES="TOTAL_QUES";
     int checkedId;
    RadioGroup rg;
    static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
      data= (ArrayList<Question>) this.getIntent().getExtras().getSerializable(MainActivity.KEY_CONTENT);
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                seconds = findViewById(R.id.secondsRemaining);
                seconds.setText(millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {

                goToNext();
            }
        }.start();
      if (data.size()>0)
      currentQuestion= data.get(0);
      MapQuestion();
        // TODO: 2/17/2018 add event handler for radio clicks


        // TODO: 2/17/2018 store the userAnswer currentQuestion.userAnswer
       Button quit=(Button) findViewById(R.id.btnQuit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TriviaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button next = (Button) findViewById(R.id.btnNext) ;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestion.Result()){
                    score++;
                }


                if (data.indexOf(currentQuestion)< data.size()-1){
                 //   Log.d(TAG, "onClick: "+data.indexOf(currentQuestion));

                    currentQuestion=data.get(currentQuestion.Index+1);
                    MapQuestion();
                }else{

                    Log.d(TAG, "score is "+score);
                    // TODO: 2/17/2018 pass score to next activity
                    goToNext();

                }

            }
        });
      //in the change event of radio group call currentQuestion.Result() and increment the score if returned true

    }
    protected  void goToNext(){
        Intent intent = new Intent(TriviaActivity.this, StatsActivity.class);
        intent.putExtra(SCORE,score);
        intent.putExtra(TOTAL_QUES, data.size());
        intent.putExtra(MainActivity.KEY_CONTENT,data);
        startActivity(intent);
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
    protected void MapQuestion( ) {
        TextView Qnumber = (TextView) findViewById(R.id.QuestionNumber);
        TextView timer = (TextView) findViewById(R.id.timer);
        TextView Question = (TextView) findViewById(R.id.question);
        Qnumber.setText("Q " + String.valueOf(currentQuestion.Index + 1));
        Question.setText(currentQuestion.Question);
        createRadioButton();


        if (!currentQuestion.ImageURL.isEmpty()) {
            if (isConnected()) {
                new getAsyncImage(TriviaActivity.this, TriviaActivity.this).execute(currentQuestion.ImageURL.toString());
            } else {
                Toast.makeText(TriviaActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } else {
            ImageView im = (ImageView) findViewById(R.id.questionImage);
            im.setVisibility(View.GONE);
        }
    }

        // TODO: 2/17/2018 add programmatically UI for radio buttons
        private void createRadioButton() {
            //final RadioButton[] rb = new RadioButton[5];
            rg = findViewById(R.id.optionGroup); //create the RadioGroup
            rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
            rg.removeAllViews();
            RadioButton[] rb = new RadioButton[currentQuestion.Options.size()];
            for(int i=0; i<currentQuestion.Options.size(); i++){

                rb[i]  = new RadioButton(this);

                rb[i].setText(currentQuestion.Options.get(i));
                rb[i].setId(i);
                rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout

                }
        // Handling the radiobutton click...
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {


                    checkedId = rg.getCheckedRadioButtonId();
                    currentQuestion.UserAnswer=i;

                }
            });
        }


    @Override
    public void handleImage(Bitmap data) {
        Log.d(TAG, "imagedisplay");
        ImageView im = (ImageView) findViewById(R.id.questionImage) ;

        im.setImageBitmap(data);
        im.setVisibility(View.VISIBLE);
    }

}






