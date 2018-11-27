package com.example.eilynn.chalktalklsu;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PostActivity extends AppCompatActivity {
    private ImageButton selectPostImage;
    private Button subButton;
    private EditText postText;
   // private Toolbar toolbar;
    private static final int gallery_pick = 1;
    private Uri ImageUri;
    private String description;
    private StorageReference PostImagesReference;
    private String saveCurrDate, saveCurrTime, postRandomName, downloadURL;
    public PostActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        PostImagesReference = FirebaseStorage.getInstance().getReference();

        selectPostImage = (ImageButton) findViewById(R.id.imageButton);
        subButton = (Button) findViewById(R.id.subButton);
        postText = (EditText) findViewById(R.id.postText);

        selectPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePostInfo();
            }
        });

    }

    private void ValidatePostInfo() {
         description = postText.getText().toString();
         if(ImageUri == null){
             Toast.makeText(this,"please select post image", Toast.LENGTH_SHORT).show();
         }
        else if(TextUtils.isEmpty(description)){
            Toast.makeText(this,"please put text for image", Toast.LENGTH_SHORT).show();
        }
        else{
             StoringImageToStorage();
         }
    }

    private void StoringImageToStorage() {
        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrDate = currentDate.format(callForDate.getTime());

        Calendar callForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrTime = currentTime.format(callForDate.getTime());

        postRandomName = saveCurrDate + saveCurrTime;

        StorageReference filePath = PostImagesReference.child("Post Images").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");

        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    //downloadURL = task.getResult().getDownloadUrl().toString();
                    Toast.makeText(PostActivity.this,"Image has uploaded sucessfully",Toast.LENGTH_SHORT).show();
                }else{
                    String message = task.getException().getMessage();
                    Toast.makeText(PostActivity.this, "Error occured" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, gallery_pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == gallery_pick && resultCode == RESULT_OK && data != null){
            ImageUri = data.getData();
            selectPostImage.setImageURI(ImageUri);
        }
    }
}





