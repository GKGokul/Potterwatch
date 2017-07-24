package com.example.gk.potterwatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        String score = extras.getString("POINTS","");
        TextView display = (TextView) findViewById(R.id.player_score);
        display.setText(score);
    }
}
