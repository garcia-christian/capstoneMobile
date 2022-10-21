package com.example.heremiStartup;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements ProductAdapter.OnProdListener {


    LoadingDia loadingDia;
    RecyclerView notifrecy, prodrecy;
    NotifAdapter notifAdapter = new NotifAdapter();
    ProductAdapter productAdapter = new ProductAdapter();
    List<modelReminderRes> allrem = new ArrayList<>();
    List<modelTimeRes> timeToday = new ArrayList<>();
    List<modelTimeRes> allTime = new ArrayList<>();
    List<modelReminderRes> allremToday = new ArrayList<>();
    List<modelPartialPharma> pharmas = new ArrayList<modelPartialPharma>();
    ArrayList<modelPartialPharma> pharmaslist = new ArrayList<>();
    ArrayList<modelLogRes> allData = new ArrayList<>();
    GeoApiContext mGeoApiContext;
    TextView pharma, price,locationtxt;
    LoadData loadData;
    FusedLocationProviderClient fusedLocationProviderClient;
    private FusedLocationProviderClient mLocation;
    private final static int REQUEST_CODE = 100;
    SwipeRefreshLayout swipeRefreshLayout;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment(LoadingDia loadingDia) {
        this.loadingDia = loadingDia;
    }

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @SuppressLint("NotifyDataSetChanged")
    private void getEssentials() {
        LoadData data = new LoadData(productAdapter);
        productAdapter = new ProductAdapter(getContext(),LoadData.prod, this);

        prodrecy.setAdapter(productAdapter);
        notifAdapter.notifyDataSetChanged();
    }


    @SuppressLint("NotifyDataSetChanged")
    private void initializeNotifView() {
        LoadData data = new LoadData(notifAdapter);
        notifAdapter = new NotifAdapter(getContext(),LoadData.allData);



        notifrecy.setAdapter(notifAdapter);


        notifAdapter.notifyDataSetChanged();

    }
    private void showBottomDialog(modelProduct product){
        final Dialog dialog = new Dialog(getContext());
       // LoadData data = new LoadData(productAdapter);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_bottom_dialog);



        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        LinearLayout pharmalist = (LinearLayout) dialog.findViewById(R.id.layoutEdit);
        ImageView prod_img = dialog.findViewById(R.id.prod_img);
        TextView brand_name = dialog.findViewById(R.id.brand_name);
        TextView generic_name = dialog.findViewById(R.id.generic_name);
        TextView pricerange = dialog.findViewById(R.id.price_range);
        TextView classification = dialog.findViewById(R.id.classification);
        TextView category = dialog.findViewById(R.id.bottom_medcat);
        pricerange.setText("₱"+df.format(product.getMin())+" - "+"₱"+df.format(product.getMax()));
        brand_name.setText(product.getGlobal_brand_name());
        generic_name.setText(product.getGlobal_generic_name());
        category.setText("Category: "+product.getMed_cat_desc());
        classification.setText("Classification: "+product.getClass_desc());
        Glide.with(dialog.getContext()).load(apiClient.BASEURL+product.getImage()).into(prod_img);
        Call<List<modelPartialPharma>> modelPartialPharmaCall = apiClient.getDeclaration().getAvailablePharma(product.getGlobal_med_id());

        modelPartialPharmaCall.enqueue(new Callback<List<modelPartialPharma>>() {
            @Override
            public void onResponse(Call<List<modelPartialPharma>> call, Response<List<modelPartialPharma>> response) {
            pharmas = response.body();
                pharmaLoader(dialog, pharmalist);
            }

            @Override
            public void onFailure(Call<List<modelPartialPharma>> call, Throwable t) {

            }
        });




    }
    private void pharmaLoader(Dialog dialog, LinearLayout pharmalist){


        for(int i=0;i<pharmas.size();i++){

            modelPartialPharma modelPartialPharma;
            modelPartialPharma = pharmas.get(i);

            final View pharm = dialog.getLayoutInflater().inflate(R.layout.layout_available_pharma_list,null, false);


            getLocation(modelPartialPharma,pharm);
            pharmalist.addView(pharm);
        }

    }
    private void loadPharma(modelPartialPharma modelPartialPharma,View view ){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(),pharmacyPage.class);
                i.putExtra("pharmaID",modelPartialPharma.getPharmacy_id());
                startActivity(i);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_home, container, false);


        ImageView shopbtn = parent.findViewById(R.id.shopbtn);
        notifrecy = parent.findViewById(R.id.notification_layout);
        prodrecy = parent.findViewById(R.id.essentials_list);
        locationtxt = parent.findViewById(R.id.address);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this.getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        initializeNotifView();
        getEssentials();
        notifrecy.setLayoutManager(layoutManager);
        prodrecy.setLayoutManager(layoutManager2);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        mLocation = LocationServices.getFusedLocationProviderClient(getContext());

        swipeRefreshLayout = parent.findViewById(R.id.swipeyyHome);
        //initData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                getLocation();
                initializeNotifView();
                getEssentials();
                notifAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        shopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(getContext(), Cart.class);
                startActivity(c);
            }
        });



         getLocation();
        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey("AIzaSyBK5v0ITOtgyaJAY1JfLdb_vfQYEDDHiBI")
                    .build();
        }
        loadingDia.dismissDialog();

        return parent;
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {
                    if(location !=null){
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addresses;
                        try {
                           addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                           if(addresses.get(0).getThoroughfare() != null){
                               locationtxt.setText(addresses.get(0).getThoroughfare()+", "+addresses.get(0).getSubLocality()+", "+addresses.get(0).getLocality());
                           }else{
                               locationtxt.setText(addresses.get(0).getSubLocality()+", "+addresses.get(0).getLocality());

                           }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

            });
        }else{
            askPermission();
        }


    }

    private void askPermission() {
        ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }else{
                Toast.makeText(getContext(), "PermissionRequired", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onProdClick(int position) {
        showBottomDialog(LoadData.prod.get(position));
    }

    @SuppressLint("MissingPermission")
    private void getLocation(modelPartialPharma modelPharmacy,View dialog) {
        mLocation.getLastLocation().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Location location = task.getResult();
                calculateDirections(location.getLatitude(),location.getLongitude(), modelPharmacy,dialog);
            }
        });

    }

    private void calculateDirections(double latitude, double longitude, modelPartialPharma modelPharmacy, View dialog){


        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                Double.parseDouble(modelPharmacy.getPharmacy_lat()),
                Double.parseDouble(modelPharmacy.getPharmacy_lng())
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(false);
        directions.origin(
                new com.google.maps.model.LatLng(
                        latitude, longitude
                )
        );

        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {

            @Override
            public void onResult(DirectionsResult result) {
                setTxtLocation(result, modelPharmacy,dialog);

            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "onFailuredsds: " + e.getMessage() );

            }
        });
    }

    private void setTxtLocation(DirectionsResult result, modelPartialPharma modelPharmacy, View dialog) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for(DirectionsRoute route: result.routes){
                    if(!(route.legs[0].distance.toString().isEmpty())){
                        modelPharmacy.setDistance(route.legs[0].distance.toString());
                        setData(modelPharmacy, dialog);
                    }


                }
            }
        });
    }

    private void setData(modelPartialPharma modelPharmacy, View dialog) {

        TextView pharm_name = dialog.findViewById(R.id.pharma_name);
        TextView price = dialog.findViewById(R.id.prod_price);
        TextView dist = dialog.findViewById(R.id.prod_dist);
        pharm_name.setText(modelPharmacy.getPharmacy_name());
        price.setText("₱"+df.format(modelPharmacy.getMed_price()));
        dist.setText(modelPharmacy.getDistance()+" • ");

        loadPharma(modelPharmacy,dialog);

    }

}