package com.example.shop;

import static android.app.PendingIntent.getActivity;

import static com.example.shop.Functions.isSignIn;
import static com.example.shop.Functions.returnUser;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends BasicActivity implements View.OnClickListener {


    static Person connectedPerson =null;
    static City city=null;

    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        setP();



    }


    @Override
    public void onClick(View view) {
        setP();
        if (connectedPerson !=null){
            tv.setText(connectedPerson.getFirstName());
        }
        else {
            tv.setText(isSignIn()+"");
        }



          //  startActivity(new Intent(this, TestActivity.class));

    }


    public static void setP(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (isSignIn()){

            DocumentReference docRef = db.collection("users").document(returnUser().getEmail());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    MainActivity.connectedPerson = documentSnapshot.toObject(Person.class);
                }
            });
        }
    }




}