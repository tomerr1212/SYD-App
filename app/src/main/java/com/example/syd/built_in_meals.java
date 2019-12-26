package com.example.syd;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class built_in_meals extends AppCompatActivity implements IFirebaseLoadDoneM, View.OnClickListener {

    TextView textViewBFData,textViewLunchData,textViewSNData,textViewDIData;
    SearchableSpinner searchableSpinnerMenus;
    DatabaseReference dbreff;
    private FirebaseAuth mAuth;
    DatabaseReference menusRef;
    List<readyMenu> menus;
    IFirebaseLoadDoneM iFirebaseLoadDoneM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_built_in_meals);

        searchableSpinnerMenus = findViewById(R.id.searchableSpinnerMenus);


        dbreff = FirebaseDatabase.getInstance().getReference().child("Menu");
        iFirebaseLoadDoneM = this;

        mAuth = FirebaseAuth.getInstance();
        menusRef = FirebaseDatabase.getInstance().getReference("Menu");
        menusRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<readyMenu> menus = new ArrayList<>();
                for(DataSnapshot menuSnapshot:dataSnapshot.getChildren()){
                    menus.add(menuSnapshot.getValue(readyMenu.class));
                }
                iFirebaseLoadDoneM.onFirebaseLoadSuccess(menus);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        findViewById(R.id.buttonBack2).setOnClickListener(this);
        findViewById(R.id.buttonKeepMenu).setOnClickListener(this);
    }


    @Override
    public void onFirebaseLoadSuccess(List<readyMenu> menusList) {
        menus = menusList;

        List<String> mealsBF_list = new ArrayList<>();
//        List<String> mealsLU_list = new ArrayList<>();
//        List<String> mealsSN_list = new ArrayList<>();
//        List<String> mealsDI_list = new ArrayList<>();

        Log.println(Log.ERROR,"Tagging Size", String.valueOf(menusList.size()));

        Log.d("myTag", "This is my message");
        for (int i =0;i<menusList.size(); i++) {
            Log.println(Log.ERROR,"Tagging Obj", menusList.get(i).getBreakfast());

            mealsBF_list.add(menusList.get(i).getBreakfast());
//            mealsLU_list.add(menusList.get(i).getLunch());
//            mealsSN_list.add(menusList.get(i).getSnack());
//            mealsDI_list.add(menusList.get(i).getDinner());
        }

        ArrayAdapter<String> adapterBF = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mealsBF_list);
        Log.println(Log.ERROR,"Tagging Arr", adapterBF.getItem(1));

        searchableSpinnerMenus.setAdapter(adapterBF);



    }
    @Override
    public void onFirebaseLoadFailed(String message) {

    }

    @Override
    public void onClick(View v) {

    }
}
