package com.example.syd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class built_in_menu extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_built_in_menu);

        Spinner spinnerbreakfast = findViewById(R.id.spinnerBreakfast);
        Spinner spinnerLunch = findViewById(R.id.spinnerLunch);
        Spinner spinnerSnack = findViewById(R.id.spinnerSnack);
        Spinner spinnerDinner = findViewById(R.id.spinnerDinner);

        ArrayAdapter<CharSequence> breakfastAdapter = ArrayAdapter.createFromResource(this,R.array.Breakfast,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> lunchAdapter = ArrayAdapter.createFromResource(this,R.array.Lunch,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> snackAdapter = ArrayAdapter.createFromResource(this,R.array.Snack,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> dinnerAdapter = ArrayAdapter.createFromResource(this,R.array.Dinner,android.R.layout.simple_spinner_item);

        breakfastAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lunchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snackAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerbreakfast.setAdapter(breakfastAdapter);
        spinnerLunch.setAdapter(lunchAdapter);
        spinnerSnack.setAdapter(snackAdapter);
        spinnerDinner.setAdapter(dinnerAdapter);

        spinnerbreakfast.setOnItemSelectedListener(this);
        spinnerLunch.setOnItemSelectedListener(this);
        spinnerSnack.setOnItemSelectedListener(this);
        spinnerDinner.setOnItemSelectedListener(this);


        findViewById(R.id.buttonBack).setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(),text,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonBack:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }
}
