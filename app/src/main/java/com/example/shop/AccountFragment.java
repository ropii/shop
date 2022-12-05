package com.example.shop;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;


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
        //return inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

    @Override
    public void onClick(View view) {
        Log.d("sagie", "openDisconnectDialog: ");
        Log.d("fragment_on_click", "onClick: ");

        if (view==btn_signOut_acc){
            openDisconnectDialog();
        }

    }

    private void openDisconnectDialog() {
        Log.d("sagie", "openDisconnectDialog: ");
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
                builder.cancel();
            }
        });

        builder.create();
        builder.show();
    }


}