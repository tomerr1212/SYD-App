package com.example.syd;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class customer_update_profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText editTextWE,editTextAge;
    Button buttonUpdateProfile, buttonCamera;
    FirebaseAuth mAuth;
    ArrayList<String> myimages = new ArrayList<>();
    String CUSTOMER_PROFILE_CHANNEL_ID = "customer profile update";
    private static final int ImageBack =1;
    private StorageReference Folder;
    DatabaseReference memberReff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update_profile);
        mAuth = FirebaseAuth.getInstance();
        Folder= FirebaseStorage.getInstance().getReference().child("ImageFolder");
        editTextAge = findViewById(R.id.editTextAge);
        editTextWE = findViewById(R.id.editTextWE);
        Spinner spinnerActivity = findViewById(R.id.spinnerCustomerWeeklyActivity);
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(this,R.array.weekly_activity,android.R.layout.simple_spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivity.setAdapter(activityAdapter);
        spinnerActivity.setOnItemSelectedListener(this);



        memberReff = FirebaseDatabase.getInstance().getReference("Member");

        memberReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double weight=0;
                int age=0;
                String activitygoal="";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    weight = Double.parseDouble(postSnapshot.child("weight").getValue().toString());
                    age = Integer.parseInt(postSnapshot.child("age").getValue().toString());
                    activitygoal = postSnapshot.child("activitygoal").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);
        buttonCamera=findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = editTextAge.getText().toString().trim();
                String weight = editTextWE.getText().toString().trim();
                if (age.isEmpty()) {
                    editTextAge.setError("Age is required");
                    editTextAge.requestFocus();
                    return;
                }
                if (weight.isEmpty()) {
                    editTextWE.setError("Age is required");
                    editTextWE.requestFocus();
                    return;
                }
                memberReff.child(mAuth.getCurrentUser().getUid()).child("age").setValue(age);
                memberReff.child(mAuth.getCurrentUser().getUid()).child("weight").setValue(weight);

                addNotification();
            }
        });
    }


    private void uploadPhoto() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,ImageBack);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ImageBack){
            if(resultCode== RESULT_OK){
                Uri ImageData = data.getData();
                final StorageReference Imagename = Folder.child(mAuth.getCurrentUser().getUid()).child("image" + ImageData.getLastPathSegment());
                Imagename.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DatabaseReference imagestore = FirebaseDatabase.getInstance().getReference()
                                        .child("Member").child(mAuth.getCurrentUser().getUid());

                                myimages.add(String.valueOf(uri));
                                imagestore.child("myimages").setValue(myimages.toString());
                                Toast.makeText(customer_update_profile.this,"Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }



    }

    private void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CUSTOMER_PROFILE_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Hey !")
                .setContentText("Your profile has been updated")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, MainActivity_Customer.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CUSTOMER_PROFILE_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
            memberReff.child(mAuth.getCurrentUser().getUid()).child("activitygoal").setValue(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
