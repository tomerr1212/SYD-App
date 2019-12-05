package com.example.syd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signup extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    EditText editTextEmail, editTextName, editTextAge, editTextKG, editTextCM, editTextPassword, editTextConfirmPassword;
    RadioGroup radioGenderGroup;
    RadioButton radioButtonGender;


    Member member;
    long maxId = 0;
    private FirebaseAuth mAuth;
    DatabaseReference dbreff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail = findViewById(R.id.editEmail);
        editTextName = findViewById(R.id.editName);
        editTextAge = findViewById(R.id.editAge);

        radioGenderGroup = findViewById(R.id.radioGenderGroup);
//        radioButtonFemale = findViewById(R.id.radioButtonFemale);
//        radioButtonMale = findViewById(R.id.radioButtonMale);

        editTextKG = findViewById(R.id.editTextKG);
        editTextCM = findViewById(R.id.editTextCM);
        editTextPassword = findViewById(R.id.editPassword);
        editTextConfirmPassword = findViewById(R.id.editConfirmPassword);

        member = new Member();
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        dbreff = FirebaseDatabase.getInstance().getReference().child("Member");
        dbreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findViewById(R.id.btnSignUP).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }


//    public void checkButton(View view) {
//        int radioID = radioGenderGroup.getCheckedRadioButtonId();
//        radioButtonGender = findViewById(radioID);
//        Toast.makeText(this, "Selected: "+radioButtonGender.getText(),Toast.LENGTH_SHORT).show();
//    }

    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String KG = editTextKG.getText().toString().trim();
        String CM = editTextCM.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String cPaswword = editTextConfirmPassword.getText().toString().trim();


        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please insert a valid Email");
            editTextEmail.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return;
        }
        if (KG.isEmpty()) {
            editTextAge.setError("Weight is required");
            editTextAge.requestFocus();
            return;
        }
        if (CM.isEmpty()) {
            editTextAge.setError("Height is required");
            editTextAge.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        if (cPaswword.isEmpty()) {
            editTextConfirmPassword.setError("Please Confirm your password");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (!password.matches(cPaswword)) {
            editTextConfirmPassword.setError("The password are not matched");
            editTextConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    int age = Integer.parseInt(editTextAge.getText().toString().trim());
                    String name = editTextName.getText().toString().trim();
                    int weight = Integer.parseInt(editTextKG.getText().toString().trim());
                    int height = Integer.parseInt(editTextCM.getText().toString().trim());
                    System.out.println("Before Member");
                    member.setAge(age);
                    member.setName(name);
                    member.setWeight(weight);
                    member.setGender(radioButtonGender.getText().toString());
                    dbreff.child(String.valueOf(maxId + 1)).setValue(member);

                    Toast.makeText(getApplicationContext(), "User SignedUp successfully", Toast.LENGTH_SHORT).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already signed up", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public void onClick(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.btnSignUP:
                registerUser();
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }

}
