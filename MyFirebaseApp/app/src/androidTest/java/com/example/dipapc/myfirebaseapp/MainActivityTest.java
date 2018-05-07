package com.example.dipapc.myfirebaseapp;

import org.junit.Test;
import org.mockito.cglib.core.Converter;

import static org.junit.Assert.assertEquals;

public class MainActivityTest {
    @Test
    public void SignInAuthenticate() throws Exception {
        String input1 = "rayeed.rashid@gmail.com";
        String input2 = "bangladesh22";
        Boolean expected = true;
        Boolean result = SignInAuthenticate(input1,input2);
        assertEquals(expected,result);
        }

    private boolean SignInAuthenticate(String e , String p){
        boolean result = true ;
        if (e.isEmpty()) {

            result = false;
        }
        if (p.isEmpty()) {

            result = false; }

        if (p.length() < 6) {

            result = false;
        }

        return result;
        }
}