package com.example.shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
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

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        btn_signUp_acc = view.findViewById(R.id.btn_signUp_acc);
        btn_signIn_acc = view.findViewById(R.id.btn_signIn_acc);
        btn_signOut_acc = view.findViewById(R.id.btn_signOut_acc);
        btn_AccountSettings_acc = view.findViewById(R.id.btn_AccountSettings_acc);
        btn_signUp_acc.setOnClickListener(this);
        btn_signIn_acc.setOnClickListener(this);
        btn_signOut_acc.setOnClickListener(this);
        btn_AccountSettings_acc.setOnClickListener(this);

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onClick(View view) {
        if (view==btn_signUp_acc){
        }

    }



}