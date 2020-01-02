package com.example.syd;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class goalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spinnerWeeklyActivity,spinnerWeeklyGoal;
    TextView editTextKG;
    DatabaseReference dbreff, memberReff;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        Spinner spinnerGoal = findViewById(R.id.spinnerWeeklyGoal);
        Spinner spinnerActivity = findViewById(R.id.spinnerWeeklyActivity);

        mAuth = FirebaseAuth.getInstance();

        editTextKG = findViewById(R.id.editTextKG);


        dbreff = FirebaseDatabase.getInstance().getReference().child("Member");

        memberReff = FirebaseDatabase.getInstance().getReference("Member");

        memberReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double weight=0;
                double height=0;
                String weeklygoal="";
                int age=0;
                String gender="";
                String activitygoal="";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    weight = Double.parseDouble(postSnapshot.child("weight").getValue().toString());
                    height = Double.parseDouble(postSnapshot.child("height").getValue().toString());
                    age = Integer.parseInt(postSnapshot.child("age").getValue().toString());
                    gender = postSnapshot.child("gender").getValue().toString();
                    weeklygoal = postSnapshot.child("weeklygoal").getValue().toString();
                    activitygoal = postSnapshot.child("activitygoal").getValue().toString();

                }

                calculatingDailyCalories(weight,height,age,gender,weeklygoal,activitygoal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        editTextKG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                memberReff.child(mAuth.getCurrentUser().getUid()).child("targetweight").setValue(s.toString());
            }
        });
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

        if(text.equals("0.25") || text.equals("0.5") ||text.equals("1"))
            memberReff.child(mAuth.getCurrentUser().getUid()).child("weeklygoal").setValue(text);
        else
            memberReff.child(mAuth.getCurrentUser().getUid()).child("activitygoal").setValue(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.textViewLetsGO:
                startActivity(new Intent(this, MainActivity_Customer.class));
                break;

        }

    }

    private void calculatingDailyCalories(double weight,double height,int age,String gender,
                                          String weeklygoal,String activitygoal) {
        double bmr=0;
        int finalbmr=0;
        switch(gender){
            case"Male":
                switch(weeklygoal) {
                case "1":
                    bmr = (66.5+(13.75*weight)+(5.003*height)-(6.755*age))*0.62;
                    break;

                case "0.5":
                    bmr = (66.5+(13.75*weight)+(5.003*height)-(6.755*age))*0.81;
                    break;
                case "0.25":
                    bmr = (66.5+(13.75*weight)+(5.003*height)-(6.755*age))*0.91;
                    break;
                }

            case"Female":
                switch(weeklygoal) {
                    case "1":
                        bmr = 55.1+(9.56*weight)+(1.850*height)-(4.676*age)*0.62;
                        break;

                    case "0.5":
                        bmr = 55.1+(9.56*weight)+(1.850*height)-(4.676*age)*0.81;
                        break;
                    case "0.25":
                        bmr = 55.1+(9.56*weight)+(1.850*height)-(4.676*age)*0.91;
                        break;
                }
            }

        switch(activitygoal){
            case "Little to no exercise":
                bmr*=1.2;
                break;
            case "Light exercise (1–3 days per week)":
                bmr*=1.375;
                break;
            case "Moderate exercise (3–5 days per week)":
                bmr*=1.55;
                break;
            case "Very heavy exercise (twice per day)":
                bmr*=1.725;
                break;
        }

        finalbmr=(int)(bmr);
        memberReff.child(mAuth.getCurrentUser().getUid()).child("BMR").setValue(finalbmr);


    }


}
