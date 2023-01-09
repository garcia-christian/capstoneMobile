package com.example.heremiStartup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class orderComplete extends AppCompatActivity {

    TextView pharma, subtotal, discount,total, sub2,done;
    LinearLayout prodlst;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    ArrayList<modelOrderItems> orders = new ArrayList<>();
    modelOrders modelOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        pharma = findViewById(R.id.pharma);
        subtotal = findViewById(R.id.subtotal);
        discount = findViewById(R.id.discount);
        total = findViewById(R.id.total);
        sub2 = findViewById(R.id.subtotal3);
        done = findViewById(R.id.textView17);
        done.setOnClickListener(v -> {
            onBackPressed();
        });
        int orderID = getIntent().getIntExtra("order_id",0);
        getdata(orderID);
        prodlst = findViewById(R.id.prodlistview);
        ImageCarousel carousel = findViewById(R.id.carousel);
        carousel.registerLifecycle(getLifecycle());
        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem(R.drawable.ad_1));
        list.add(new CarouselItem(R.drawable.ad_2));
        carousel.setData(list);
    }

    private void getItems(int id) {
    Call<List<modelOrderItems>> call = apiClient.getDeclaration().getOrderItems(id);
    call.enqueue(new Callback<List<modelOrderItems>>() {
        @Override
        public void onResponse(Call<List<modelOrderItems>> call, Response<List<modelOrderItems>> response) {
           if(response.isSuccessful()){
               orders.addAll(response.body());
               loadUI();
           }
        }

        @Override
        public void onFailure(Call<List<modelOrderItems>> call, Throwable t) {

        }
    });

    }

    private void loadUI() {
        for(int i=0; i<orders.size(); i++){
            final View plist = getLayoutInflater().inflate(R.layout.layout_checkout_list, null, false);
            TextView qty, gname,bname,price;
            qty = plist.findViewById(R.id.qty);
            gname = plist.findViewById(R.id.genericname);
            bname = plist.findViewById(R.id.brandname);
            price = plist.findViewById(R.id.price);
            qty.setText("x"+orders.get(i).getQuantity());
            gname.setText(orders.get(i).getGlobal_generic_name());
            bname.setText(orders.get(i).getGlobal_brand_name());
            price.setText(""+df.format(orders.get(i).getMed_price()*orders.get(i).getQuantity()));
            prodlst.addView(plist);
        }
        pharma.setText(modelOrders.getPharmacy_name());
        sub2.setText("₱"+df.format(modelOrders.getSubtotal())+"");
        subtotal.setText("₱"+df.format(modelOrders.getSubtotal())+"");
        discount.setText("₱"+df.format(modelOrders.getSubtotal() - (modelOrders.getSubtotal()*modelOrders.getDiscount_cost()))+"");
        total.setText("₱"+df.format(modelOrders.getOrder_totalprice())+"");

    }

    private void getdata(int id) {
        Call<List<modelOrders>> call =  apiClient.getDeclaration().getOrder(id);
        call.enqueue(new Callback<List<com.example.heremiStartup.modelOrders>>() {
            @Override
            public void onResponse(Call<List<com.example.heremiStartup.modelOrders>> call, Response<List<com.example.heremiStartup.modelOrders>> response) {
               if(response.isSuccessful()){
                   modelOrders = response.body().get(0);
                   getItems(id);
               }
            }

            @Override
            public void onFailure(Call<List<com.example.heremiStartup.modelOrders>> call, Throwable t) {

            }
        });
    }

}