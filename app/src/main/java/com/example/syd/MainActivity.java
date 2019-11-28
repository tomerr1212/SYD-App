package com.example.syd;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button signup;
    EditText editTextEmail,editTextPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.textViewSignup).setOnClickListener(this);
    }

    private void userLogin(){


    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.textViewSignup:
                startActivity(new Intent(this,signup.class));
                break;

            case R.id.textViewLogin:
                userLogin();
        }

    }


}
