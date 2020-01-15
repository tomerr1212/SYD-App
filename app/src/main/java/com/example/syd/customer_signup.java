package com.example.syd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class customer_signup extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    EditText editTextEmail, editTextName, editTextAge, editTextKG, editTextCM, editTextPassword, editTextConfirmPassword;
    RadioGroup radioGenderGroup;
    RadioButton radioButtonGender;


    Member member;
    private FirebaseAuth mAuth;
    DatabaseReference dbreff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);

        editTextEmail = findViewById(R.id.editEmail);
        editTextName = findViewById(R.id.editName);
        editTextAge = findViewById(R.id.editAge);

        radioGenderGroup = findViewById(R.id.radioGenderGroup);
        editTextKG = findViewById(R.id.editTextKG);
        editTextCM = findViewById(R.id.editTextCM);
        editTextPassword = findViewById(R.id.editPassword);
        editTextConfirmPassword = findViewById(R.id.editConfirmPassword);

        member = new Member();
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        dbreff = FirebaseDatabase.getInstance().getReference().child("Member");

        findViewById(R.id.textViewSignUP2).setOnClickListener(this);
        findViewById(R.id.textViewAlreadyLoggedIn).setOnClickListener(this);
    }



    private boolean registerUser() {

        final String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String KG = editTextKG.getText().toString().trim();
        String CM = editTextCM.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String cPaswword = editTextConfirmPassword.getText().toString().trim();


        int selectedId = radioGenderGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioButtonGender = (RadioButton) findViewById(selectedId);
        final String gender = (String)radioButtonGender.getText();


        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please insert a valid Email");
            editTextEmail.requestFocus();
            return false;
        }

        if (name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return false;
        }
        if (age.isEmpty()) {
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return false;
        }
        if (KG.isEmpty()) {
            editTextAge.setError("Weight is required");
            editTextAge.requestFocus();
            return false;
        }
        if (CM.isEmpty()) {
            editTextAge.setError("Height is required");
            editTextAge.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return false;
        }

        if (cPaswword.isEmpty()) {
            editTextConfirmPassword.setError("Please Confirm your password");
            editTextConfirmPassword.requestFocus();
            return false;
        }

        if (!password.matches(cPaswword)) {
            editTextConfirmPassword.setError("The password are not matched");
            editTextConfirmPassword.requestFocus();
            return false;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    int age = Integer.parseInt(editTextAge.getText().toString().trim());
                    String name = editTextName.getText().toString().trim();
                    double weight = Integer.parseInt(editTextKG.getText().toString().trim());
                    double height = Integer.parseInt(editTextCM.getText().toString().trim());
                    System.out.println("Before Member");
                    member.setEmail(email);
                    member.setAge(age);
                    member.setName(name);
                    member.setWeight(weight);
                    member.setHeight(height);
                    member.setGender(gender);
                    member.setMyimages("");
                    dbreff.child(mAuth.getUid()).setValue(member);


                    Toast.makeText(getApplicationContext(), "User SignedUp successfully", Toast.LENGTH_SHORT).show();

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already signed up", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return true;
    }


    @Override
    public void onClick(View view) {

       // boolean checked = ( view).isChecked();
        switch (view.getId()) {
            case R.id.textViewSignUP2:
                if(registerUser())
                    startActivity(new Intent(this, login.class));
                break;

            case R.id.textViewAlreadyLoggedIn:
                //  credential(log in details);
                startActivity(new Intent(this, login.class));
                break;

        }
    }

}
