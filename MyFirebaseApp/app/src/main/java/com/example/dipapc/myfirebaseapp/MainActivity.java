package com.example.dipapc.myfirebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;



public class MainActivity extends AppCompatActivity  {
    private static  final  String TAG = "MainActivity";

    private FirebaseAuth mAuth;

    private EditText email, password;
    private Button bt;
    private TextView tv;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        //initialize the view
        email = (EditText)findViewById(R.id.editText);
        password =  (EditText)findViewById(R.id.editText2);
        bt = (Button)findViewById(R.id.button2);
        tv = (TextView)findViewById(R.id.textView2);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the values for email and password
                String uemail = email.getText().toString();
                String upass = password.getText().toString();
                Authenticate(uemail,upass);
                Validate(uemail,upass);



            }
        });




    }
    private void Authenticate(String e , String p){
        if (e.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
//            email.setError("Please enter a valid email");
//            email.requestFocus();
//            return;
//        }

        if (p.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if (p.length() < 6) {
            password.setError("Minimum length of password should be 6");
            password.requestFocus();
            return;
        }


    }

    public void Validate(String uemail, String upass){
        //create a firebase instance
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(uemail, upass)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Sign in Successful.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Sign in failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });


    }




}
