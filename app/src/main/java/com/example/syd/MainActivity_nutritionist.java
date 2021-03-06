package com.example.syd;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

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
    SearchableSpinner searchableSpinnerBF,searchableSpinnerLU,searchableSpinnerSN,searchableSpinnerDI;
    TextView textViewSumNum;
    EditText editTextMenuName;
    IFirebaseLoadDone iFirebaseLoadDone;
    List<Meal> meals;
    private FirebaseAuth mAuth;
    DatabaseReference dbreff,mealsRef;
    List<Double> calories = new ArrayList<>();
    double bfCalorieSum=0,luCalorieSum=0,snCalorieSum=0,diCalorieSum=0;
    NotificationChannel nutritionChannel;
    String NUTRITIONIS_CHANNEL_ID = "Nutritionis_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nutritionist);
        Log.println(Log.ERROR,"build version",Build.VERSION.RELEASE);

        dbreff = FirebaseDatabase.getInstance().getReference().child("Menu");
        mAuth = FirebaseAuth.getInstance();

        textViewSumNum = findViewById(R.id.textViewSumNum);
        editTextMenuName = findViewById(R.id.editTextMenuName);

        searchableSpinnerBF = findViewById(R.id.spinnerBreakfast);
        searchableSpinnerLU = findViewById(R.id.spinnerLunch);
        searchableSpinnerSN = findViewById(R.id.spinnerSnack);
        searchableSpinnerDI = findViewById(R.id.spinnerDinner);

        searchableSpinnerBF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                String bf = searchableSpinnerBF.getSelectedItem().toString();
                bfCalorieSum =0;
                for(Meal meal:meals) {
                    if (meal.getName().equals(bf))
                        bfCalorieSum = meal.getCalories();
                }
                textViewSumNum.setText(Double.toString(bfCalorieSum + luCalorieSum + snCalorieSum + diCalorieSum));
            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
        searchableSpinnerLU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                String lu = searchableSpinnerLU.getSelectedItem().toString();
                luCalorieSum =0;

                for(Meal meal:meals) {
                    if (meal.getName().equals(lu))
                        luCalorieSum = meal.getCalories();
                }

                textViewSumNum.setText(Double.toString(bfCalorieSum + luCalorieSum + snCalorieSum + diCalorieSum));
            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
        searchableSpinnerSN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                String sn = searchableSpinnerSN.getSelectedItem().toString();
                snCalorieSum =0;

                for(Meal meal:meals) {
                    if (meal.getName().equals(sn))
                        snCalorieSum = meal.getCalories();
                }

                textViewSumNum.setText(Double.toString(bfCalorieSum + luCalorieSum + snCalorieSum + diCalorieSum));
            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
        searchableSpinnerDI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                String di = searchableSpinnerDI.getSelectedItem().toString();
                diCalorieSum =0;

                for(Meal meal:meals) {
                    if (meal.getName().equals(di))
                        diCalorieSum = meal.getCalories();
                }

                textViewSumNum.setText(Double.toString(bfCalorieSum + luCalorieSum + snCalorieSum + diCalorieSum));
            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });


        mealsRef = FirebaseDatabase.getInstance().getReference("Meals");
        iFirebaseLoadDone= this;
        mealsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meals = new ArrayList<>();
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

        findViewById(R.id.buttonSave).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonSave:
                savingMenu();
                Toast.makeText(getApplicationContext(), "readyMenu Saved Successfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void savingMenu() {

        readymenu = new readyMenu();
        String breakfast = searchableSpinnerBF.getItemAtPosition(searchableSpinnerBF.getSelectedItemPosition()).toString();
        String lunch = searchableSpinnerLU.getItemAtPosition(searchableSpinnerLU.getSelectedItemPosition()).toString();
        String snack = searchableSpinnerSN.getItemAtPosition(searchableSpinnerSN.getSelectedItemPosition()).toString();
        String dinner = searchableSpinnerDI.getItemAtPosition(searchableSpinnerDI.getSelectedItemPosition()).toString();
        String name = editTextMenuName.getText().toString().trim();

        String author = mAuth.getCurrentUser().getUid();

        if (name.isEmpty()) {
            editTextMenuName.setError("Please insert menu name");
            editTextMenuName.requestFocus();
            return;
        }

        readymenu.setBreakfast(breakfast);
        readymenu.setLunch(lunch);
        readymenu.setSnack(snack);
        readymenu.setDinner(dinner);
        readymenu.setAuthor(author);
        readymenu.setSum(bfCalorieSum+luCalorieSum+snCalorieSum+diCalorieSum);
        readymenu.setMenuname(editTextMenuName.getText().toString());
        dbreff.push().setValue(readymenu);

      //  addNotification();


    }

    private void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NUTRITIONIS_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("New menu added")
                .setContentText(editTextMenuName.getText())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, MainActivity_nutritionist.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NUTRITIONIS_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
            calories.add(meal.getCalories());
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
