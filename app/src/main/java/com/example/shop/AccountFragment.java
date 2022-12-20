package com.example.shop;

import static android.content.ContentValues.TAG;
import static com.example.shop.Functions.isSignIn;
import static com.example.shop.Functions.isValidEmailAddress;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class AccountFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private String mParam1;
    private String mParam2;

    public AccountFragment() {
    }
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    Button btn_signUp_acc, btn_signIn_acc, btn_signOut_acc, btn_AccountSettings_acc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("sagie", "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        btn_signUp_acc = view.findViewById(R.id.btn_signUp_acc);
        btn_signIn_acc = view.findViewById(R.id.btn_signIn_acc);
        btn_signOut_acc = view.findViewById(R.id.btn_signOut_acc);
        btn_AccountSettings_acc = view.findViewById(R.id.btn_AccountSettings_acc);
        btn_signUp_acc.setOnClickListener(this);
        btn_signIn_acc.setOnClickListener(this);
        btn_signOut_acc.setOnClickListener(this);
        btn_AccountSettings_acc.setOnClickListener(this);
        setVisibility();
        return view;
    }

    @Override
    public void onClick(View view) {


        if (view==btn_signOut_acc){
            openDisconnectDialog();
        }
        if (view==btn_signIn_acc){
            openSignInDialog();
        }
        if (view==btn_signUp_acc){
            openSignUpDialog();
        }
    }


                        // דיאלוגים
    //הפעולה פותחת דיאלוג המאפשר למשתמש להתנתק
    private void openDisconnectDialog() {
        Dialog builder = new Dialog(getContext());
        builder.setContentView(R.layout.dialog_disconnect);
        builder.setCancelable(true);


       Button btn_cancel = builder.findViewById(R.id.btn_cancel);
        Button btn_disconnect = builder.findViewById(R.id.btn_disconnect);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });

        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // מנתק
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "disconnected.", Toast.LENGTH_SHORT).show();
                setVisibility();
                builder.cancel();
            }
        });

        builder.create();
        builder.show();
    }

    //הפעולה פותחת דיאלוג המאפשר למשתמש להתחבר דרך אימייל וסיסמא
    void openSignInDialog() {


        Dialog builder = new Dialog(getContext());
        builder.setContentView(R.layout.dialog_sign_in);
        builder.setCancelable(true);


        EditText et_password = builder.findViewById(R.id.et_password);
        Button btn_cancel = builder.findViewById(R.id.btn_cancel);
        EditText et_email = builder.findViewById(R.id.et_email);
        Button btn_logIn = builder.findViewById(R.id.btn_logIn);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });

        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_password = et_password.getText().toString();
                String str_email = et_email.getText().toString();

                //check if et is empty
                if (str_email.equals("") || !isValidEmailAddress(str_email)) {
                    et_email.setError("ENTER A VALID EMAIL ");

                }
                if (str_password.equals("") ||str_password.length()<6) {
                    et_password.setError("ENTER PASSWORD (6 chars)");
                }
                Boolean validInfo = str_password.length()>=6 && isValidEmailAddress(str_email)&& !str_email.equals("") &&  !str_password.equals("");
                if (validInfo) {   //info is valid

                  //  builder.cancel();
                    setVisibility();
                    //logIn(str_email,str_password);
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    mAuth.signInWithEmailAndPassword(str_email, str_password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getActivity(), "logged in.",Toast.LENGTH_SHORT).show();
                                        setVisibility();
                                        builder.cancel();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "logged in failed, try other password/ email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }


            }
        });

        builder.create();
        builder.show();


    }

    // דיאלוג המאפשר למשתמש להירשם בעזרת מילוי פרטים
    void openSignUpDialog() {

        Dialog builder = new Dialog(getActivity());
        builder.setContentView(R.layout.dialog_sign_up);
        builder.setCancelable(true);


        Button btn_signUp = builder.findViewById(R.id.btn_signUp);
        Button btn_cancel = builder.findViewById(R.id.btn_cancel);
        EditText et_firstName = builder.findViewById(R.id.et_fistName);
        EditText et_lastName = builder.findViewById(R.id.et_lastName);
        EditText et_password = builder.findViewById(R.id.et_password);
        EditText et_email = builder.findViewById(R.id.et_email);


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
                String  str_lastName = et_lastName.getText().toString();
                String  str_password = et_password.getText().toString();
                String str_email = et_email.getText().toString();

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
                    setVisibility();
                    builder.cancel();
                    Person p = new Person(str_firstName, str_lastName, str_email, str_password);
                    registerUser(p);

                }


            }
        });

        builder.create();
        builder.show();
    }



                    // פעולות עזר

    // הפעולה מחברת את המשתמש בעזרת אימייל וסיסמא שהיא מקבלת מהדיאלוג
    private void logIn(String str_email, String str_password) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getActivity(), "logged in.",Toast.LENGTH_SHORT).show();
                            setVisibility();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "logged in failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    // שליחת אימייל וסיסמא לפייר-בייס (הרשמה)
    public void registerUser(Person p) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth.createUserWithEmailAndPassword(p.getEmail(), p.getPassword()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    db.collection("users").document(p.getEmail()+"").set(p);          // הוספת הפרטים למשתמש
                    Toast.makeText(getActivity(), "SIGN UP SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    setVisibility();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //הפעולה המעדכנת את נראות הכפתורים
    public void  setVisibility(){
        if (isSignIn()){
            btn_signUp_acc.setVisibility(View.GONE);
            btn_signIn_acc.setVisibility(View.GONE);
            btn_signOut_acc.setVisibility(View.VISIBLE);
            btn_AccountSettings_acc.setVisibility(View.VISIBLE);

        }
        else{
            btn_signUp_acc.setVisibility(View.VISIBLE);
            btn_signIn_acc.setVisibility(View.VISIBLE);
            btn_signOut_acc.setVisibility(View.GONE);
            btn_AccountSettings_acc.setVisibility(View.GONE);
        }
    }


}



