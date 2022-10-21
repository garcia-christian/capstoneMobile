package com.example.heremiStartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkout extends AppCompatActivity {
    ArrayList<modelCartDetails> cartList = new ArrayList<>();
    List<modelCartDetails> cartDetails = new ArrayList<>();
    ImageView backbtn;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    LinearLayout prodlst;
    TextView subttl;
    Button checkout;
    LoadingDia loadingMap= new LoadingDia(Checkout.this);
    modelPharmacy modelPharmacy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        prodlst = findViewById(R.id.prodlistview);
        subttl = findViewById(R.id.subtotal);
        backbtn =findViewById(R.id.back_btn_med);
        checkout = findViewById(R.id.submit_med);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingMap.loadingDialog();
                Intent intent = new  Intent(Checkout.this, MainActivity.class);
                intent.putExtra("destination", "map");
                intent.putExtra("pharma_id", modelPharmacy.getPharmacy_id());
                    startActivity(intent);

            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initData();


    }

    private void initData() {
        Call<List<modelCartDetails>> cartcall = apiClient.getDeclaration().getCart();

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
        calculate();
        getCartPharma(cartDetails.get(0).getCart_pharmacy_id());
        runUI();
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
            int cartqty = Integer.parseInt(cartList.get(i).getCart_qty());
            subtotal += cartList.get(i).getMed_price()*cartqty;
            price.setText(""+df.format(cartList.get(i).getMed_price()*cartqty));
            prodlst.addView(plist);
        }
        subttl.setText("₱"+ df.format(subtotal));

    }

    private void calculate() {
    }

    private void getCartPharma(int id) {
        Call<List<modelPharmacy>> call = apiClient.getDeclaration().getPharma(id);
        call.enqueue(new Callback<List<modelPharmacy>>() {
            @Override
            public void onResponse(Call<List<modelPharmacy>> call, Response<List<modelPharmacy>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        modelPharmacy = response.body().get(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<modelPharmacy>> call, Throwable t) {

            }
        });

    }
}