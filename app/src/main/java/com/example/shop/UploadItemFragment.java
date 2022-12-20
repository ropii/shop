package com.example.shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


public class UploadItemFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";private static final String ARG_PARAM2 = "param2";private String mParam1;private String mParam2;

    ImageButton ib_upload,ib_camera,ib_gallery;
    EditText ed_name, ed_category, ed_price, ed_description;
    public UploadItemFragment() {
    }


    public static UploadItemFragment newInstance(String param1, String param2) {
        UploadItemFragment fragment = new UploadItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_upload_item, container, false);
        ed_name = view.findViewById(R.id.ed_name);
        ed_category = view.findViewById(R.id.ed_category);
        ed_price = view.findViewById(R.id.ed_price);
        ed_description = view.findViewById(R.id.ed_description);
        ib_upload = view.findViewById(R.id.ib_upload);
        ib_camera = view.findViewById(R.id.ib_camera);
        ib_gallery = view.findViewById(R.id.ib_gallery);
        return view;
    }
}