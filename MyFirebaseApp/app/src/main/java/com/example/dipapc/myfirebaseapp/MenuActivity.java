package com.example.dipapc.myfirebaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by DipaPc on 4/5/2018.
 */

public class MenuActivity extends AppCompatActivity {
    FirebaseAuth auth;

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        auth = FirebaseAuth.getInstance();
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        int id = item.getItemId();

        if (id == R.id.home) {
            //Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.friendlist) {
            startActivity(new Intent(this, FriendActivity.class));
            //Toast.makeText(this, "Contacts", Toast.LENGTH_SHORT).show);
        }
        else if (id == R.id.Profile) {
            startActivity(new Intent(this, ProfileActivity.class));

        }
        else if (id == R.id.signout) {
            auth.signOut();
            startActivity(new Intent(this, MainActivity.class));

        }



            return true;
        //return super.onOptionsItemSelected(item);
    }

    }




