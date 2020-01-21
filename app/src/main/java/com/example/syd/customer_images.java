package com.example.syd;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class customer_images extends AppCompatActivity {
    SearchableSpinner searchableSpinnerViewImages;
    ImageView imageViewCustomerImage;
    FirebaseAuth mAuth;
    FirebaseStorage storageAuth;
    List<String> uploadsNamesList = new ArrayList<>();
    ArrayList<upload> uploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_images);

        searchableSpinnerViewImages = findViewById(R.id.searchableViewImages);
        imageViewCustomerImage = findViewById(R.id.imageViewCustomerImage);
        storageAuth = FirebaseStorage.getInstance();

        uploads = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();

        storageAuth.getReference().child("ImageFolder").child(mAuth.getCurrentUser().getUid()).listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                          @Override
                                          public void onSuccess(ListResult listResult) {
                                              for (StorageReference item : listResult.getItems()) {
                                                  String url = "https://firebasestorage.googleapis.com/v0/b/sydfirebaseproject-60fc3.appspot.com/o/ImageFolder%2F"
                                                          +mAuth.getCurrentUser().getUid() +"%2F"+ item.getName() +"?alt=media";

                                                  upload temp = new upload(url,item.getName());
                                                  uploads.add(temp);
                                                  uploadsNamesList.add(temp.getName());
                                              }
                                          }
                                      });

        searchableSpinnerViewImages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Log.println(Log.ERROR, "Tagging Size", selectedItem);
                for (int i=0;i<uploads.size();i++){
                    if(selectedItem.equals(uploads.get(i).getName())){
                        Glide.with(getApplicationContext())
                                .load(uploads.get(i).getUrl())
                                .into(imageViewCustomerImage);
                    }
                }

            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        ArrayAdapter<String> adapterUploadName = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, uploadsNamesList);

        searchableSpinnerViewImages.setAdapter(adapterUploadName);


    }


}
