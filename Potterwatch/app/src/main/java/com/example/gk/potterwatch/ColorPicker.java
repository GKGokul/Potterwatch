package com.example.gk.potterwatch;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ColorPicker extends AppCompatActivity {

    private RadioGroup HouseColorPicker;
    private RadioButton RadioHouseButton;
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorpicker);

        // function call to set listener on the radiobuttons
        RadioButtonListener();

    }

    public void RadioButtonListener() {
        HouseColorPicker = (RadioGroup) findViewById(R.id.radioHouse);
        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectID = HouseColorPicker.getCheckedRadioButtonId();

                RadioHouseButton = (RadioButton) findViewById(selectID);

                String trait = RadioHouseButton.getText().toString();

                Toast.makeText(ColorPicker.this, RadioHouseButton.getText(), Toast.LENGTH_LONG).show();

                Intent test = new Intent(ColorPicker.this, TestActivity.class);
                test.putExtra("KEY", trait);
                startActivity(test);

            }
        });
    }


}
