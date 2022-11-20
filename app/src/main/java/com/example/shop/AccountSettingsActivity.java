package com.example.shop;

import static com.example.shop.Functions.isValidEmailAddress;
import static com.example.shop.Functions.returnConnectedPerson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AccountSettingsActivity extends BasicActivity implements View.OnClickListener {

    ImageButton imb_product,imb_cart,imb_history,imb_account;
    Person p=MainActivity.p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

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

            et_firstName.setText(p.getFirstName());
            et_lastName.setText(p.getLastName());
            et_password.setText(p.getPassword());
            et_email.setText(p.getEmail());


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
                    if(et_current_password.getText().toString().equals(p.getPassword())){
                    builder.cancel();
                    Person p = new Person(str_firstName, str_lastName, str_email, str_password);
                    //registerUser(p);


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