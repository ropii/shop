package com.example.shop;

import static android.app.PendingIntent.getActivity;

import static com.example.shop.Functions.isSignIn;
import static com.example.shop.Functions.returnConnectedPerson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class MainActivity extends BasicActivity implements View.OnClickListener {
    static Person p = null;
    TextView tv;
    ChipNavigationBar chipNavigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.chipNavBar);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.menu_signUp:
                        openSignUpDialog();
                        break;
                    case R.id.menu_signIn:
                        openSignInDialog();
                        break;
                    case R.id.menu_accSettings:
                        startActivity(new Intent(getApplicationContext(), AccountSettingsActivity.class));
                        break;
                }
                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.commit();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        p = returnConnectedPerson();
        if (p != null ) {
            //Person p2 = p;
            tv.setText(p.getFirstName() + "\n" + p.getLastName() + "\n" + p.getEmail() + "\n" + p.getPassword()+"\n" +p.getClass());
        } else {
            tv.setText(isSignIn() + "");
        }
        //  startActivity(new Intent(this, TestActivity.class));
    }
}