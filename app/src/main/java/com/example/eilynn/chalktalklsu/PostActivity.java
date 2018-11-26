package com.example.eilynn.chalktalklsu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;

public class PostActivity extends AppCompatActivity {
    private ImageButton selectPostImage;
    private Button subButton;
    private EditText postText;
   // private Toolbar toolbar;
    private static final int gallery_pick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        selectPostImage = (ImageButton) findViewById(R.id.imageButton);
        subButton = (Button) findViewById(R.id.subButton);
        postText = (EditText) findViewById(R.id.postText);

        selectPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, gallery_pick);
    }
}



