package com.example.dipapc.whatshappnin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

// create objects

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;  // counter after which the sign in button will get locked
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // assign the views to the objects by their xml id

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
        userRegistration = (TextView)findViewById(R.id.tvRegister);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);

        Info.setText("No of attempts remaining: 5");//number of attempts remaining at the beginning

        //First, obtain an instance of this class by calling getInstance().

        firebaseAuth = FirebaseAuth.getInstance();
        //getCurrentUser()
        //Returns the currently signed-in FirebaseUser or null if there is none.



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the current user
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // if the user has already signed in that means the user returns not null then take the user to the next activity
//                if(user != null){
//                    Toast.makeText(MainActivity.this,"You have already signed in",Toast.LENGTH_SHORT).show();
//                    //finish();
//                    startActivity(new Intent(MainActivity.this,SecondActivity.class));
//                }
                String email = Name.getText().toString();
                String password = Password.getText().toString();
                Validate(email,password);
            }
        });



        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });
    }

    private void Validate(String user_email,String user_password){

        firebaseAuth.signInWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Sign in successful!!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,SecondActivity.class));
                }
                else{
                    Toast.makeText(MainActivity.this,"Sign in failed!!",Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: "+counter);
                    if(counter == 0){
                        Login.setEnabled(false);
                    }
                }
            }
        });

    }

//    private void checkEmailVerification(){
//        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
//        Boolean emailflag = user.isEmailVerified();
//
//        if(emailflag){
//            startActivity(new Intent(MainActivity.this,SecondActivity.class));
//        }
//        else
//        {
//            Toast.makeText(MainActivity.this,"Verify your email!!",Toast.LENGTH_SHORT).show();
//        }
//
//    }

}
