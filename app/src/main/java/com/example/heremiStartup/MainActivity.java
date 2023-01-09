package com.example.heremiStartup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static Integer UserID = 0;
    public static boolean hasPending = false;
    private AlarmManager alarmManager;
    modelCustomer modelCustomer;
    Integer orderID;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private PendingIntent pendingIntent;
    BottomNavigationView bottomNavigationView;
    ConstraintLayout orderTab;
    FloatingActionButton fabMain, fab1,fab2;
    Animation open,close,from,to;
    Boolean clicked = false, locationpermission;
    LoadData data;
    Integer pharmaId = 0;
    TextView pharmaname,pharmaloc,orderno,pickuptime,orderStat;
    Socket mSocket;
    public String message;
    private int GPS_REQUEST_CODE = 9001;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        LoadingDia loadingDiaProfile = new LoadingDia(MainActivity.this);
        LoadingDia loadingDiaHome = new LoadingDia(MainActivity.this);
        LoadingDia loadingShop = new LoadingDia(MainActivity.this);
        LoadingDia loadingMap= new LoadingDia(MainActivity.this);
        preferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = preferences.edit();
        checkMyPermission();
        getUser();
        orderTab = findViewById(R.id.order_tab);
        pharmaname = findViewById(R.id.pharmaName);
        pharmaloc = findViewById(R.id.pharmLocation);
        orderno = findViewById(R.id.orderNumber);
        pickuptime = findViewById(R.id.orderNumber);
        orderStat = findViewById(R.id.textView13);
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

        NotificationChannel();

        orderTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomNavigationView.getSelectedItemId()!=R.id.nav_location){


                    bottomNavigationView.setSelectedItemId(R.id.nav_location);
                }


            }
        });

        checkAccount(UserID);

        int sessionId = getIntent().getIntExtra("pharma_id",0);
        int orderId = getIntent().getIntExtra("pharma_id",0);
        pharmaId = sessionId;

        if(sessionId!=0){


        }else{
            orderTab.setVisibility(View.INVISIBLE);
        }

    }



    private void NotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "hereMeds";
            String description = "hereMeds Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Notification", name, importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }
    private void getUser() {
    Call<modelCustomer> call = apiClient.getDeclaration().getUser(preferences.getString("token",""));

    call.enqueue(new Callback<modelCustomer>() {
        @Override
        public void onResponse(Call<modelCustomer> call, Response<modelCustomer> response) {
            if(response.isSuccessful()){
                modelCustomer = response.body();
                setUser(modelCustomer);
            }
        }

        @Override
        public void onFailure(Call<modelCustomer> call, Throwable t) {

        }
    });

    }

    private void setUser(modelCustomer modelCustomer) {
        UserID = modelCustomer.getCustomer_id();


    }

    private void checkAccount(int tempUserID) {
        Call<List<modelOrders>> call = apiClient.getDeclaration().getPending(tempUserID);
        call.enqueue(new Callback<List<modelOrders>>() {
            @Override
            public void onResponse(Call<List<modelOrders>> call, Response<List<modelOrders>> response) {
                if(response.isSuccessful()){
                    if(response.body().size() != 0){
                        hasPending = true;
                        getDetails(response.body().get(0).getPharmacy_id(),response.body().get(0).getOrder_id());
                        pharmaId = response.body().get(0).getPharmacy_id();
                        orderID = response.body().get(0).getOrder_id();
                        if(response.body().get(0).getOrder_status() == 1){
                            orderStat.setText("Order Confirmed");
                        }
                        SocketHandler.setSocket();
                        mSocket = SocketHandler.getSocket();
                        mSocket.connect();
                        mSocket.emit("login", tempUserID);
                        setListening();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<modelOrders>> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void getDetails(int pharma_id, int orderId) {





        Call<List<modelPharmacy>> call = apiClient.getDeclaration().getPharma(pharma_id);
        call.enqueue(new Callback<List<modelPharmacy>>() {
            @Override
            public void onResponse(Call<List<modelPharmacy>> call, Response<List<modelPharmacy>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        pharmaname.setText(response.body().get(0).getPharmacy_name());
                        pharmaloc.setText(response.body().get(0).getPharmacy_location());
                        orderno.setText("Order#: #"+orderId);
                        orderTab.setVisibility(View.VISIBLE);
                        nofify();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<modelPharmacy>> call, Throwable t) {

            }
        });

    }

    private void nofify() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sendeID",UserID);
            jsonObject.put("receiverID",pharmaId);
            jsonObject.put("type",1);
            mSocket.emit("sendNotif",jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setListening() {
        Toast.makeText(this, "Listening", Toast.LENGTH_SHORT).show();

            mSocket.on("changeStatus", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("called 1");
                    if(args[0] != null){
                        System.out.println("called 2");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("called 3");
                                if(Integer.parseInt(args[0].toString()) == 1){
                                    orderStat.setText("Order Confirmed");
                                }
                                if(Integer.parseInt(args[0].toString()) == 2){
                                    orderCanceled();
                                }
                                if(Integer.parseInt(args[0].toString()) == 3){
                                    orderComplete();
                                }


                            }
                        });
                    }


                }
            });

    }
    private void orderComplete() {
        orderTab.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "The order is Completed", Toast.LENGTH_SHORT).show();
        Intent intent = new  Intent(MainActivity.this, orderComplete.class);
        intent.putExtra("order_id", orderID);
        startActivity(intent);
        hasPending = false;
        pharmaId=0;
    }
    private void orderCanceled() {
        orderTab.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "The order is cancelled by the Pharmacy", Toast.LENGTH_SHORT).show();
        hasPending = false;
        pharmaId=0;
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

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
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