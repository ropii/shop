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

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BasicActivity implements View.OnClickListener {


    Person p;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);

    }


    @Override
    public void onClick(View view) {
        if (isSignIn()){
            DocumentReference docRef = db.collection("users").document(returnUser().getEmail()+"");
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    HashMap<String,String> map=new HashMap<String,String>();
                    String email,firstName,password, lastName;
                    for(Map.Entry<String, String> entry:map.entrySet()){
                        if (entry.getKey().equals("email")){
                            email= entry.getValue();
                        }
                        if (entry.getKey().equals("firstName")){
                            firstName= entry.getValue();
                        }
                        if (entry.getKey().equals("password")){
                            password= entry.getValue();
                        }
                        if (entry.getKey().equals("lastName")){
                            lastName= entry.getValue();
                        }
                    }
                    afterGetPerson();
                }
            });
            // צריך לקבל את האיש שאיחסנתי בפייר-סטור
        }
        else{
            tv.setText(isSignIn()+"");
        }
          //  startActivity(new Intent(this, TestActivity.class));

    }
    public void afterGetPerson(){
        tv.setText(""+p.getFirstName());


    }




}