package com.example.syd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_Customer extends AppCompatActivity {

    Button buttonMyMenus;
    Button buttonBuiltInMenu;
    Button buttonUpdateProfile;
    Button buttonLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        buttonBuiltInMenu = findViewById(R.id.buttonBuiltInMenu);
        buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonMyMenus = findViewById(R.id.buttonMyMenus);


        buttonBuiltInMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity_Customer.this, built_in_meals.class));
            }

        });

        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity_Customer.this, customer_update_profile.class));
            }

        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity_Customer.this, login.class));
            }

        });

        buttonMyMenus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity_Customer.this, customer_my_menus.class));
            }

        });
    }



}
