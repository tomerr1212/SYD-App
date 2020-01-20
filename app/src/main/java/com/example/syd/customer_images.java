package com.example.syd;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class customer_images extends AppCompatActivity {
    SearchableSpinner searchableSpinnerViewImages;
    ImageView imageViewCustomerImage;
    FirebaseAuth mAuth;
    FirebaseStorage storageAuth;
    Image image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_images);

        searchableSpinnerViewImages = findViewById(R.id.searchableViewImages);
        imageViewCustomerImage = findViewById(R.id.imageViewCustomerImage);
        storageAuth = FirebaseStorage.getInstance();

        mAuth = FirebaseAuth.getInstance();

        Log.v("WOW", storageAuth.getReference().child("ImageFolder").child(mAuth.getCurrentUser().getUid()).toString());

        storageAuth.getReference().child("ImageFolder").child(mAuth.getCurrentUser().getUid()).listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                          @Override
                                          public void onSuccess(ListResult listResult) {
                                              for (StorageReference item : listResult.getItems()) {
                                                  Log.v("WOW", item.getName());

                                                  String url = "gs://sydfirebaseproject-60fc3.appspot.com/ImageFolder/Tz7dsF6cxjZap2bPWB5RvoImIQj2/imageimage:34" + item.getName() + "?alt=media";

                                                  Log.w("WOW", url);
                                              }
                                          }
                                      });

        Log.v("CHECKING",storageAuth.getReference().child("ImageFolder").child(mAuth.getCurrentUser().getUid()).toString());
        StorageReference imageReff = storageAuth.getReference().child("ImageFolder").child(mAuth.getCurrentUser().getUid());
//        Glide.with(customer_images.this /* context */)
//                .load(imageReff)
//                .into(imageViewCustomerImage);
//
//




    }


}
