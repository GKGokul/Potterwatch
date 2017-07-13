package com.example.gk.potterwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            String trait = extras.getString("KEY");
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



    }
}
