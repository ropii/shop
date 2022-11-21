package com.example.shop;

import static android.app.PendingIntent.getActivity;

import static com.example.shop.Functions.isSignIn;
import static com.example.shop.Functions.returnConnectedPerson;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends BasicActivity implements View.OnClickListener {


     static Person p =null;

    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        p = returnConnectedPerson();



    }


    @Override
    public void onClick(View view) {
        p = returnConnectedPerson();
        if (p !=null){
            tv.setText(p.getFirstName() +"\n" + p.getLastName() +"\n" + p.getEmail()+"\n" + p.getPassword());
        }
        else {
            tv.setText(isSignIn()+"");
        }



          //  startActivity(new Intent(this, TestActivity.class));

    }






}