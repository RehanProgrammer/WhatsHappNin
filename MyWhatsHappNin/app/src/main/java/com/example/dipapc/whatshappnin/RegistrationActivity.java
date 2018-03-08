package com.example.dipapc.whatshappnin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail, userAge;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfilePic;
    String email, name, age, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        //public abstract class FirebaseAuth extends Object
        //The entry point of the Firebase Authentication SDK.
        //First, obtain an instance of this class by calling getInstance().

        firebaseAuth = FirebaseAuth.getInstance();

        //After the Register button is clicked...

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validate()){
                  //upload data to data base
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(RegistrationActivity.this,"Registration Successful!!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this,"Registration Failed!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });
    }


    private void setupUIViews()

    {
        userName = (EditText) findViewById(R.id.etUserName);
        userPassword = (EditText) findViewById(R.id.etUserPassword);
        userEmail = (EditText) findViewById(R.id.etUserEmail);
        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);
        //userAge = (EditText) findViewById(R.id.etAge);
        //userProfilePic = (ImageView) findViewById(R.id.ivProfile);

    }
    private Boolean Validate(){
        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();

        if(name.isEmpty()||password.isEmpty()||email.isEmpty()){
            Toast.makeText(this,"Please enter all fields",Toast.LENGTH_SHORT).show();
        }
        else
            result = true;

        return result;

    }
//    private void sendEmailVerification(){
//        FirebaseUser  user = firebaseAuth.getCurrentUser();
//        if(user != null){
//            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//
//                        Toast.makeText(RegistrationActivity.this,"Successfully registered!!",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(RegistrationActivity.this,"Successful
//                    }
//                }
//            });
//        }
//    }
}