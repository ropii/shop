package com.example.shop;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;


public class ProductsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    ArrayList<Product> productArrayList = new ArrayList<>();
    ArrayList<Product> productArrayList2 = new ArrayList<>();

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
            createArLs();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        productArrayList2.addAll(productArrayList);
        lvProduct = view.findViewById(R.id.lvProduct);
        createArLs();
        productArrayList.addAll(productArrayList2);
        Log.d("ada", "on create");
        return view;
    }

    public void createArLs(){
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                productArrayList.add( document.toObject(Product.class));

                            }
                            createAdapter();
                            progressDialog.dismiss();

                        } else {
                            Log.d("aaccvv", "Error getting documents: ", task.getException());
                        }
                    }
                });

        for (int i = 0; i <15 ; i++) {

        productArrayList.add(new Product("normal","house","https://firebasestorage.googleapis.com/v0/b/shop-d4e6c.appspot.com/o/productsImg%2F583ef80e-93c2-401c-aa86-28c37ba14464?alt=media&token=4d88f04d-5ae2-45b7-977b-107689fee12c",100,"583ef80e-93c2-401c-aa86-28c37ba14464"));
            productArrayList.add(new Product("dog","pet","https://firebasestorage.googleapis.com/v0/b/shop-d4e6c.appspot.com/o/productsImg%2F8cd0595f-9b52-4e45-9aa5-7401c201c3d7?alt=media&token=ed51464c-9d88-4b7d-99d7-b32bef8ed91d",100,"583ef80e-93c2-401c-aa86-28c37ba14464"));
        }

    }

    public void createAdapter() {

        productAdapter = new ProductAdapter(getContext(), 0, 0, productArrayList);
        lvProduct.setAdapter(productAdapter);
    }


}