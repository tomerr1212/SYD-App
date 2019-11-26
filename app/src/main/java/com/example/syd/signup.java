package com.example.syd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity implements View.OnClickListener  {
    ProgressBar progressBar;
    EditText editTextUsername,editTextEmail,editTextName,editTextPassword,editTextConfirmPassword;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsername = findViewById(R.id.editUserName);
        editTextEmail = findViewById(R.id.editEmail);
        editTextName = findViewById(R.id.editName);
        editTextPassword = findViewById(R.id.editPassword);
        editTextConfirmPassword = findViewById(R.id.editConfirmPassword);
        progressBar= findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btnSignUP).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }


    private void registerUser(){

        String username =editTextUsername.getText().toString().trim();
        String email =editTextEmail.getText().toString().trim();
        String name =editTextName.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();
        String cPaswword =editTextConfirmPassword.getText().toString().trim();

        if(username.isEmpty()){
            editTextUsername.setError("UserName is required");
            editTextUsername.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please insert a valid Email");
            editTextEmail.requestFocus();
            return;
        }

        if(name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        if(cPaswword.isEmpty()){
            editTextConfirmPassword.setError("Please Confirm your password");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if(!password.matches(cPaswword)){
            editTextConfirmPassword.setError("The password are not matched");
            editTextConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User SignedUp succesfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnSignUP:
                registerUser();
                break;

            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }

}
