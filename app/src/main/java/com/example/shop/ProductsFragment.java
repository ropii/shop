package com.example.shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class ProductsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    ArrayList<Product> productArrayList = new ArrayList<>();
    ListView lvProduct;
    ProductAdapter productAdapter;


    public ProductsFragment() {
    }

    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        lvProduct = view.findViewById(R.id.lvProduct);
        createArLs();
        createAdapter();

        return view;
    }

    public void createArLs(){
        for (int i = 0; i <20 ; i++) {


        productArrayList.add(new Product("normal","house",R.drawable.acc_img,100));
        productArrayList.add(new Product("red","gaming",R.drawable.cart_img,5));
        }

    }

    public void createAdapter() {

        productAdapter = new ProductAdapter(getContext(), 0, 0, productArrayList);
        lvProduct.setAdapter(productAdapter);
    }


}