package com.example.gk.potterwatch;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {

    String trait = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            trait = extras.getString("KEY");
            switch(trait) {
                case "Bravery":     setTheme(R.style.Theme_Gryffindor);
                    break;
                case "Loyalty":     setTheme(R.style.Theme_Hufflepuff);
                    break;
                case "Wisdom":      setTheme(R.style.Theme_Ravenclaw);
                    break;
                case "Cunning":     setTheme(R.style.Theme_Slytherin);
                    break;
            }

        }
        setContentView(R.layout.activity_test2);

        final Button pvp = (Button) findViewById(R.id.pvpbutton);
        final Button lan = (Button) findViewById(R.id.lanparty);
        final Button drill = (Button) findViewById(R.id.training);

        drill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, Question.class);
                intent.putExtra("KEY",trait);
                startActivity(intent);
            }
        });

        pvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, PvPStart.class);
                intent.putExtra("KEY",trait);
                startActivity(intent);
            }
        });

    }

}
