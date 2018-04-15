package com.example.dipapc.myfirebaseapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegistrationActivityTest {
    @Test
    public void Authenticate() throws Exception {
        String input1 = "rayeed.rashid@gmail.com";
        String input2 = "bangladesh22";
        Boolean expected = true;
        Boolean result = Authenticate(input1,input2);
        assertEquals(expected,result);


    }
    private boolean Authenticate(String e , String p){
        boolean result = true ;
        if (e.isEmpty()) {

            result = false;
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
//            email.setError("Please enter a valid email");
//            email.requestFocus();
//            return;
//        }

        if (p.isEmpty()) {

            result = false;
        }

        if (p.length() < 6) {

            result = false;
        }

        return result;


    }


}