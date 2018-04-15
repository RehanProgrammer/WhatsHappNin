package com.example.dipapc.myfirebaseapp;

import android.widget.Toast;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreateProfileActivityTest {



        @Test
        public void Validate() throws Exception {
            String input1 = "rayeed";
            String input2 = "rayeed@gmail.com";
            String input3 = "I am cute";
            Boolean expected = true;
            Boolean result = Validate(input1,input2,input3);
            assertEquals(expected,result);


        }

    private boolean Validate(String e ,String p,String d){
        boolean result = true ;

        if (e.isEmpty() || p.isEmpty() || d.isEmpty()) {
            //Toast.makeText(CreateProfileActivity.this, "The fields has to be filled", Toast.LENGTH_SHORT).show();
            result = false;
        }

//

        return result;


    }



}