package com.example.shop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {


    Context context;
    List<Product> objects;
    public ProductAdapter(Context context, int resource, int textViewResourceId, List<Product> objects){
        super(context, resource, textViewResourceId, objects);
        this.context=context;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_list_view_row_layout,parent,false);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        TextView tvCategory = view.findViewById(R.id.tvCategory);
        ImageView ivProduct=view.findViewById(R.id.ivProduct);
        Product temp = objects.get(position);

        ivProduct.setImageResource(temp.getImageId());
        tvPrice.setText(String.valueOf(temp.getPrice()));
        tvName.setText(temp.getName());
        tvCategory.setText(temp.getCategory());


        return view;
    }


}
