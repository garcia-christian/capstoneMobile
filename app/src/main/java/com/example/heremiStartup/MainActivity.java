package com.example.heremiStartup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabMain, fab1,fab2;
    Animation open,close,from,to;
    Boolean clicked = false;
    LoadData data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
         LoadingDia loadingDiaProfile = new LoadingDia(MainActivity.this);
        LoadingDia loadingDiaHome = new LoadingDia(MainActivity.this);
        LoadingDia loadingShop = new LoadingDia(MainActivity.this);
        fabMain = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab2);
        fab2 = findViewById(R.id.fab3);
        open = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        close = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        from = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        to = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainClicked();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, addMed.class));

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, setReminder.class));

            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav);
        loadingDiaHome.loadingDialog();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment(loadingDiaHome)).commit();
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:

                        loadingDiaHome.loadingDialog();
                        fragment = new HomeFragment(loadingDiaHome);

                        break;
                    case R.id.nav_shop:
                        loadingShop.loadingDialog();
                        fragment = new ShopFragment(loadingShop);

                        break;
                    case R.id.nav_location:

                        fragment = new LocationFragment();

                        break;
                    case R.id.nav_profile:
                        loadingDiaProfile.loadingDialog();
                        fragment = new ProfileFragment(loadingDiaProfile);

                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
                System.out.println("dataLoad");


                return true;
            }
        });


    }



    private void mainClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        if(!clicked)
            clicked = true;
        else
            clicked = false;
    }

    private void setAnimation(Boolean clicked) {
        if (!clicked){
            fab1.startAnimation(from);
            fab2.startAnimation(from);
            fabMain.startAnimation(open);
        }else {
            fab1.startAnimation(to);
            fab2.startAnimation(to);
            fabMain.startAnimation(close);
        }
    }

    private void setVisibility(Boolean clicked) {
        if (!clicked){
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
        }else {
            fab1.setVisibility(View.INVISIBLE);
            fab2.setVisibility(View.INVISIBLE);
        }
    }
}