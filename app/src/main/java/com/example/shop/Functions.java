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

    static  Object generalConnectedPerson=null;

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
        if (generalConnectedPerson .getClass()==Buyer.class){
           return  ((Buyer) generalConnectedPerson);
        }
        if (generalConnectedPerson .getClass()==Seller.class){
            return  ((Seller) generalConnectedPerson);
        }
        if (generalConnectedPerson .getClass()==Person.class){
            return  ((Person) generalConnectedPerson);
        }
        return null;
    }



    //מעדכן את "האיש הכללי המחובר"(משתנה ב FUNCTIONS) לאיש שמחובר
    private static void setPerson(){
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
        else {
            Functions.generalConnectedPerson=null;
        }
    }

    public static void test(){
        Date date = new Date(12,2022);
        CreditCard card = new CreditCard(1,2,date);
        Person p = new Person("name","family","gmail","pass");
        Person p2 = new Person("name2","family","gmail","pass");
        Person p3 = new Person("name3","family","gmail","pass");
        Buyer b1 = new Buyer(p,card,123);
        Buyer b2 = new Buyer(p2,card,234);
        Seller s1 = new Seller("seller1","sell family","1@g.com","pass",card,345);
        Person pArr[] = new Person[]{p2,b1,s1};
        Object tr=new Person("name","family","gmail","pass");
        if (tr .getClass()==Buyer.class){
            ((Buyer) tr).getOrders();
        }


    }

}
