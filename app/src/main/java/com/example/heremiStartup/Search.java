package com.example.heremiStartup;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity implements IndivProductAdapter.OnAllProdListener {
    TextView search;
    ImageView back;
    Integer quantity=1;
    modelPharmacy modelPharmacy;
    Switch distance,price;
    RecyclerView searchlist;
    modelCart modelCart;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    IndivProductAdapter adapter;
   // List<modelIndivProduct> products = new ArrayList<modelIndivProduct>();
    ArrayList<modelIndivProduct> prods = new ArrayList<modelIndivProduct>();
    ArrayList<modelIndivProduct> filtered = new ArrayList<modelIndivProduct>();
    ArrayList<modelIndivProduct> oldfiltered = new ArrayList<modelIndivProduct>();
    GeoApiContext mGeoApiContext;
    private FusedLocationProviderClient mLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search = findViewById(R.id.textView9);
        back = findViewById(R.id.back_btn_med);
        distance = findViewById(R.id.switch2);
        price = findViewById(R.id.switch1);
        searchlist = findViewById(R.id.search_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchlist.setLayoutManager(layoutManager);
        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(BuildConfig.MAPS_API_KEY)
                    .build();
        }
        mLocation = LocationServices.getFusedLocationProviderClient(this);
        getdata();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){

                }else{
                    Filter(s.toString());
                }
            }
        });
        distance.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                if(filtered.size()!=0){
                    if(distance.isChecked()){
                        Collections.sort(filtered,(o1,o2) -> o1.getDistancems().compareTo((o2.getDistancems())));
                        adapter.notifyDataSetChanged();
                    }else{
                        filtered = oldfiltered;
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });
        price.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                if(price.isChecked()){
                    Collections.sort(filtered,(o1,o2) -> o1.getMed_price().compareTo((o2.getMed_price())));
                    adapter.notifyDataSetChanged();
                }else{
                    filtered = oldfiltered;
                    adapter.notifyDataSetChanged();
                }

            }
        });



    }

    @SuppressLint("NotifyDataSetChanged")
    private void Filter(String s) {
        filtered.clear();
        oldfiltered.clear();
        if(s.isEmpty()){
            adapter.notifyDataSetChanged();
        }else{
            for(modelIndivProduct mprod: prods){
                if((mprod.getGlobal_brand_name()+""+mprod.getGlobal_generic_name()).toLowerCase().contains(s)){
                    filtered.add(mprod);
                    oldfiltered.add(mprod);
                }
                if(distance.isChecked()){
                    Collections.sort(filtered,(o1,o2) -> o1.getDistancems().compareTo((o2.getDistancems())));
                    adapter.notifyDataSetChanged();
                }
            adapter = new IndivProductAdapter(Search.this,filtered,this);
            searchlist.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            }
        }


    }

    private void getdata() {
        Call<List<modelIndivProduct>> call = apiClient.getDeclaration().indivProd();
        call.enqueue(new Callback<List<modelIndivProduct>>() {
            @Override
            public void onResponse(Call<List<modelIndivProduct>> call, Response<List<modelIndivProduct>> response) {
             prods.addAll(response.body());
             locate();
            }

            @Override
            public void onFailure(Call<List<modelIndivProduct>> call, Throwable t) {

            }
        });
    }




    @Override
    public void onAllProdClick(int position) {
        viewProd(prods.get(position));

    }


    private void locate() {
        for(modelIndivProduct mprod: prods){
            getLocation(mprod);
        }

    }
    @SuppressLint("MissingPermission")
    private void getLocation(modelIndivProduct modelIndivProduct) {
        mLocation.getLastLocation().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Location location = task.getResult();
                calculateDirections(location.getLatitude(),location.getLongitude(), modelIndivProduct);
            }
        });

    }

    private void calculateDirections(double latitude, double longitude, modelIndivProduct modelIndivProduct){


        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                modelIndivProduct.getPharmacy_lat(),
                modelIndivProduct.getPharmacy_lng()
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
                setTxtLocation(result, modelIndivProduct);

            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "onFailuredsds: " + e.getMessage() );

            }
        });
    }

    private void setTxtLocation(DirectionsResult result, modelIndivProduct modelIndivProduct) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for(DirectionsRoute route: result.routes){
                    if(!(route.legs[0].distance.toString().isEmpty())){
                       modelIndivProduct.setDistance(route.legs[0].distance.toString());
                       long l = route.legs[0].distance.inMeters;
                       modelIndivProduct.setDistancems((double) l);
                    }

                }
            }
        });
    }
    private void viewProd(modelIndivProduct product) {
        final Dialog dialog = new Dialog(Search.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_bottom_dialog_buy);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        ImageView prod_img = dialog.findViewById(R.id.prod_img);
        TextView brand_name = dialog.findViewById(R.id.brand_name);
        TextView generic_name = dialog.findViewById(R.id.generic_name);
        TextView pricerange = dialog.findViewById(R.id.price_range);
        TextView classification = dialog.findViewById(R.id.classification);
        TextView category = dialog.findViewById(R.id.bottom_medcat);
        ImageView addqty = dialog.findViewById(R.id.qtyadd);
        ImageView subqty = dialog.findViewById(R.id.qtysub);
        TextView cartqty = dialog.findViewById(R.id.qtycontext);
        Button addToCart = dialog.findViewById(R.id.materialButton3);
        Button buyNow = dialog.findViewById(R.id.materialButton3);
        TextView pharamatxt = dialog.findViewById(R.id.avaialbleinpharmatxt);

        pharamatxt.setText(product.getPharmacy_name()+": "+"10km");
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToCart(product,quantity,dialog);
            }
        });


        quantity=1;
        cartqty.setText(quantity+"");
        addqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity < 10){
                    quantity++;
                    cartqty.setText(quantity+"");
                }

            }
        });
        subqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity > 1){
                    quantity--;
                    cartqty.setText(quantity+"");
                }
            }
        });


        pricerange.setText("₱"+df.format(product.getMed_price()));
        brand_name.setText(product.getGlobal_brand_name());
        generic_name.setText(product.getGlobal_generic_name());
        category.setText("Category: "+product.getMed_cat_desc());
        classification.setText("Classification: "+product.getClass_desc());
        Glide.with(dialog.getContext()).load(apiClient.BASEURL+product.getImage()).into(prod_img);



    }

    private void addItemToCart(modelIndivProduct product, int qty, Dialog dialog) {
        modelCart = new modelCart(product.getMed_id(),product.getGlobal_med_id(),13,qty,MainActivity.UserID);
        Call<List<modelCart>> modelCartCall = apiClient.getDeclaration().saveCart(modelCart);
        modelCartCall.enqueue(new Callback<List<com.example.heremiStartup.modelCart>>() {
            @Override
            public void onResponse(Call<List<com.example.heremiStartup.modelCart>> call, Response<List<com.example.heremiStartup.modelCart>> response) {
                if(response.code()==401){
                    showAlertDialog("Would you like to replace the contents of the cart?", product,qty,dialog);

                    dialog.dismiss();
                }else{
                    Toast.makeText(Search.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<List<com.example.heremiStartup.modelCart>> call, Throwable t) {

            }
        });


    }
    private void showAlertDialog(String msg, modelIndivProduct product, int qty, Dialog dialog) {
        AlertDialog adialog = new AlertDialog.Builder(Search.this)
                .setTitle("Products from other pharmacy")
                .setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        executeWipe(product,qty,dialog);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        adialog.show();
    }

    private void executeWipe(modelIndivProduct product, int qty, Dialog dialog) {
        Call<ResponseBody> del = apiClient.getDeclaration().deleteCart(MainActivity.UserID);

        del.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(Search.this, "Cart Items Replaced", Toast.LENGTH_SHORT).show();
                    addItemToCart(product,quantity,dialog);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}