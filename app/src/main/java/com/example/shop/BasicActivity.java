package com.example.shop;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BasicActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {   //מזמן MENU
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {    // בתוך MENU
        int id = item.getItemId();
        if (id == R.id.menu_signUp) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
