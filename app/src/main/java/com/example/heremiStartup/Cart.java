package com.example.heremiStartup;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity{
    RecyclerView cartView;
    List<modelCartDetails> cartDetails = new ArrayList<>();
    ArrayList<modelCartDetails> cartList = new ArrayList<>();
    CartAdapter cartAdapter;
    ImageView back,pharmaimg;
    Button checkout;
    TextView name,loc,dist, subtotaltxt;
    GeoApiContext mGeoApiContext;
    boolean cartempty = true;
    private FusedLocationProviderClient mLocation;
    private static final DecimalFormat df = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initData();
        cartView = findViewById(R.id.cart_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartView.setLayoutManager(layoutManager);
        pharmaimg = findViewById(R.id.pharmacyimg);
        back = findViewById(R.id.back_btn_med);
        name = findViewById(R.id.pharmacyName);
        loc = findViewById(R.id.pharmacyLoc);
        dist = findViewById(R.id.pharmacyKm);
        subtotaltxt = findViewById(R.id.totalnum);
        checkout = findViewById(R.id.submit_med);


        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(BuildConfig.MAPS_API_KEY)
                    .build();
        }
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if(cartempty){
                  Toast.makeText(Cart.this, "The Cart is Empty", Toast.LENGTH_SHORT).show();
              }else if(MainActivity.hasPending){
                  Toast.makeText(Cart.this, "There is currently an order in progress", Toast.LENGTH_SHORT).show();
              }else{
                  Intent c = new Intent(Cart.this, Checkout.class);
                  startActivity(c);
              }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mLocation = LocationServices.getFusedLocationProviderClient(this);

    }
    @SuppressLint("MissingPermission")
    private void getLocation(modelPharmacy modelPharmacy) {
        mLocation.getLastLocation().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Location location = task.getResult();
                calculateDirections(location.getLatitude(),location.getLongitude(), modelPharmacy);
            }
        });

    }

    private void calculateDirections(double latitude, double longitude, modelPharmacy modelPharmacy){


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
                setTxtLocation(result, modelPharmacy);

            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "onFailuredsds: " + e.getMessage() );

            }
        });
    }

    private void setTxtLocation(DirectionsResult result, modelPharmacy modelPharmacy) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for(DirectionsRoute route: result.routes){
                    if(!(route.legs[0].distance.toString().isEmpty())){
                        dist.setText(route.legs[0].distance.toString());
                        name.setText(modelPharmacy.getPharmacy_name()+" • ");
                        loc.setText(modelPharmacy.getPharmacy_location()+" • ");
                        pharmaimg.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

    private void initData() {
        Call<List<modelCartDetails>> cartcall = apiClient.getDeclaration().getCart(MainActivity.UserID);

        cartcall.enqueue(new Callback<List<modelCartDetails>>() {
            @Override
            public void onResponse(Call<List<modelCartDetails>> call, Response<List<modelCartDetails>> response) {
                cartDetails = response.body();
                if(cartDetails.size() != 0){
                    cartempty=false;
                    extractData();
                }else{
                    name.setText("Cart is Empty");
                    cartempty=true;
                }

            }

            @Override
            public void onFailure(Call<List<modelCartDetails>> call, Throwable t) {

            }
        });
    }



    private void extractData() {
        cartList.clear();
        double subtotal = 0;
        for(int i=0;i<cartDetails.size();i++){
            cartList.add(cartDetails.get(i));
        }
        calculate();
        getCartPharma(cartDetails.get(0).getCart_pharmacy_id());

        runUI();
    }


    private void calculate() {
        double subtotal = 0;
        int cartqty = 0;
        for(int i=0;i<cartList.size();i++){
            cartqty = Integer.parseInt(cartList.get(i).getCart_qty());
            subtotal = subtotal + (cartList.get(i).getMed_price()*cartqty);
        }
        subtotaltxt.setText("₱"+df.format(subtotal));
    }


    private void getCartPharma(int id) {
        Call<List<modelPharmacy>> call = apiClient.getDeclaration().getPharma(id);
         call.enqueue(new Callback<List<modelPharmacy>>() {
             @Override
             public void onResponse(Call<List<modelPharmacy>> call, Response<List<modelPharmacy>> response) {
                 if(response.isSuccessful()){

                     getLocation(response.body().get(0));
                 }
             }

             @Override
             public void onFailure(Call<List<modelPharmacy>> call, Throwable t) {

             }
         });

    }



    private void runUI() {
        cartAdapter = new CartAdapter(Cart.this, cartList);
        cartView.setAdapter(cartAdapter);
        cartAdapter.setOnItemClickListener(new CartAdapter.OnCartListener() {
            @Override
            public void onCartClick(int position) {

            }

            @Override
            public void onIncrementClick(int position) {
                addClick(position);
            }

            @Override
            public void onDecrementClick(int position) {
                subtractClick(position);
            }
        });


    }

    private void subtractClick(int position) {
        int qty = Integer.parseInt(cartList.get(position).getCart_qty());
        if (qty > 1) {
            qty--;
            cartList.get(position).setCart_qty(String.valueOf(qty));
            cartAdapter.notifyItemChanged(position);
            decrement(cartList.get(position).getCart_med_id());
            calculate();
        }
    }

    private void addClick(int position) {
        int qty = Integer.parseInt(cartList.get(position).getCart_qty());
        if (qty < 10) {
            qty++;
            cartList.get(position).setCart_qty(String.valueOf(qty));
            cartAdapter.notifyItemChanged(position);
            increment(cartList.get(position).getCart_med_id());
            calculate();
        }
    }
    private void increment(int id){
        Call<ResponseBody> call = apiClient.getDeclaration().incrementCart(id,MainActivity.UserID);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
    }
    private void decrement(int id){
        Call<ResponseBody> call = apiClient.getDeclaration().decrementCart(id,MainActivity.UserID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}