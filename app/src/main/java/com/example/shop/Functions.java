package com.example.shop;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {
    static  Person generalConnectedPerson=null;
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


    //מחזיר את "האיש הכללי המחובר"(משתנה ב FUNCTIONS)
    public static Person returnConnectedPerson(){
        setPerson();
        return generalConnectedPerson;
    }



    //מעדכן את "האיש הכללי המחובר"(משתנה ב FUNCTIONS) לאיש שמחובר
    public static void setPerson(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (isSignIn()){

            DocumentReference docRef = db.collection("users").document(returnUser().getEmail());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Functions.generalConnectedPerson = documentSnapshot.toObject(Person.class);
                }
            });
        }
    }

}
