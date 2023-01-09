package com.example.heremiStartup;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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

public class Checkout extends AppCompatActivity {
    ArrayList<modelCartDetails> cartList = new ArrayList<>();
    modelOrders modelOrders;
    ArrayList<modelOrdersItems> orderlist = new ArrayList<>();
    ArrayList<modelDiscount> discountm = new ArrayList<>();
    modelDiscount selectedDisc;
    List<modelCartDetails> cartDetails = new ArrayList<>();
    ArrayList<String> discountstrings = new ArrayList<>();
    ArrayList<String> poptstring = new ArrayList<>();
    double sub=0.0, total=0.0, discount=0.0;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    LinearLayout prodlst;
    Integer select =0, select2 = 0;
    TextView subttl,totaltxt,disctxt,subtxt;
    TextView locationtxt,dist,time,phdist,phname;
    Button checkout;
    LoadingDia loadingMap= new LoadingDia(Checkout.this);
    modelPharmacy modelPharmacy;
    ConstraintLayout prescrip;
    ImageView discselector, backbtn, pmtbtn;
    TextView discountdesc, pmtopt;
    private FusedLocationProviderClient mLocation;
    GeoApiContext mGeoApiContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        prodlst = findViewById(R.id.prodlistview);
        subttl = findViewById(R.id.subtotal);
        backbtn =findViewById(R.id.back_btn_med);
        checkout = findViewById(R.id.submit_med);
        totaltxt = findViewById(R.id.totalnum);
        disctxt = findViewById(R.id.totaldisc);
        subtxt = findViewById(R.id.sub);
        locationtxt = findViewById(R.id.locationtext);
        dist = findViewById(R.id.locationdist);
        time = findViewById(R.id.time);
        phdist = findViewById(R.id.phrma);
        phname = findViewById(R.id.textView9);
        prescrip = findViewById(R.id.noticely);
        discselector = findViewById(R.id.discopt);
        discountdesc = findViewById(R.id.discname);
        pmtbtn = findViewById(R.id.paymentopn);
        pmtopt = findViewById(R.id.poptbtn);
        poptstring.add("Cash");
       // poptstring.add("Gcash (Comming Soon)");
        pmtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Checkout.this
                );
                builder.setTitle("Select Discount");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(poptstring.toArray(new String[poptstring.size()]), select2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });
        discselector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Checkout.this

                );
                builder.setTitle("Select Discount");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(discountstrings.toArray(new String[discountstrings.size()]), select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which!=0){
                            discountdesc.setText(discountstrings.get(which));
                            selectedDisc = discountm.get(which-1);
                            Toast.makeText(Checkout.this, ""+selectedDisc.getDiscount_id(), Toast.LENGTH_SHORT).show();
                            select = which;
                            discount = Double.parseDouble(selectedDisc.getDiscount_cost());
                            calculator();
                        } else {
                            discountdesc.setText("Select your Discount");
                            selectedDisc = null;
                            select = 0;
                            discount =0;

                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        calculator();
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOutFun();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initData();
        mLocation = LocationServices.getFusedLocationProviderClient(this);
        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(BuildConfig.MAPS_API_KEY)
                    .build();
        }
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
                       //dist.setText(route.legs[0].distance.toString());
                       // name.setText(modelPharmacy.getPharmacy_name()+" • ");
                       // loc.setText(modelPharmacy.getPharmacy_location()+" • ");
                      //  pharmaimg.setVisibility(View.VISIBLE);
                        locationtxt.setText(modelPharmacy.getPharmacy_location());
                        dist.setText(route.legs[0].distance.toString()+" • "+route.legs[0].duration.toString());
                        phdist.setText("Distance from you: "+route.legs[0].distance.toString());
                        phname.setText(modelPharmacy.getPharmacy_name());
                        time.setText(route.legs[0].distance.inMeters+"");
                    }

                }
            }
        });
    }



    private void checkOutFun() {
        modelOrders = new modelOrders(
                MainActivity.UserID,
                modelPharmacy.getPharmacy_id(),
                0,
                0,
                total,
                sub,
                selectedDisc ==null ? 0.0 : Double.parseDouble(selectedDisc.getDiscount_cost()),
                selectedDisc ==null ? 1 : selectedDisc.getDiscount_id());


        Toast.makeText(Checkout.this, "saving", Toast.LENGTH_SHORT).show();
        Call<modelOrders> call = apiClient.getDeclaration().saveOrder(modelOrders);
          call.enqueue(new Callback<com.example.heremiStartup.modelOrders>() {
                @Override
                public void onResponse(Call<com.example.heremiStartup.modelOrders> call, Response<com.example.heremiStartup.modelOrders> response) {
                    saveItems(response.body().getOrder_id());
                }

                @Override
                public void onFailure(Call<com.example.heremiStartup.modelOrders> call, Throwable t) {
                    Toast.makeText(Checkout.this, "fail"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



      //  saveItems();

        }

    private void saveItems(Integer id) {
        for(int i=0; i<cartList.size();i++){
            modelOrdersItems modelOrdersItems = new modelOrdersItems(
                    id,
                    cartList.get(i).getCart_med_id(),
                    Integer.parseInt(cartList.get(i).getCart_qty()));
            orderlist.add(modelOrdersItems);

    }
        upload(id);


}

    private void upload(Integer id) {

        for (int i = 0; i < orderlist.size(); i++) {
            Call<ResponseBody> call = apiClient.getDeclaration().saveItems(orderlist.get(i));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Checkout.this, "sss", Toast.LENGTH_SHORT).show();
                        executeWipe();
                        openHome(id);

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(Checkout.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    private void executeWipe() {
        Call<ResponseBody> del = apiClient.getDeclaration().deleteCart(MainActivity.UserID);

        del.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void openHome(Integer id) {
         loadingMap.loadingDialog();
         Intent intent = new  Intent(Checkout.this, MainActivity.class);
         intent.putExtra("destination", "map");
         intent.putExtra("pharma_id", modelPharmacy.getPharmacy_id());
         intent.putExtra("order_id",id);
         startActivity(intent);
         finish();
    }

    private void initData() {
        Call<List<modelCartDetails>> cartcall = apiClient.getDeclaration().getCart(MainActivity.UserID);

        cartcall.enqueue(new Callback<List<modelCartDetails>>() {
            @Override
            public void onResponse(Call<List<modelCartDetails>> call, Response<List<modelCartDetails>> response) {
                cartDetails = response.body();
                extractData();

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

        getCartPharma(cartDetails.get(0).getCart_pharmacy_id());
        getDiscount(cartDetails.get(0).getCart_pharmacy_id());
        runUI();
    }

    private void getDiscount(int cart_pharmacy_id) {
        Call<List<modelDiscount>> call = apiClient.getDeclaration().getDiscount(cart_pharmacy_id);
        call.enqueue(new Callback<List<modelDiscount>>() {
            @Override
            public void onResponse(Call<List<modelDiscount>> call, Response<List<modelDiscount>> response) {
               if(response.isSuccessful()){
                   discountm.addAll(response.body());
                    if(discountm.size()!=0){
                         arrayextract(discountm);
                    }
               }
               }

            @Override
            public void onFailure(Call<List<modelDiscount>> call, Throwable t) {

            }
        });

    }

    private void arrayextract(ArrayList<modelDiscount> discountm) {
        discountstrings.add("None");
        for(int i=0; i<discountm.size();i++){
            discountstrings.add(discountm.get(i).getDiscount_desc());
        }
    }

    private void runUI() {
        double subtotal =0.0;
        for(int i = 0; i<cartList.size();i++){
            final View plist = getLayoutInflater().inflate(R.layout.layout_checkout_list, null, false);
            TextView qty, gname,bname,price;
            qty = plist.findViewById(R.id.qty);
            gname = plist.findViewById(R.id.genericname);
            bname = plist.findViewById(R.id.brandname);
            price = plist.findViewById(R.id.price);
            qty.setText("x"+cartList.get(i).getCart_qty());
            gname.setText(cartList.get(i).getGlobal_generic_name());
            bname.setText(cartList.get(i).getGlobal_brand_name());
            if(cartList.get(i).getClass_desc().equals("Prescription Drug")){
                prescrip.setVisibility(View.VISIBLE);
            }
            int cartqty = Integer.parseInt(cartList.get(i).getCart_qty());
            subtotal += cartList.get(i).getMed_price()*cartqty;
            price.setText(""+df.format(cartList.get(i).getMed_price()*cartqty));
            prodlst.addView(plist);
        }

        sub = subtotal;
       calculator();

    }

    private void calculator() {
        subttl.setText("₱"+ df.format(sub));
        total = (sub - (sub*discount));
        totaltxt.setText("₱"+df.format(total));
        disctxt.setText("-₱"+df.format(discount));
        subtxt.setText("₱"+df.format(sub));
    }


    private void getCartPharma(int id) {
        Call<List<modelPharmacy>> call = apiClient.getDeclaration().getPharma(id);
        call.enqueue(new Callback<List<modelPharmacy>>() {
            @Override
            public void onResponse(Call<List<modelPharmacy>> call, Response<List<modelPharmacy>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        modelPharmacy = response.body().get(0);
                        getLocation(modelPharmacy);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<modelPharmacy>> call, Throwable t) {

            }
        });

    }
}