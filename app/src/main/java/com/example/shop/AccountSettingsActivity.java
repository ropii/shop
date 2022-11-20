package com.example.shop;

import static com.example.shop.Functions.returnConnectedPerson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AccountSettingsActivity extends BasicActivity implements View.OnClickListener {

    Person p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        p = returnConnectedPerson();

    }

    @Override
    public void onClick(View view) {

    }
}