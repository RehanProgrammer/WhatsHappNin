package com.example.dipapc.myfirebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private static  final  String TAG = "RegistrationActivity";

    private FirebaseAuth mAuth;

    EditText name, password,email;
    Button signup;
    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //initialize views
         name = (EditText)findViewById(R.id.name);
         password = (EditText)findViewById(R.id.password);
         email = (EditText)findViewById(R.id.email);
         signup = (Button)findViewById(R.id.btn_signUp);
         signin = (TextView) findViewById(R.id.btn_signin);
        //create a firebase instance
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the values
                String uemail = email.getText().toString();
                String upass = password.getText().toString();
                if(Authenticate(uemail,upass)) {
                    mAuth.createUserWithEmailAndPassword(uemail, upass).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                finish();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getApplicationContext(), "You are already registered,Please sign in", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Already registered");
                                    finish();
                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }


                        }

                        ;
                    });
                }
                else{
                    startActivity(new Intent(RegistrationActivity.this, RegistrationActivity.class));


                }



            }
        });




    }
    private boolean Authenticate(String e , String p){
        if (e == null) {
            email.setError("Email is required");
            email.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
           email.setError("Please enter a valid email");
            email.requestFocus();
            return false;
        }

        if (p == null) {
            password.setError("Password is required");
            password.requestFocus();
            return false;
        }

        if (p.length() < 6) {
            password.setError("Minimum length of password should be 6");
            password.requestFocus();
            return false;
        }

        return true;


    }
}
