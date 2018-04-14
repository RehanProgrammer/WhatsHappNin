package com.example.dipapc.myfirebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FriendActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText email;
    private Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        email = (EditText) findViewById(R.id.editText4);
        bt = (Button) findViewById(R.id.button4);


        bt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //get values for email to search for friend by email
                String femail = email.getText().toString();


            }
        });
    }//end onCreate



}
