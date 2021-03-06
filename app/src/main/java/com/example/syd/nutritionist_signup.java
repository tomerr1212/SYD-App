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

public class nutritionist_signup extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    EditText editTextEmail, editTextName, editTextAge, editTextYearsIfExpFill, editTextEducationFill, editTextPassword, editTextConfirmPassword;
    RadioGroup radioGenderGroup;
    RadioButton radioButtonGender;


    nutritionist nutritionist;
    private FirebaseAuth mAuth;
    DatabaseReference dbreff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_signup);

        editTextEmail = findViewById(R.id.editEmail);
        editTextName = findViewById(R.id.editName);
        editTextAge = findViewById(R.id.editAge);

        radioGenderGroup = findViewById(R.id.radioGenderGroup);
        editTextYearsIfExpFill = findViewById(R.id.editTextYearsOfExpFill);
        editTextEducationFill = findViewById(R.id.editTextEducationFill);
        editTextPassword = findViewById(R.id.editPassword);
        editTextConfirmPassword = findViewById(R.id.editConfirmPassword);

        nutritionist = new nutritionist();
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        dbreff = FirebaseDatabase.getInstance().getReference().child("nutritionist");


        findViewById(R.id.textViewSignUP2).setOnClickListener(this);
        findViewById(R.id.textViewAlreadyLoggedIn).setOnClickListener(this);
    }



    private boolean registerUser() {

        final String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String yearsOfExp = editTextYearsIfExpFill.getText().toString().trim();
        final String education = editTextEducationFill.getText().toString().trim();
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
        if (yearsOfExp.isEmpty()) {
            editTextYearsIfExpFill.setError("Years of experience is required");
            editTextYearsIfExpFill.requestFocus();
            return false;
        }
        if (education.isEmpty()) {
            editTextEducationFill.setError("Education is required");
            editTextEducationFill.requestFocus();
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

                if (task.isSuccessful()) {
                    int age = Integer.parseInt(editTextAge.getText().toString().trim());
                    String name = editTextName.getText().toString().trim();
                    int yearsOfExp = Integer.parseInt(editTextYearsIfExpFill.getText().toString().trim());
                    String Education = editTextEducationFill.getText().toString().trim();
                    System.out.println("Before nutritionist");
                    nutritionist.setEmail(email);
                    nutritionist.setAge(age);
                    nutritionist.setName(name);
                    nutritionist.setYearsOfExp(yearsOfExp);
                    nutritionist.setEducation(education);
                    nutritionist.setGender(gender);
                    dbreff.child(mAuth.getUid()).setValue(nutritionist);


                    Toast.makeText(getApplicationContext(), "User SignedUp successfully", Toast.LENGTH_SHORT).show();

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already signed up", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        progressBar.setVisibility(View.GONE);
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
