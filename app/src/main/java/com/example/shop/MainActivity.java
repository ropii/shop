package com.example.shop;

import static android.app.PendingIntent.getActivity;

import static com.example.shop.Functions.generalConnectedPerson;
import static com.example.shop.Functions.returnConnectedPerson;
import static com.example.shop.Functions.setPerson;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.checkerframework.checker.nullness.qual.NonNull;


public class MainActivity extends BasicActivity implements View.OnClickListener {
    static Person p = null;
    TextView tv;
    ChipNavigationBar chipNavigationBar;

    public DrawerLayout drawerLayout;//
    public ActionBarDrawerToggle actionBarDrawerToggle;//
    ImageButton btn_musicOf, btn_musicOn;
    Boolean boolean_music = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.chipNavBar);
        setPerson();
        p = returnConnectedPerson();

        drawerLayout = findViewById(R.id.my_drawer_layout);//
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);//
        drawerLayout.addDrawerListener(actionBarDrawerToggle);//
        actionBarDrawerToggle.syncState();//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //

        btn_musicOf = findViewById(R.id.btn_musicOf);
        btn_musicOn = findViewById(R.id.btn_musicOn);
        btn_musicOf.setOnClickListener(this);
        btn_musicOn.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProductsFragment()).commit();
        chipNavigationBar.setItemSelected(R.id.menu_products,true);

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {


            // הפעולה בודקת האם לחצו על בר הניווט ומעבירה פרגמאנט בהתאם לכך
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case  R.id.menu_about:
                        fragment = new AboutFragment();
                        break;
                    case R.id.menu_products:
                        fragment = new ProductsFragment();
                        break;
                    case R.id.menu_accSettings:
                        fragment = new AccountFragment();
                        break;
                    case R.id.menu_upload:
                        if (generalConnectedPerson==null || !(generalConnectedPerson instanceof Partner)){
                            //chipNavigationBar.setItemEnabled(R.id.menu_upload,false);
                            chipNavigationBar.setItemSelected(R.id.menu_accSettings,true);
                            if (generalConnectedPerson==null){
                                //todo please log in
                            }
                            else {
                                {
                                    //todo please become a partner
                                }
                            }
                        }

                        else {
                            fragment = new UploadItemFragment();}
                        break;
                }
                if (fragment!=null){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left);
                    ft.replace(R.id.fragment_container,fragment).commit();

                }
            }
        });
    }



    @Override
    public void onClick(View view) {



    }

    @Override//
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//
        //
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {//
            return true;//
        }//
//
//
        return super.onOptionsItemSelected(item);//
    }//
}