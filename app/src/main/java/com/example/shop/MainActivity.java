package com.example.shop;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

    Dialog signUp;

    EditText et_firstName, et_lastName, et_password, et_email;
    TextView tv, tv_signUp_title;
    private FirebaseAuth mAuth;
    Dialog d = openSignUpDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        et_firstName = d.findViewById(R.id.et_fistName);
        et_lastName = d.findViewById(R.id.et_lastName);
        et_password = d.findViewById(R.id.et_password);
        et_email = d.findViewById(R.id.et_email);
        tv_signUp_title= d.findViewById(R.id.tv_signUp_title);
        mAuth = FirebaseAuth.getInstance();
    }



/*    public void signUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("sign up");
        builder.setMessage("are you sure?");
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://orteil.dashnet.org/cookieclicker/"));
                startActivity(it);
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();


            }
        });
        signUp = builder.create();
        signUp.show();
    }*/  // delete


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_signUp) {

            d.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Dialog openSignUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sign_up, null);
        builder.setView(view).setPositiveButton("signup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!et_firstName.getText().toString().equals("") && !et_lastName.getText().toString().equals("") && !et_password.getText().toString().equals("") && !et_email.getText().toString().equals("")) {
                    tv.setText("" + et_firstName.getText().toString() + et_lastName.getText().toString() + et_password.getText().toString() + et_email.getText().toString());
                    Person person = new Person(et_firstName.getText().toString(), et_lastName.getText().toString(), et_password.getText().toString(), et_email.getText().toString());
                    mAuth.createUserWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                   // tv_signUp_title= d.findViewById(R.id.tv_signUp_title);
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();

                                    } else {
                                        tv_signUp_title.setText("Sign Up Failed");
                                    }
                                }
                            });


                }

            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        return builder.create();


    }

    @Override
    public void onClick(View view) {

    }
}