package com.example.syd;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class customer_images extends AppCompatActivity {
    SearchableSpinner searchableSpinnerViewImages;
    ImageView imageViewCustomerImage;
    FirebaseAuth mAuth;
    FirebaseStorage storageAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_images);

        searchableSpinnerViewImages = findViewById(R.id.searchableViewImages);
        imageViewCustomerImage = findViewById(R.id.imageViewCustomerImage);
        storageAuth = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        StorageReference imageReff = storageAuth.getReference().child("ImageFolder").child(mAuth.getCurrentUser().getUid());
        Glide.with(customer_images.this /* context */)
                .load(imageReff)
                .into(imageViewCustomerImage);






    }


}
