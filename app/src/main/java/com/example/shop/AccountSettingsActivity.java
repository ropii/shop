package com.example.shop;

import static android.content.ContentValues.TAG;
import static com.example.shop.Functions.isValidEmailAddress;
import static com.example.shop.Functions.returnConnectedPerson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;

public class AccountSettingsActivity extends BasicActivity implements View.OnClickListener {

    ImageButton imb_product,imb_cart,imb_history,imb_account;
    Person person=MainActivity.p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        if (person==null){
            person=returnConnectedPerson();
        }

        imb_product = findViewById(R.id.imb_product);
        imb_cart = findViewById(R.id.imb_cart);
        imb_history = findViewById(R.id.imb_history);
        imb_account = findViewById(R.id.imb_account);

        imb_product.setOnClickListener(this);
        imb_cart.setOnClickListener(this);
        imb_history.setOnClickListener(this);
        imb_account.setOnClickListener(this);
    }



        private void openAccInfoDialog() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Dialog builder = new Dialog(AccountSettingsActivity.this);
        builder.setContentView(R.layout.dialog_update_info);
        builder.setCancelable(true);


        Button btn_update = builder.findViewById(R.id.btn_update);
        btn_cancel = builder.findViewById(R.id.btn_cancel);
        et_firstName = builder.findViewById(R.id.et_fistName);
        et_lastName = builder.findViewById(R.id.et_lastName);
        et_password = builder.findViewById(R.id.et_password);
        et_email = builder.findViewById(R.id.et_email);
        EditText et_current_password = builder.findViewById(R.id.et_current_password);

            et_firstName.setText(person.getFirstName());
            et_lastName.setText(person.getLastName());
            et_password.setText(person.getPassword());
            et_email.setText(person.getEmail());

            et_email.setEnabled(false);
            et_email.setInputType(InputType.TYPE_NULL);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
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
                    if(et_current_password.getText().toString().equals(person.getPassword())){


                    db.collection("users").document(str_email).set(new Person(str_firstName, str_lastName, str_email, str_password));

                        if (!str_password.equals(person.getPassword())) {


                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // Get auth credentials from the user for re-authentication
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(str_email, person.getPassword()); // Current Login Credentials \\
                            // Prompt the user to re-provide their sign-in credentials
                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "User re-authenticated.");
                                            //Now change your email address \\
                                            //----------------Code for Changing Email Address----------\\
                                            user.updatePassword(str_password)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d("change password tag", "User password address updated.");
                                                            }
                                                        }
                                                    });
                                            //----------------------------------------------------------\\
                                        }
                                    });
                        }



                        builder.cancel();
                        Toast.makeText(AccountSettingsActivity.this,"info saved",Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(AccountSettingsActivity.this,"pls enter the password",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        builder.create();
        builder.show();
    }





    @Override
    public void onClick(View view) {
        if (view==imb_account){
            openAccInfoDialog();
        }

    }
}