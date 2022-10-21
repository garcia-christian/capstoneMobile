package com.example.heremiStartup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    ConstraintLayout orderTab;
    FloatingActionButton fabMain, fab1,fab2;
    Animation open,close,from,to;
    Boolean clicked = false, locationpermission;
    LoadData data;
    Integer pharmaId = 0;
    TextView pharmaname,pharmaloc,orderno,pickuptime;
    private int GPS_REQUEST_CODE = 9001;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
         LoadingDia loadingDiaProfile = new LoadingDia(MainActivity.this);
        LoadingDia loadingDiaHome = new LoadingDia(MainActivity.this);
        LoadingDia loadingShop = new LoadingDia(MainActivity.this);
        LoadingDia loadingMap= new LoadingDia(MainActivity.this);
        checkMyPermission();
        orderTab = findViewById(R.id.order_tab);
        pharmaname = findViewById(R.id.pharmaName);
        pharmaloc = findViewById(R.id.pharmLocation);
        orderno = findViewById(R.id.orderNumber);
        pickuptime = findViewById(R.id.orderNumber);


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

                //bottomNavigationView.setSelectedItemId(R.id.nav_location);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav);

        loadingDiaHome.loadingDialog();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment(loadingDiaHome)).commit();
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);



        isGPSenabled();


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
                        loadingMap.loadingDialog();
                        fragment = new LocationFragment(loadingMap,locationpermission,pharmaId);

                        break;
                    case R.id.nav_profile:
                        loadingDiaProfile.loadingDialog();
                        fragment = new ProfileFragment(loadingDiaProfile);

                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();



                return true;
            }
        });
        int sessionId = getIntent().getIntExtra("pharma_id",0);
        if(sessionId!=0){

            getDetails(sessionId);
        }else{
            orderTab.setVisibility(View.INVISIBLE);
        }
        pharmaId = sessionId;

        orderTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomNavigationView.getSelectedItemId()!=R.id.nav_location){


                    bottomNavigationView.setSelectedItemId(R.id.nav_location);
                }


            }
        });






    }

    private void getDetails(int pharma_id) {
        Call<List<modelPharmacy>> call = apiClient.getDeclaration().getPharma(pharma_id);
        call.enqueue(new Callback<List<modelPharmacy>>() {
            @Override
            public void onResponse(Call<List<modelPharmacy>> call, Response<List<modelPharmacy>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        pharmaname.setText(response.body().get(0).getPharmacy_name());
                        pharmaloc.setText(response.body().get(0).getPharmacy_location());
                        orderTab.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<modelPharmacy>> call, Throwable t) {

            }
        });

    }


    private boolean isGPSenabled() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(providerEnabled){
            System.out.println(true);
            return true;
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("GPS Permission Required")
                    .setPositiveButton("Yes",((dialogInterface,i)->{
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    })).setCancelable(false).show();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GPS_REQUEST_CODE){
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(providerEnabled){
                Toast.makeText(this, "GPS ENABLED", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "GPS DISABLED", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void checkMyPermission() {
        Toast.makeText(MainActivity.this, "Permission Check", Toast.LENGTH_SHORT).show();

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                locationpermission = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);
                locationpermission = false;
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();

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