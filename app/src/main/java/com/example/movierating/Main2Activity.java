package com.example.movierating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.Hashtable;


public class Main2Activity extends AppCompatActivity {

    EditText title, desciption, year, genres;
    RatingBar ratingBar;
    Button save;
    ImageButton imageButton;
    DatabaseReference databaseReference;
    int Gallery_intent = 2;
    Uri ImageUri;
    String downloadImageUrl;
    StorageReference ImagesRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseApp.initializeApp(this);

        title = findViewById(R.id.move_titel);
        desciption = findViewById(R.id.item_description);
        year = findViewById(R.id.release_year);
        ratingBar = findViewById(R.id.ratingBar);
        save = findViewById(R.id.save_details);
        genres = findViewById(R.id.genres);
        imageButton = findViewById(R.id.imageButton);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("rating_table");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                uploadingData();
                String nameValue = title.getText().toString();
                String descriptionValue = desciption.getText().toString();
                String yearValue = year.getText().toString();
                float rateValue = Float.parseFloat(String.valueOf(ratingBar.getRating()));
                String genresValue = genres.getText().toString();

                Hashtable<String, Object> Value = new Hashtable<>();
                Value.put("title", nameValue);
                Value.put("description", descriptionValue);
                Value.put("year", yearValue);
                Value.put("rating", rateValue);
                Value.put("genres", genresValue);
                Value.put("image", downloadImageUrl);

                databaseReference.push().setValue(Value);

                finish();


            }
        });


    }

    public void btnImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Gallery_intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_intent  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            imageButton.setImageURI(ImageUri);

        }

    }

    private void uploadingData()
    {

         final StorageReference filePath = ImagesRef.child(ImageUri.getLastPathSegment() + ".jpg");
         final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(Main2Activity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(Main2Activity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(Main2Activity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
            }
        });
    }


}






