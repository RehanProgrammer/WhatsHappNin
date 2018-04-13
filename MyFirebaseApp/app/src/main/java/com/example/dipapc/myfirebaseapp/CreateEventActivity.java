package com.example.dipapc.myfirebaseapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateEventActivity extends MenuActivity {

    private static final String TAG = "CreateEventActivity";
    private static final int RC_PHOTO_PICKER = 123;

    private EditText eventname;
    private EditText eventdescription;
    private EditText eventlocation;
    private ImageView eventpic;
    private Button save;


    private FirebaseAuth auth;

    private FirebaseDatabase database;
    private DatabaseReference myref;
    private DatabaseReference cref;


    private FirebaseStorage storage;
    private StorageReference sref;
    private double lat, lon;

    private Uri downloadUrl;


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
//            //get te url for storage
//            Uri selectedimg = data.getData();
//            //get reference for the storage
//            StorageReference photoref = sref.child(selectedimg.getLastPathSegment());
//            photoref.putFile(selectedimg).addOnSuccessListener(this,new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    downloadUrl = taskSnapshot.getDownloadUrl();
//                    Event eve = new Event();
//                    eve.setPhotourl(downloadUrl.toString());
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
        setContentView(R.layout.activity_create_event);
        eventname = findViewById(R.id.eventname);
        eventdescription = findViewById(R.id.eventdescription);
        eventlocation = findViewById(R.id.eventlocation);
        eventpic = findViewById(R.id.eventpic);
        save = findViewById(R.id.save);

       auth = FirebaseAuth.getInstance();

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myref = database.getReference(auth.getUid()).child("Event");
        cref = database.getReference().child("EventLocation");


        //get the lattitude and longitude from the address;

        eventpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }



        });


         save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Log.d(TAG,"Event creating");
                 geoLocate();
                 //craete a user object

                 Event event = new Event(eventname.getText().toString(),eventdescription.getText().toString(),null,
                         eventlocation.getText().toString(),lat,lon);

                 geoLocate();


                 //LatLng latlang = new LatLng(lat,lon);



                 cref.push().setValue(event);

                 Log.d(TAG,"event created");
                 startActivity(new Intent(CreateEventActivity.this,MapsActivity.class));

             }
         });


//        cref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Event event = dataSnapshot.getValue(Event.class);
//
//
//                eventname.setText(event.getName());
//                eventdescription.setText(event.getDescription());
//                eventlocation.setText(event.getLoaction());
//                eventpic.setVisibility(View.VISIBLE);
//                Glide.with(eventpic.getContext())
//                        .load(event.getPhotourl())
//                        .into(eventpic);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



    }

    private void geoLocate() {
        Log.d("TAG", "Geolocationg..");
        String searchAdd = eventlocation.getText().toString();
        Geocoder geocoder = new Geocoder(CreateEventActivity.this);
        List<Address> addresses = new ArrayList<>();
        try {
            // May throw an IOException
            addresses = geocoder.getFromLocationName(searchAdd, 1);

        } catch (IOException ex) {

            ex.printStackTrace();
            Log.e(TAG, "geoLocate: IOException: " + ex.getMessage());
        }
        if (addresses.size() > 0) {
            Address address = addresses.get(0);
            // Toast.makeText(MapActivity.this,"lat"+address.getCountryName(),Toast.LENGTH_SHORT).show();
            Log.d(TAG, "geoLocate: found a location: " + address.getLatitude() + address.getLongitude());
            lat = address.getLatitude();
            lon = address.getLongitude();
            //moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM);}
        }
    }

}
