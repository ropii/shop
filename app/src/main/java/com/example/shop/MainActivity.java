package com.example.shop;

import static android.app.PendingIntent.getActivity;

import static com.example.shop.Functions.isValidEmailAddress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    TextView tv;

    Button btn_cancel, btn_signUp;
    EditText et_firstName, et_lastName, et_password, et_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_signUp) {
            openSignUpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSignUpDialog() {
        Dialog builder = new Dialog(MainActivity.this);
        builder.setContentView(R.layout.dialog_sign_up);
        builder.setCancelable(true);


        btn_signUp = builder.findViewById(R.id.btn_signUp);
        btn_cancel = builder.findViewById(R.id.btn_cancel);
        et_firstName = builder.findViewById(R.id.et_fistName);
        et_lastName = builder.findViewById(R.id.et_lastName);
        et_password = builder.findViewById(R.id.et_password);
        et_email = builder.findViewById(R.id.et_email);



        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_firstName = et_firstName.getText().toString();
                String str_lastName = et_lastName.getText().toString();
                String str_password = et_password.getText().toString();
                String str_email = et_email.getText().toString();

                //check if et is empty
                if (str_firstName.equals("")) {
                    Toast.makeText(MainActivity.this, "ENTER FIRST NAME", Toast.LENGTH_SHORT).show();
                }
                else if (str_lastName.equals("")) {
                    Toast.makeText(MainActivity.this, "ENTER LAST NAME", Toast.LENGTH_SHORT).show();
                }
                else if (str_email.equals("")|| !isValidEmailAddress(str_email)) {
                    Toast.makeText(MainActivity.this, "ENTER AN VALID EMAIL", Toast.LENGTH_SHORT).show();
                }
                else  if (str_password.equals("")) {
                    Toast.makeText(MainActivity.this, "ENTER PASSWORD", Toast.LENGTH_SHORT).show();
                }
                else{ //all valid info

                    builder.cancel();
                    Toast.makeText(MainActivity.this, "SIGN UP SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                }


            }
        });

        builder.create();
        builder.show();
    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, TestActivity.class));


    }




}