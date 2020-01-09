package com.example.syd;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class customer_update_profile extends AppCompatActivity {
    TextView textViewCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update_profile);

    textViewCamera=findViewById(R.id.textViewCamera);


    textViewCamera.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            takephoto();
        }
    });
    }


    private void takephoto() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);

    }
}
