package com.example.syd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail,editTextPassword;
    TextView textViewLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.login_email);
        editTextPassword = findViewById(R.id.login_password);
        textViewLogin = findViewById(R.id.textViewLogin);
        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.textViewNutritionistLogin).setOnClickListener(this);
        findViewById(R.id.textViewNutritionistSignup).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
        getWindow().setStatusBarColor(Color.WHITE);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(),
                        editTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(login.this, goalActivity.class));
                        }else{
                            Toast.makeText(login.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }


    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.textViewSignup:
                startActivity(new Intent(this, customer_signup.class));
                break;


            case R.id.textViewNutritionistSignup:
                startActivity(new Intent(this, nutritionist_signup.class));
                break;

            case R.id.textViewNutritionistLogin:
                startActivity(new Intent(this, MainActivity_nutritionist.class));
                break;
        }

    }


}
