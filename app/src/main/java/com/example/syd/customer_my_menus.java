package com.example.syd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.syd.Interface.IFirebaseLoadDoneM;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class customer_my_menus extends AppCompatActivity implements IFirebaseLoadDoneM {

    TextView textViewBFData, textViewLunchData, textViewSNData, textViewDIData, textViewSumNum, textViewGoalNum;
    Button buttonBack3;
    SearchableSpinner searchableSpinnerMenus;
    private FirebaseAuth mAuth;
    DatabaseReference menusRef, memberReff, dbreff;
    List<readyMenu> menus;
    IFirebaseLoadDoneM iFirebaseLoadDoneM;
    private String mymenus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_built_in_meals);

        textViewBFData = findViewById(R.id.textViewBFData);
        textViewLunchData = findViewById(R.id.textViewLunchData);
        textViewSNData = findViewById(R.id.textViewSNData);
        textViewDIData = findViewById(R.id.textViewDIData);
        textViewSumNum = findViewById(R.id.textViewSumNum);
        textViewGoalNum = findViewById(R.id.textViewGoalNum);

        searchableSpinnerMenus = findViewById(R.id.searchableSpinnerMenus);


        searchableSpinnerMenus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Log.println(Log.ERROR, "Tagging Size", selectedItem);
                textViewSumNum.setText(String.valueOf(menus.get(position).getSum()));
                textViewBFData.setText(menus.get(position).getBreakfast());
                textViewLunchData.setText(menus.get(position).getLunch());
                textViewSNData.setText(menus.get(position).getSnack());
                textViewDIData.setText(menus.get(position).getDinner());


            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        memberReff = FirebaseDatabase.getInstance().getReference("Member");
        memberReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                textViewGoalNum.setText(dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("BMR").getValue().toString());
                mymenus = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("my_menus").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbreff = FirebaseDatabase.getInstance().getReference().child("Menu");
        iFirebaseLoadDoneM = this;

        mAuth = FirebaseAuth.getInstance();

        menusRef = FirebaseDatabase.getInstance().getReference("Menu");
        menusRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<readyMenu> menus = new ArrayList<>();
                for (DataSnapshot menuSnapshot : dataSnapshot.getChildren()) {
                    menus.add(menuSnapshot.getValue(readyMenu.class));
                }
                iFirebaseLoadDoneM.onFirebaseLoadSuccess(menus);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonBack3 =findViewById(R.id.buttonBack3);
        buttonBack3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(customer_my_menus.this, MainActivity_Customer.class));
            }
        });
    }


    @Override
    public void onFirebaseLoadSuccess(List<readyMenu> menusList) {
        menus = menusList;
        List<String> menus_id_list = new ArrayList<>();

        for (int i = 0; i < menusList.size(); i++) {
            if (mymenus.contains(menusList.get(i).getMenuname()))
                menus_id_list.add(menusList.get(i).getMenuname());
        }

        ArrayAdapter<String> adapterBF = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menus_id_list);

        searchableSpinnerMenus.setAdapter(adapterBF);


    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }


}
