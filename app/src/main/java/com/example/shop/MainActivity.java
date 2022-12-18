package com.example.shop;

import static android.app.PendingIntent.getActivity;

import static com.example.shop.Functions.returnConnectedPerson;
import static com.example.shop.Functions.setPerson;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class MainActivity extends BasicActivity implements View.OnClickListener {
    static Person p = null;
    TextView tv;
    ChipNavigationBar chipNavigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.chipNavBar);
        setPerson();
        p = returnConnectedPerson();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProductsFragment()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.menu_products:
                        fragment = new ProductsFragment();
                        break;
                    case R.id.menu_accSettings:
                        fragment = new AccountFragment();
                        break;
                }
                if (fragment!=null){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                    ft.replace(R.id.fragment_container,fragment).commit();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}