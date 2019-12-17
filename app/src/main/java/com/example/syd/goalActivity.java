package com.example.syd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class goalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    TextView textViewLetsGo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        Spinner spinnerGoal = findViewById(R.id.spinnerWeeklyGoal);
        Spinner spinnerActivity = findViewById(R.id.spinnerWeeklyActivity);

        ArrayAdapter<CharSequence> goalAdapter = ArrayAdapter.createFromResource(this,R.array.weekly_goal,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(this,R.array.weekly_activity,android.R.layout.simple_spinner_item);

        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGoal.setAdapter(goalAdapter);
        spinnerActivity.setAdapter(activityAdapter);

        spinnerGoal.setOnItemSelectedListener(this);
        spinnerActivity.setOnItemSelectedListener(this);

        findViewById(R.id.textViewLetsGO).setOnClickListener(this);

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
            case R.id.textViewLetsGO:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }

    }


}
