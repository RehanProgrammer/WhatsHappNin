package com.example.dipapc.myfirebaseapp;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends MenuActivity {

    private ImageView profileimg;
    private TextView tv1,tv2,tv3;
    private Button update;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private Uri imagedata;
    @SuppressLint("RestrictedApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileimg = findViewById(R.id.profileimg);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        update = findViewById(R.id.update);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(auth.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);


                tv3.setText(user.getName());
                tv1.setText(user.getEmail());
                tv2.setText(user.getDescription());
                profileimg.setVisibility(View.VISIBLE);
                Glide.with(profileimg.getContext())
                        .load(user.getPhotourl())
                        .into(profileimg);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
