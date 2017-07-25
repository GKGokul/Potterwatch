package com.example.gk.potterwatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Question extends TestActivity {

    Counter count;
    public int QuestionCounter = 0;
    public int score = 0;
    public String AnswerKey;
    public int buttonColor,scoreColor;

    public String jsonResult;
    private TextView QuestionView,ScoreView;
    private Button One, Two, Three, Four,Timer;

    List<QuestionData> Object = new ArrayList<>();

    public String trait = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        trait = extras.getString("KEY", "");
        if (trait != null) {
            switch (trait) {
                case "Bravery":
                    setTheme(R.style.Theme_Gryffindor);
                    break;
                case "Loyalty":
                    setTheme(R.style.Theme_Hufflepuff);
                    break;
                case "Wisdom":
                    setTheme(R.style.Theme_Ravenclaw);
                    break;
                case "Cunning":
                    setTheme(R.style.Theme_Slytherin);
                    break;
            }

        }
        setContentView(R.layout.activity_question);

        QuestionView = (TextView) findViewById(R.id.Question);
        One = (Button) findViewById(R.id.Option1);
        Two = (Button) findViewById(R.id.Option2);
        Three = (Button) findViewById(R.id.Option3);
        Four = (Button) findViewById(R.id.Option4);
        ColorDrawable initialButton = (ColorDrawable) One.getBackground();
        ScoreView = (TextView) findViewById(R.id.score);

        buttonColor = initialButton.getColor();
        scoreColor = ScoreView.getCurrentTextColor();

        Timer = (Button) findViewById(R.id.timer);
        if (QuestionCounter == 0) {
            new getQuestions().execute();
        }

        One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ans = One.getText().toString();

                One.setClickable(false);
                Two.setClickable(false);
                Three.setClickable(false);
                Four.setClickable(false);

                if (AnswerKey.equals(ans)) {
                    One.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    count.cancel();
                    score += 10;
                    ScoreView.setText(String.valueOf(score));
                    ScoreView.setTextColor(getResources().getColor(R.color.CorrectAnswer));
                } else {
                    One.setBackgroundColor(getResources().getColor(R.color.WrongAnswer));
                    ScoreView.setTextColor(getResources().getColor(R.color.WrongAnswer));
                    if(Two.getText().toString()==AnswerKey) {
                        Two.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                    else if(Three.getText().toString()==AnswerKey) {
                        Three.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                    else if(Four.getText().toString()==AnswerKey){
                        Four.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if (QuestionCounter < 10) {
                                updateUI();

                            } else {
                                String total = score+" Points";
                                Intent intent = new Intent(Question.this,ResultPage.class);
                                intent.putExtra("KEY",trait);
                                intent.putExtra("POINTS",total);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 3000);

            }
        });

        Two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                One.setClickable(false);
                Two.setClickable(false);
                Three.setClickable(false);
                Four.setClickable(false);

                String ans = Two.getText().toString();

                if (AnswerKey.equals(ans)) {
                    Two.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    count.cancel();
                    score += 10;
                    ScoreView.setText(String.valueOf(score));
                    ScoreView.setTextColor(getResources().getColor(R.color.CorrectAnswer));
                } else {
                    Two.setBackgroundColor(getResources().getColor(R.color.WrongAnswer));
                    ScoreView.setTextColor(getResources().getColor(R.color.WrongAnswer));
                    if(One.getText().toString()==AnswerKey) {
                        One.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                    else if(Three.getText().toString()==AnswerKey) {
                        Three.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                    else if(Four.getText().toString()==AnswerKey){
                        Four.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if (QuestionCounter < 10) {
                                updateUI();

                            } else {
                                String total = score+" Points";
                                Intent intent = new Intent(Question.this,ResultPage.class);
                                intent.putExtra("KEY",trait);
                                intent.putExtra("POINTS",total);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 3000);

            }
        });

        Three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                One.setClickable(false);
                Two.setClickable(false);
                Three.setClickable(false);
                Four.setClickable(false);

                String ans = Three.getText().toString();

                if (AnswerKey.equals(ans)) {
                    Three.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                count.cancel();
                    score += 10;
                    ScoreView.setText(String.valueOf(score));
                    ScoreView.setTextColor(getResources().getColor(R.color.CorrectAnswer));
                } else {
                    Three.setBackgroundColor(getResources().getColor(R.color.WrongAnswer));
                    ScoreView.setTextColor(getResources().getColor(R.color.WrongAnswer));
                    if(Two.getText().toString()==AnswerKey) {
                        Two.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                    else if(One.getText().toString()==AnswerKey) {
                        One.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                    else if(Four.getText().toString()==AnswerKey){
                        Four.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if (QuestionCounter < 10) {
                                updateUI();

                            } else {
                                String total = score+" Points";
                                Intent intent = new Intent(Question.this,ResultPage.class);
                                intent.putExtra("KEY",trait);
                                intent.putExtra("POINTS",total);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 3000);

            }
        });

        Four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                One.setClickable(false);
                Two.setClickable(false);
                Three.setClickable(false);
                Four.setClickable(false);

                String ans = Four.getText().toString();

                if (AnswerKey.equals(ans)) {
                    Four.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    count.cancel();
                    score += 10;
                    ScoreView.setText(String.valueOf(score));
                    ScoreView.setTextColor(getResources().getColor(R.color.CorrectAnswer));
                } else {
                    Four.setBackgroundColor(getResources().getColor(R.color.WrongAnswer));
                    ScoreView.setTextColor(getResources().getColor(R.color.WrongAnswer));
                    if(Two.getText().toString()==AnswerKey) {
                        Two.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                    else if(Three.getText().toString()==AnswerKey) {
                        Three.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                    else if(One.getText().toString()==AnswerKey){
                        One.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                    }
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if (QuestionCounter < 10) {
                                updateUI();

                            } else {
                                String total = score+" Points";
                                Intent intent = new Intent(Question.this,ResultPage.class);
                                intent.putExtra("KEY",trait);
                                intent.putExtra("POINTS",total);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 3000);

            }
        });



    }

    private class getQuestions extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                extractDataFromJSON();
                updateUI();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();

                //TODO: Change IP before running

                Request request = new Request.Builder().url("http:192.168.43.198:8080/questions").get().build();
                Response response = client.newCall(request).execute();
                jsonResult = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void updateUI() throws JSONException {

        QuestionData temp = Object.get(QuestionCounter);
        AnswerKey = temp.getAnswer();
        QuestionView.setText(temp.getQuestion());

        String[] option = new String[4];

        option[0] = temp.getOption1();
        option[1] = temp.getOption2();
        option[2] = temp.getOption3();
        option[3] = temp.getOption4();

        Collections.shuffle(Arrays.asList(option));

        One.setText(option[0]);
        One.setBackgroundColor(buttonColor);
        One.setClickable(true);
        Two.setText(option[1]);
        Two.setBackgroundColor(buttonColor);
        Two.setClickable(true);
        Three.setText(option[2]);
        Three.setBackgroundColor(buttonColor);
        Three.setClickable(true);
        Four.setText(option[3]);
        Four.setBackgroundColor(buttonColor);
        Four.setClickable(true);
        ScoreView.setTextColor(scoreColor);
        QuestionCounter++;
        count = new Counter(11000,100);
        count.start();
    }


    private void extractDataFromJSON() throws JSONException {
        JSONArray array = new JSONArray(jsonResult);

        for (int i = 0; i < 10; i++) {
            JSONObject questions = array.getJSONObject(i);
            JSONObject questionObject = questions.getJSONObject("questions");
            String q, o1, o2, o3, o4, ans;
            q = questionObject.getString("question");
            o1 = questionObject.getString("optionA");
            o2 = questionObject.getString("optionB");
            o3 = questionObject.getString("optionC");
            o4 = questionObject.getString("optionD");
            ans = questionObject.getString("answer");

            Object.add(new QuestionData(q, o1, o2, o3, o4, ans));
        }
    }

    public class Counter extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished){
            Timer.setText(""+(millisUntilFinished/1000));
            if((millisUntilFinished/1000)<5){
                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(50); //You can manage the blinking time with this parameter
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.ABSOLUTE);
                Timer.startAnimation(anim);
                Timer.setBackgroundColor(Color.RED);

            }
        }

        @Override
        public void onFinish() {
            Timer.setText("0");
            Toast.makeText(Question.this, "NOT ANSWERED", Toast.LENGTH_SHORT).show();
        }
    }

}
