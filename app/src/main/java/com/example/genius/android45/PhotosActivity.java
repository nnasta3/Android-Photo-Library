package com.example.genius.android45;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {

    Album currAlbum = MainActivity.currAlbum;
    ImageButton backButton;
    Button addPhoto;
    public static final int PICK_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            //TODO: action
        }
    }
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_photos);
       //Intent intent = getIntent();
       addPhoto=findViewById(R.id.addPhoto);

       addPhoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getPhoto();
           }
       });

       // backButton = findViewById(R.id.backButton);
       // configureBackButton();

   }
    public void getPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
   // private void configureBackButton(){
   //     backButton.setOnClickListener(new View.OnClickListener() {
   //         @Override
   //         public void onClick(View v) {
   //             finish();
   //         }
   //     });
   // }
}
