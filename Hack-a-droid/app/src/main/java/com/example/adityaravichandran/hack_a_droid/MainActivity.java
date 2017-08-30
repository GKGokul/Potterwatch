package com.example.adityaravichandran.hack_a_droid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import com.evgenii.jsevaluator.JsEvaluator;

public class MainActivity extends AppCompatActivity{

    String exp = "";

    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button one = (Button)findViewById(R.id.button1);
        Button two = (Button)findViewById(R.id.button2);
        Button three = (Button)findViewById(R.id.button3);
        Button four = (Button)findViewById(R.id.button4);
        Button five = (Button)findViewById(R.id.button5);
        Button six = (Button)findViewById(R.id.button6);
        Button seven = (Button)findViewById(R.id.button7);
        Button eight = (Button)findViewById(R.id.button8);
        Button nine = (Button)findViewById(R.id.button9);
        Button zero = (Button)findViewById(R.id.button0);
        Button add = (Button)findViewById(R.id.buttonadd);
        Button sub = (Button)findViewById(R.id.buttonsub);
        Button div = (Button)findViewById(R.id.buttondiv);
        Button mult = (Button)findViewById(R.id.buttonmult);
        Button del = (Button)findViewById(R.id.buttondel);
        Button eq = (Button)findViewById(R.id.buttoneq);

        final TextView box = (TextView) findViewById(R.id.box);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="1";
                box.setText(exp);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="2";
                box.setText(exp);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="3";
                box.setText(exp);
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="4";
                box.setText(exp);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="5";
                box.setText(exp);
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="6";
                box.setText(exp);
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="7";
                box.setText(exp);
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="8";
                box.setText(exp);
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="9";
                box.setText(exp);
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="0";
                box.setText(exp);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="+";
                box.setText(exp);
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="-";
                box.setText(exp);
            }
        });

        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="/";
                box.setText(exp);
            }
        });

        mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp+="*";
                box.setText(exp);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exp = "";
                box.setText(exp);
            }
        });

        eq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    exp = engine.eval(exp).toString();
                    box.setText(exp);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
