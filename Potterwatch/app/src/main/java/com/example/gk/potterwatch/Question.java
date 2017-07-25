package com.example.gk.potterwatch;

import android.content.Context;
import android.content.SharedPreferences;
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

    public String jsonResult;
    private TextView QuestionView;
    private Button One, Two, Three, Four,Timer;

    List<QuestionData> Object = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String trait = sharedPref.getString("HOUSE", "");
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

        Timer = (Button) findViewById(R.id.timer);
        if (QuestionCounter == 0) {
            new getQuestions().execute();
        }

        One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                count.cancel();
                if (AnswerKey.equals("OptionA")) {
                    Toast.makeText(Question.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                    score += 10;
                } else {
                    Toast.makeText(Question.this, "WRONG Answer", Toast.LENGTH_SHORT).show();
                }
                try {
                    if (QuestionCounter < 10) {
                        updateUI();
                    } else {
                        Log.e("SCOREEEEEEEE", String.valueOf(score));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count.cancel();
                if (AnswerKey.equals("OptionB")) {
                    Toast.makeText(Question.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                    score += 10;
                } else {
                    Toast.makeText(Question.this, "WRONG Answer", Toast.LENGTH_SHORT).show();
                }
                try {

                    if (QuestionCounter < 10) {
                        updateUI();
                    } else {
                        Log.e("SCOREEEEEEEE", String.valueOf(score));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count.cancel();
                if (AnswerKey.equals("OptionC")) {
                    Toast.makeText(Question.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                    score += 10;
                } else {
                    Toast.makeText(Question.this, "WRONG Answer", Toast.LENGTH_SHORT).show();
                }
                try {

                    if (QuestionCounter < 10) {
                        updateUI();
                    } else {
                        Log.e("SCOREEEEEEEE", String.valueOf(score));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count.cancel();
                if (AnswerKey.equals("OptionD")) {
                    Toast.makeText(Question.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                    score += 10;
                } else {
                    Toast.makeText(Question.this, "WRONG Answer", Toast.LENGTH_SHORT).show();
                }
                try {

                    if (QuestionCounter < 10) {
                        updateUI();
                    } else {
                        Log.e("SCOREEEEEEEE", String.valueOf(score));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                Request request = new Request.Builder().url("http:192.168.43.232:8080/questions").get().build();
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
        One.setText(temp.getOption1());
        Two.setText(temp.getOption2());
        Three.setText(temp.getOption3());
        Four.setText(temp.getOption4());
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
