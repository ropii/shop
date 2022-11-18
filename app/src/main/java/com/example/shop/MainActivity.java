package com.example.shop;

import static android.app.PendingIntent.getActivity;

import static com.example.shop.Functions.isSignIn;
import static com.example.shop.Functions.returnConnectedPerson;
import static com.example.shop.Functions.returnUser;
import static com.example.shop.Functions.setP;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends BasicActivity implements View.OnClickListener {


     Person p =null;

    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);



    }


    @Override
    public void onClick(View view) {
        p = returnConnectedPerson();
        if (p !=null){
            tv.setText(p.getFirstName() +"\n" + p.getLastName() +"\n" + p.getEmail());
        }
        else {
            tv.setText(isSignIn()+"");
        }



          //  startActivity(new Intent(this, TestActivity.class));

    }






}