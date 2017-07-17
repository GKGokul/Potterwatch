package com.example.gk.potterwatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import junit.framework.Test;

public class Question extends TestActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String trait = sharedPref.getString("HOUSE","");
        if(trait!=null) {
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
        setContentView(R.layout.activity_question);
    }
}
