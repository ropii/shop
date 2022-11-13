package com.example.shop;

import static android.content.ContentValues.TAG;
import static com.example.shop.Functions.isSignIn;
import static com.example.shop.Functions.isValidEmailAddress;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BasicActivity extends AppCompatActivity {

    Button btn_cancel, btn_signUp,btn_logIn;
    EditText et_firstName, et_lastName, et_password, et_email;
    private FirebaseAuth mAuth;
    String str_firstName;
    String str_lastName;
    String str_password;
    String str_email;

    MenuItem menu_signUp,menu_signIn;


    //מזמן MENU
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu_signIn = findViewById(R.id.menu_signIn);
        if (isSignIn()){
//            menu_signIn.setVisible(false);
        }
        return true;
    }


    // בתוך MENU
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_signUp) {
            openSignUpDialog();
        }
        if (id == R.id.menu_signIn) {
            openSignInDialog();
        }
        return true;
    }


    //דיאלוג של התחברות
    private void openSignInDialog() {


        Dialog builder = new Dialog(BasicActivity.this);
        builder.setContentView(R.layout.dialog_sign_in);
        builder.setCancelable(true);


        et_password = builder.findViewById(R.id.et_password);
        btn_cancel = builder.findViewById(R.id.btn_cancel);
        et_email = builder.findViewById(R.id.et_email);
        btn_logIn = builder.findViewById(R.id.btn_logIn);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });

        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_password = et_password.getText().toString();
                str_email = et_email.getText().toString();

                //check if et is empty
                if (str_email.equals("") || !isValidEmailAddress(str_email)) {
                    et_email.setError("ENTER A VALID EMAIL ");

                }
                if (str_password.equals("") ||str_password.length()<6) {
                    et_password.setError("ENTER PASSWORD (6 chars)");
                }
                Boolean validInfo = str_password.length()>=6 && isValidEmailAddress(str_email)&& !str_email.equals("") &&  !str_password.equals("");
                if (validInfo) {   //info is valid

                    builder.cancel();
                    logIn();

                }


            }
        });

        builder.create();
        builder.show();


    }


    // דיאלוג של הרשמה
    private void openSignUpDialog() {
        Dialog builder = new Dialog(BasicActivity.this);
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
                str_firstName = et_firstName.getText().toString();
                str_lastName = et_lastName.getText().toString();
                str_password = et_password.getText().toString();
                str_email = et_email.getText().toString();

                //check if et is empty
                if (str_firstName.equals("")) {
                    et_firstName.setError("ENTER FIRST NAME");
                }

                if (str_lastName.equals("")) {
                    et_lastName.setError("ENTER LAST NAME");
                }
                if (str_email.equals("") || !isValidEmailAddress(str_email)) {
                    et_email.setError("ENTER A VALID EMAIL ");

                }
                if (str_password.equals("") ||str_password.length()<6) {
                    et_password.setError("ENTER PASSWORD (6 chars)");
                }
                 Boolean validInfo = str_password.length()>=6 && isValidEmailAddress(str_email)&& !str_firstName.equals("") &&  !str_lastName.equals("") &&  !str_email.equals("") &&  !str_password.equals("");
                if (validInfo) {   //info is valid

                    builder.cancel();
                    // Person p = new Person(str_firstName, str_lastName, str_email, str_password);
                    registerUser();

                }


            }
        });

        builder.create();
        builder.show();
    }




                                                                                   // פעולות

    // התחברות
    public void logIn(){

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(BasicActivity.this, "logged in.",Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(BasicActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    // שליחת אימייל וסיסמא לפייר-בייס
    public void registerUser() {

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(str_email, str_password).addOnCompleteListener(BasicActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(BasicActivity.this, "SIGN UP SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(BasicActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}

