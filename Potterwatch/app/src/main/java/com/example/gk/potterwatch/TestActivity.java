package com.example.gk.potterwatch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    Button pvp,lan,training,ahead;
    String trait = null;
    TextView house,poisonview;

    public TestActivity() {
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trait = extras.getString("KEY");
            switch (trait) {
                case "Gryffindor":
                    setTheme(R.style.Theme_Gryffindor);
                    break;
                case "Hufflepuff":
                    setTheme(R.style.Theme_Hufflepuff);
                    break;
                case "Ravenclaw":
                    setTheme(R.style.Theme_Ravenclaw);
                    break;
                case "Slytherin":
                    setTheme(R.style.Theme_Slytherin);
                    break;
            }

        }
        setContentView(R.layout.activity_test2);
        init();
        house.setText(trait);

        ahead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI();
            }
        });
        final CharSequence[] diff = {"Easy","Hard","Impossible"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Difficulty");
        builder.setItems(diff, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String difficulty = diff[i].toString();
                Intent intent = new Intent(TestActivity.this, Question.class);
                intent.putExtra("KEY",trait);
                intent.putExtra("DIFF",difficulty);
                startActivity(intent);
                finish();
            }
        });

        final AlertDialog alert = builder.create();

        final Button pvp = (Button) findViewById(R.id.pvpbutton);
        final Button lan = (Button) findViewById(R.id.lanparty);
        final Button drill = (Button) findViewById(R.id.training);

        drill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });

    }

    public void init()
    {
        pvp = (Button) findViewById(R.id.pvpbutton);
        lan = (Button) findViewById(R.id.lanparty);
        training = (Button) findViewById(R.id.training);
        ahead = (Button) findViewById(R.id.goAhead);
        house = (TextView) findViewById(R.id.HouseSelected);
        poisonview = (TextView) findViewById(R.id.PoisonTextView);

        pvp.setVisibility(View.GONE);
        lan.setVisibility(View.GONE);
        training.setVisibility(View.GONE);
        poisonview.setVisibility(View.GONE);
        house.setVisibility(View.VISIBLE);
        ahead.setVisibility(View.VISIBLE);
    }

    private void updateUI()
    {
        pvp.setVisibility(View.VISIBLE);
        lan.setVisibility(View.VISIBLE);
        training.setVisibility(View.VISIBLE);
        poisonview.setVisibility(View.VISIBLE);
        house.setVisibility(View.GONE);
        ahead.setVisibility(View.GONE);
    }

}
