package com.example.dipapc.myfirebaseapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CreateProfileActivity extends MenuActivity {

    private static final String TAG = "CreateProfileActivity";
    private static final int RC_PHOTO_PICKER = 123;
    private final int PICK_IMAGE_REQUEST = 71;

    private FirebaseDatabase database;

    private DatabaseReference mRef;
    private FirebaseAuth auth;

    private FirebaseStorage storage;
    private StorageReference sref;

    private User user;



    private Button save,upload;
    private EditText name;
    private EditText email;
    private EditText description;
    private ImageView profilepic;

    //private Uri downloadUrl,selectedimg;
    private Uri filePath;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profilepic.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
//            //get te url for storage
//            selectedimg = data.getData();
//            Log.d("Photo",selectedimg.toString());
//            //get reference for the storage
//            StorageReference photoref = sref.child(selectedimg.getLastPathSegment());
//            photoref.putFile(selectedimg).addOnSuccessListener(CreateProfileActivity.this,new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    downloadUrl = taskSnapshot.getDownloadUrl();
//                    Log.d("TAG",downloadUrl.toString());
////                    user = new User(downloadUrl.toString());
////                    user.setPhotourl(downloadUrl.toString());
//
//
//
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("profile").child(auth.getUid());

        storage = FirebaseStorage.getInstance();
        sref = storage.getReference().child("photos");


        //initialize the views
        save = (Button)findViewById(R.id.buttonSave);
        upload = (Button)findViewById(R.id.upload);
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        description = (EditText)findViewById(R.id.description);
        profilepic = findViewById(R.id.profilepic);

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
            }


        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filePath!=null){
                    sref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(CreateProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateProfileActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"profile creating");
                //Validate(name.getText().toString(),email.getText().toString(),description.getText().toString());
                //Log.d(TAG,downloadUrl.toString());


                //craete a user object
                user = new User(name.getText().toString(),email.getText().toString(), description.getText().toString(),filePath.toString());

                mRef.setValue(user);
                Log.d(TAG,"profile created");
               startActivity(new Intent(CreateProfileActivity.this,HomeActivity.class));



            }

        });

    }

    private void Validate(String e, String p, String d) {


        if (e.isEmpty() || p.isEmpty() || d.isEmpty()) {
            Toast.makeText(CreateProfileActivity.this, "The fields has to be filled", Toast.LENGTH_SHORT).show();
            return;
        }


    }
}
