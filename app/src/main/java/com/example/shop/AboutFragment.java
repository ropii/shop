package com.example.shop;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;


public class AboutFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    VideoView videoView;
    TextView tv_about;
    String about_string = "האפליקציה הינה חנות וירטואלית שבה כל אחד יכול להעלות " +
            "מוצרים ולקנות מוצרים. למטה אתם רואים תפריט שבו נמצא מסך האודת," +
            " מסך הגדרות, מסך העלת מוצרים, מסך העגלה ומסך שבו אפשר לראות המוצרים המוצעים למכירה";
    private String mParam1;
    private String mParam2;

    public AboutFragment() {
    }

    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
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
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        videoView = view.findViewById(R.id.videoView_aboutF);
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.about_video);
        videoView.setVideoURI(uri);
        videoView.start();
        tv_about = view.findViewById(R.id.tv_about);
        tv_about.setText(about_string);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
    }
}