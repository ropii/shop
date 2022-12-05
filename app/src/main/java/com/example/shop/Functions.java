package com.example.shop;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    static Person generalConnectedPerson = null;

    // בודקת את תקינות האימייל
    public static boolean isValidEmailAddress(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    //מחזיר אמת אם המשתמש מחובר ושקר אחרת
    public static boolean isSignIn() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser() != null;
    }

    // מחזיר את המשתמש המחובר(אם משתמש לא מחובר מחזיר NULL)
    public static FirebaseUser returnUser() {
        if (isSignIn()) {
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            return currentUser;
        } else {
            return null;
        }
    }


    // מחזיר את "האיש הכללי המחובר"(משתנה ב FUNCTIONS) ובעזרתו אפשר לגשת לנתונים הנימצאים בפייר-סטור
    public static Person returnConnectedPerson() {
        setPerson();
        return generalConnectedPerson;
    }


    //מעדכן את "האיש הכללי המחובר"(משתנה ב FUNCTIONS) לאיש שמחובר
    static void setPerson() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (isSignIn()) {

            DocumentReference docRef = db.collection("users").document(returnUser().getEmail());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d("user_data", ""+documentSnapshot.get("money"));
                    if (documentSnapshot.get("money")==null){
                    Functions.generalConnectedPerson = documentSnapshot.toObject(Person.class);}
                    else {
                        Functions.generalConnectedPerson = documentSnapshot.toObject(Partner.class);

                            Log.d("user_data_kind", ""+generalConnectedPerson.getClass());


                    }
                }
            });
        } else {
            Functions.generalConnectedPerson = null;
        }
    }

    // בדיקת תכנות
    public static void test() {
        Date date = new Date(12, 2022);
        CreditCard card = new CreditCard(1, 2, date);
        Person p = new Person("name", "family", "gmail", "pass");
        Person p2 = new Person("name2", "family", "gmail", "pass");
        Person p3 = new Person("name3", "family", "gmail", "pass");
        Partner b1 = new Partner(p, card, 123);
        Partner b2 = new Partner(p2, card, 234);
        Person pArr[] = new Person[]{p2, b1};
        Object tr = new Person("name", "family", "gmail", "pass");
        if (tr.getClass() == Partner.class) {
            ((Partner) tr).getOrders();
        }


    }

}
