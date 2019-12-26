package com.example.syd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.syd.Interface.IFirebaseLoadDone;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_nutritionist extends AppCompatActivity implements  View.OnClickListener, IFirebaseLoadDone {


    readyMenu readymenu;
    DatabaseReference mealsRef;
    SearchableSpinner searchableSpinnerBF,searchableSpinnerLU,searchableSpinnerSN,searchableSpinnerDI;
    TextView calorieSum;
    IFirebaseLoadDone iFirebaseLoadDone;
    List<Meal> meals;
    private FirebaseAuth mAuth;
    DatabaseReference dbreff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nutritionist);

        dbreff = FirebaseDatabase.getInstance().getReference().child("Menu");

        mAuth = FirebaseAuth.getInstance();
        calorieSum = findViewById(R.id.textViewSumNum);

        searchableSpinnerBF = findViewById(R.id.spinnerBreakfast);
        searchableSpinnerLU = findViewById(R.id.spinnerLunch);
        searchableSpinnerSN = findViewById(R.id.spinnerSnack);
        searchableSpinnerDI = findViewById(R.id.spinnerDinner);


        mealsRef = FirebaseDatabase.getInstance().getReference("Meals");
        iFirebaseLoadDone= this;
        mealsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Meal> meals = new ArrayList<>();
                for(DataSnapshot mealSnapshot:dataSnapshot.getChildren()){
                    meals.add(mealSnapshot.getValue(Meal.class));
                }
                iFirebaseLoadDone.onFirebaseLoadSuccess(meals);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });


        findViewById(R.id.buttonBack).setOnClickListener(this);
        findViewById(R.id.buttonSave).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonBack:
                startActivity(new Intent(this, MainActivity_Customer.class));
                break;


            case R.id.buttonSave:
                savingMenu();
                Toast.makeText(getApplicationContext(), "readyMenu Saved Successfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void savingMenu() {

        readymenu = new readyMenu();
        String breakfast = searchableSpinnerBF.getItemAtPosition(searchableSpinnerBF.getSelectedItemPosition()).toString();
        String lunch = searchableSpinnerLU.getItemAtPosition(searchableSpinnerBF.getSelectedItemPosition()).toString();
        String snack = searchableSpinnerSN.getItemAtPosition(searchableSpinnerBF.getSelectedItemPosition()).toString();
        String dinner = searchableSpinnerDI.getItemAtPosition(searchableSpinnerBF.getSelectedItemPosition()).toString();
        //double caloriesSum = Double.parseDouble(calorieSum.getText().toString().trim());
        double caloriesSum =100;

        String author = mAuth.getCurrentUser().getUid();


        readymenu.setBreakfast(breakfast);
        readymenu.setLunch(lunch);
        readymenu.setSnack(snack);
        readymenu.setDinner(dinner);
        readymenu.setAuthor(author);
        readymenu.setSum(caloriesSum);

        dbreff.push().setValue(readymenu);


    }

    @Override


    public void onFirebaseLoadSuccess(List<Meal> mealList) {
        meals = mealList;

        //Get all name
        List<String> breakfast_list = new ArrayList<>();
        List<String> lunch_list = new ArrayList<>();
        List<String> snacks_list = new ArrayList<>();
        List<String> dinner_list = new ArrayList<>();


        for(Meal meal:mealList){

            if(meal.getType().equals("Breakfast"))
                breakfast_list.add(meal.getName());
            if(meal.getType().equals("Lunch"))
                lunch_list.add(meal.getName());
            if(meal.getType().equals("Snack"))
                snacks_list.add(meal.getName());
            if(meal.getType().equals("Dinner"))
                dinner_list.add(meal.getName());
        }
        //create adapter and send for spinner
        ArrayAdapter<String>  adapterBF = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,breakfast_list);
        ArrayAdapter<String>  adapterLU = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lunch_list);
        ArrayAdapter<String>  adapterSN = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,snacks_list);
        ArrayAdapter<String>  adapterDI = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,dinner_list);

        searchableSpinnerBF.setAdapter(adapterBF);
        searchableSpinnerLU.setAdapter(adapterLU);
        searchableSpinnerSN.setAdapter(adapterSN);
        searchableSpinnerDI.setAdapter(adapterDI);

    }

    @Override
    public void onFirebaseLoadFailed(String message) {


    }
}
