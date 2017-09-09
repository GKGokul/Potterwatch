package com.example.gk.potterwatch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/*
class Utility {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
*/


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Question extends TestActivity {

    Counter count;
    public int QuestionCounter = 0;
    public int score = 0;
    public int compScore = 0;
    public String AnswerKey;
    public int buttonColor,scoreColor;
    public String[] option = new String[4];

    public String jsonResult;
    private TextView QuestionView,ScoreView,compScoreView;
    private Button One, Two, Three, Four,Timer;
    Drawable drawable;
    public String compAnswer;
    public boolean isAnswered,compIsAnswered,isCorrect;

    public int compTime;


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
        // USE THIS FOR ALERT:
        //boolean status = Utility.isNetworkAvailable(Question.this);
/*
        if(status == false)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Please connect to the internet");
            alert.show();
        }

        else
        {


*/
            // Initializing view for question and the four options
            QuestionView = (TextView) findViewById(R.id.Question);
            One = (Button) findViewById(R.id.Option1);
            Two = (Button) findViewById(R.id.Option2);
            Three = (Button) findViewById(R.id.Option3);
            Four = (Button) findViewById(R.id.Option4);

            //Initialize TODO: Complete here
            ColorDrawable initialButton = (ColorDrawable) One.getBackground();
            //Initialize view for the score of the first player and opponent
            ScoreView = (TextView) findViewById(R.id.score);
            compScoreView = (TextView) findViewById(R.id.comp_score);

            buttonColor = initialButton.getColor();
            scoreColor = ScoreView.getCurrentTextColor();

            // Initialize the timer
            Timer = (Button) findViewById(R.id.timer);

            drawable = Timer.getBackground();

            if (QuestionCounter == 0) {
                new getQuestions(Question.this).execute();
            }

            One.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String ans = One.getText().toString();

                    isAnswered = true;
                    One.setClickable(false);
                    Two.setClickable(false);
                    Three.setClickable(false);
                    Four.setClickable(false);

                    if (AnswerKey.equals(ans)) {
                        One.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        isCorrect = true;
                        score += 10;
                        ScoreView.setText(String.valueOf(score));
                        ScoreView.setTextColor(getResources().getColor(R.color.CorrectAnswer));
                        answerCheck();
                    } else {
                        One.setBackgroundColor(getResources().getColor(R.color.WrongAnswer));
                        ScoreView.setTextColor(getResources().getColor(R.color.WrongAnswer));

                        if(Two.getText().toString().equals(AnswerKey)) {
                            Two.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        }
                        else if(Three.getText().toString().equals(AnswerKey)) {
                            Three.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        }
                        else if(Four.getText().toString().equals(AnswerKey)){
                            Four.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        }

                        if(compIsAnswered) {
                            answerCheck();
                        }
                    }
                }
            });

            Two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    isAnswered = true;
                    One.setClickable(false);
                    Two.setClickable(false);
                    Three.setClickable(false);
                    Four.setClickable(false);

                    String ans = Two.getText().toString();

                    if (AnswerKey.equals(ans)) {
                        Two.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        isCorrect = true;
                        score += 10;
                        ScoreView.setText(String.valueOf(score));
                        ScoreView.setTextColor(getResources().getColor(R.color.CorrectAnswer));
                        answerCheck();
                    } else {
                        Two.setBackgroundColor(getResources().getColor(R.color.WrongAnswer));
                        ScoreView.setTextColor(getResources().getColor(R.color.WrongAnswer));

                        if(One.getText().toString().equals(AnswerKey)) {
                            One.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        }
                        else if(Three.getText().toString().equals(AnswerKey)) {
                            Three.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        }
                        else if(Four.getText().toString().equals(AnswerKey)){
                            Four.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        }
                        if(compIsAnswered) {
                            answerCheck();
                        }
                    }

                }
            });

            Three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    isAnswered = true;
                    One.setClickable(false);
                    Two.setClickable(false);
                    Three.setClickable(false);
                    Four.setClickable(false);

                    String ans = Three.getText().toString();

                    if (AnswerKey.equals(ans)) {
                        Three.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        isCorrect = true;
                        score += 10;
                        ScoreView.setText(String.valueOf(score));
                        ScoreView.setTextColor(getResources().getColor(R.color.CorrectAnswer));
                        answerCheck();
                    } else {
                        Three.setBackgroundColor(getResources().getColor(R.color.WrongAnswer));
                        ScoreView.setTextColor(getResources().getColor(R.color.WrongAnswer));

                        if (Two.getText().toString().equals(AnswerKey)) {
                            Two.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        } else if (One.getText().toString().equals(AnswerKey)) {
                            One.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        } else if (Four.getText().toString().equals(AnswerKey)) {
                            Four.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        }
                        if(compIsAnswered) {
                            answerCheck();
                        }
                    }
                }
            });

            Four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    isAnswered = true;
                    One.setClickable(false);
                    Two.setClickable(false);
                    Three.setClickable(false);
                    Four.setClickable(false);

                    String ans = Four.getText().toString();

                    if (AnswerKey.equals(ans)) {
                        Four.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        isCorrect = true;
                        score += 10;
                        ScoreView.setText(String.valueOf(score));
                        ScoreView.setTextColor(getResources().getColor(R.color.CorrectAnswer));
                        answerCheck();

                    } else {
                        Four.setBackgroundColor(getResources().getColor(R.color.WrongAnswer));
                        ScoreView.setTextColor(getResources().getColor(R.color.WrongAnswer));
                        if(Two.getText().toString().equals(AnswerKey)) {
                            Two.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        }
                        else if(Three.getText().toString().equals(AnswerKey)) {
                            Three.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        }
                        else if(One.getText().toString().equals(AnswerKey)){
                            One.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                        }
                        if(compIsAnswered) {
                            answerCheck();
                        }
                    }
                }
            });

      //  }
    }

    private class getQuestions extends AsyncTask<String, String, String> {

        //ProgressDialog declaration
        private ProgressDialog dialog;
        android.app.Activity Activity;

        //Constructor to initialize progressDialog
        public getQuestions(Activity Activity) {
            this.Activity = Activity;
            context = Activity;
            dialog = new ProgressDialog(context);
        }

        private Context context;


        protected void onPreExecute() {
            //Sets Dialog message
            this.dialog.setMessage("Loading...");
            //Shows Dialog
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                extractDataFromJSON();
                if(dialog.isShowing()) {
                    //Dismisses dialog
                    dialog.dismiss();
                }
                updateUI();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();

                Request request = new Request.Builder().url("https://potterwatch.herokuapp.com/questions").get().build();
                Response response = client.newCall(request).execute();
                jsonResult = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateUI() throws JSONException {

        QuestionData temp = Object.get(QuestionCounter);
        AnswerKey = temp.getAnswer();
        QuestionView.setText(temp.getQuestion());

        option[0] = temp.getOption1();
        option[1] = temp.getOption2();
        option[2] = temp.getOption3();
        option[3] = temp.getOption4();

        Collections.shuffle(Arrays.asList(option));

        Timer.setBackground(drawable);

        isAnswered = false;
        compIsAnswered = false;
        isCorrect = false;

        if(score>compScore) {
            compTime = ThreadLocalRandom.current().nextInt(750,1501);
        }
        else {
            compTime = ThreadLocalRandom.current().nextInt(1000,3001);
        }

        ScoreView.setTextColor(scoreColor);
        compScoreView.setTextColor(scoreColor);
        QuestionCounter++;

        One.setText("");
        One.setBackgroundColor(buttonColor);
        Two.setText("");
        Two.setBackgroundColor(buttonColor);
        Three.setText("");
        Three.setBackgroundColor(buttonColor);
        Four.setText("");
        Four.setBackgroundColor(buttonColor);

        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                One.setText(option[0]);

                One.setClickable(true);
                Two.setText(option[1]);
                Two.setClickable(true);
                Three.setText(option[2]);
                Three.setClickable(true);
                Four.setText(option[3]);
                Four.setClickable(true);

                count = new Counter(11000,100);
                count.start();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!isCorrect)
                            compTurn();
                    }
                },compTime);
            }
        },1500);

    }

    private void compTurn() {
        String[] compOption = new String[2];

        int ansIndex=0;

        for(int i=0;i<4;i++) {
            if(option[i].equals(AnswerKey)) {
                compOption[0] = option[i];
                ansIndex = i;
                break;
            }
        }

        int compAnsKey = ansIndex;

        while(compAnsKey==ansIndex) {
            compAnsKey = ThreadLocalRandom.current().nextInt(0,4);
        }

        compOption[1] = option[compAnsKey];

        compAnsKey = ThreadLocalRandom.current().nextInt(0,2);

        if(score>compScore) {
            compAnswer = compOption[0];
        }
        else {
            if(compAnsKey == 0) {
                compAnswer = compOption[0];
            }
            else {
                compAnswer = compOption[1];
            }
        }

        if(One.getText().equals(compAnswer)) {
            One.setBackgroundColor(getResources().getColor(R.color.CompAnswer));
        }
        else if(Two.getText().equals(compAnswer)) {
            Two.setBackgroundColor(getResources().getColor(R.color.CompAnswer));
        }
        else if(Three.getText().equals(compAnswer)) {
            Three.setBackgroundColor(getResources().getColor(R.color.CompAnswer));
        }
        else if(Four.getText().equals(compAnswer)) {
            Four.setBackgroundColor(getResources().getColor(R.color.CompAnswer));
        }

        compIsAnswered = true;

        if(compAnswer.equals(AnswerKey)) {
            One.setClickable(false);
            Two.setClickable(false);
            Three.setClickable(false);
            Four.setClickable(false);
            compScore+=10;
            compScoreView.setText(String.valueOf(compScore));
            compScoreView.setTextColor(getResources().getColor(R.color.CorrectAnswer));
            answerCheck();
        }
        else if(!compAnswer.equals(AnswerKey) && isAnswered) {
            compScoreView.setTextColor(getResources().getColor(R.color.WrongAnswer));
            answerCheck();
        }
    }

    public void answerCheck() {
        count.cancel();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                    if (QuestionCounter < 10) {
                        updateUI();

                    } else {
                        Intent intent = new Intent(Question.this,ResultPage.class);
                        intent.putExtra("KEY",trait);
                        intent.putExtra("POINTS",String.valueOf(score));
                        intent.putExtra("CPOINTS",String.valueOf(compScore));
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 3000);
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
                //You can manage the blinking time with this parameter
                anim.setDuration(50);
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.ABSOLUTE);
                Timer.startAnimation(anim);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Timer.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_shape));
                }
                else {
                    Timer.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.red_shape));
                }
            }
        }

        @Override
        public void onFinish() {
            Timer.setText("0");
            if(Two.getText().toString().equals(AnswerKey)) {
                Two.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
            }
            else if(Three.getText().toString().equals(AnswerKey)) {
                Three.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
            }
            else if(One.getText().toString().equals(AnswerKey)){
                One.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
            }
            else if(Four.getText().toString().equals(AnswerKey)) {
                Four.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
            }

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {

                        if (QuestionCounter < 10) {
                            updateUI();

                        } else {
                            Intent intent = new Intent(Question.this,ResultPage.class);
                            intent.putExtra("KEY",trait);
                            intent.putExtra("POINTS",String.valueOf(score));
                            intent.putExtra("CPOINTS",String.valueOf(compScore));
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, 3000);
        }
    }

}
