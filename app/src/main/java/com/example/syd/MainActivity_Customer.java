package com.example.syd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_Customer extends AppCompatActivity {

    Button buttonCustomMenu;
    Button buttonBuiltInMenu;
    Button buttonUpdateProfile;
    Button buttonLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCustomMenu = findViewById(R.id.buttonCustomMenu);
        buttonBuiltInMenu = findViewById(R.id.buttonBuiltInMenu);
        buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);
        buttonLogout = findViewById(R.id.buttonLogout);

        buttonCustomMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity_Customer.this, customer_meal.class));
            }

        });

        buttonBuiltInMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity_Customer.this, nutritionist_meals.class));
            }

        });

        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity_Customer.this, nutritionist_meals.class));
            }

        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity_Customer.this, login.class));
            }

        });

    }



}