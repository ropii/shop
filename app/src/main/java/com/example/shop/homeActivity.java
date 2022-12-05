package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homeActivity extends BasicActivity implements View.OnClickListener {

    Button btn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==btn_start){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}