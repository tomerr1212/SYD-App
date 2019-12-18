package com.example.syd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.syd.Interface.IFirebaseLoadDone;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class built_in_menu extends AppCompatActivity implements  View.OnClickListener, IFirebaseLoadDone {

    DatabaseReference mealsRef;
    SearchableSpinner searchableSpinnerBF;//searchableSpinnerLU,searchableSpinnerSN,searchableSpinnerDI;
    IFirebaseLoadDone iFirebaseLoadDone;
    List<Meal> meals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_built_in_menu);

        searchableSpinnerBF = findViewById(R.id.spinnerBreakfast);
//        searchableSpinnerLU = findViewById(R.id.spinnerLunch);
//        searchableSpinnerSN = findViewById(R.id.spinnerSnack);
//        searchableSpinnerDI = findViewById(R.id.spinnerDinner);

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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonBack:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }

    @Override


    public void onFirebaseLoadSuccess(List<Meal> mealList) {
        meals = mealList;
        //Get all name

        List<String> breakfast_list = new ArrayList<>();
//        List<String> lunch_list = new ArrayList<>();
//        List<String> snacks_list = new ArrayList<>();
//        List<String> dinner_list = new ArrayList<>();

        for(Meal meal:mealList){
                breakfast_list.add(meal.getName());
        }
        //create adapter and send for spinner
        ArrayAdapter<String>  adapterBF = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,breakfast_list);
//        ArrayAdapter<String>  adapterLU = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lunch_list);
//        ArrayAdapter<String>  adapterSN = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,snacks_list);
//        ArrayAdapter<String>  adapterDI = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,dinner_list);

        searchableSpinnerBF.setAdapter(adapterBF);
//        searchableSpinnerLU.setAdapter(adapterLU);
//        searchableSpinnerSN.setAdapter(adapterSN);
//        searchableSpinnerDI.setAdapter(adapterDI);


    }

    @Override
    public void onFirebaseLoadFailed(String message) {


    }
}
