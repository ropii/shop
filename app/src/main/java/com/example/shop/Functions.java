package com.example.shop;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    // בודקת את תקינות האימייל
    public static boolean isValidEmailAddress(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    //מחזיר אמת אם המשתמש מחובר ושקר אחרת
    public static boolean isSignIn(){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser() != null;
    }

    // מחזיר את המשתמש המחובר(אם משתמש לא מחובר מחזיר NULL)
    public static FirebaseUser returnUser(){
        if (isSignIn()){
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            return currentUser;}
        else {
            return null;
        }
    }




}
