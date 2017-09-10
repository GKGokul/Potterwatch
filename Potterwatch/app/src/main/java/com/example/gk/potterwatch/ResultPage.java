package com.example.gk.potterwatch;

import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultPage extends AppCompatActivity {

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
        setContentView(R.layout.activity_result_page);
        String score = extras.getString("POINTS", "");
        String compScore = extras.getString("CPOINTS", "");
        String finalScore = score + "-" + compScore;

        final CharSequence[] diff = {"Easy","Hard","Impossible"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Difficulty");
        builder.setItems(diff, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String difficulty = diff[i].toString();
                Intent intent = new Intent(ResultPage.this, Question.class);
                intent.putExtra("KEY",trait);
                intent.putExtra("DIFF",difficulty);
                startActivity(intent);
            }
        });

        final AlertDialog alert = builder.create();

        String result;
        int s = Integer.valueOf(score);
        int cs = Integer.valueOf(compScore);
        if (s > cs) {
            result = "You Win!";
        } else if (s < cs) {
            result = "You Lose!";
        } else {
            result = "Its a Tie!";
        }
        TextView res = (TextView) findViewById(R.id.result);
        TextView display = (TextView) findViewById(R.id.player_score);
        display.setText(finalScore);
        res.setText(result);
        Button replay = (Button) findViewById(R.id.restart);

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultPage.this, Question.class);
                intent.putExtra("KEY", trait);
                startActivity(intent);
                alert.show();
            }
        });
    }
}
