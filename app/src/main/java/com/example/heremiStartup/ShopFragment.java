package com.example.heremiStartup;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopFragment extends Fragment implements ProductAdapter.OnProdListener, AllProductAdapter.OnAllProdListener {

    LoadingDia loadingDia;
    LinearLayout linear_pharma,linear_category,linear_essential,linear_products;
    List<modelPharmacy> pharmas = new ArrayList<modelPharmacy>();
    ProductAdapter productAdapter = new ProductAdapter();
    AllProductAdapter allProductAdapter = new AllProductAdapter();
    List<modelPartialPharma> partialPharmas = new ArrayList<modelPartialPharma>();
    RecyclerView prodrecy,allprodlist;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public ShopFragment() {
        // Required empty public constructor
    }
    public ShopFragment(LoadingDia loadingDia) {
        this.loadingDia = loadingDia;
    }

    private void getTopPharma() {


        Call<List<modelPharmacy>> modelpharmacycall = apiClient.getDeclaration().getPharma();

        modelpharmacycall.enqueue(new Callback<List<modelPharmacy>>() {
            @Override
            public void onResponse(Call<List<modelPharmacy>> call, Response<List<modelPharmacy>> response) {
                pharmas = response.body();
                getTopPharmaUI();
            }

            @Override
            public void onFailure(Call<List<modelPharmacy>> call, Throwable t) {

            }
        });




    }

    private void getTopPharmaUI() {
      for(int i =0; i<pharmas.size();i++){
          final View toppharma = getLayoutInflater().inflate(R.layout.layout_toppharma, null, false);
          ImageView pharmapic = toppharma.findViewById(R.id.toppharm_pic);
          Glide.with(getContext()).load(apiClient.BASEURL+pharmas.get(i).getLogo()).into(pharmapic);
          linear_pharma.addView(toppharma );
          loadPharma(pharmas.get(i),toppharma);

      }

    }

    private void loadPharma(modelPharmacy modelPharmacy, View toppharma) {
        toppharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(),pharmacyPage.class);
                i.putExtra("pharmaID",modelPharmacy.getPharmacy_id());
                startActivity(i);
            }
        });
    }
    private void loadPharma(modelPartialPharma modelPartialPharma,View view ){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(modelPartialPharma.getPharmacy_id());
                Intent i = new Intent(getContext(),pharmacyPage.class);
                i.putExtra("pharmaID",modelPartialPharma.getPharmacy_id());
                startActivity(i);
            }
        });
    }
    private void getCategory() {
        final View category = getLayoutInflater().inflate(R.layout.layout_categories, null, false);

        linear_category.addView(category );


    }
    private void getCategory2() {
        final View category = getLayoutInflater().inflate(R.layout.layout_categories2, null, false);

        linear_category.addView(category );


    }
    private void getCategory3() {
        final View category = getLayoutInflater().inflate(R.layout.layout_categories3, null, false);

        linear_category.addView(category );


    }
    private void getCategory4() {
        final View category = getLayoutInflater().inflate(R.layout.layout_categories4, null, false);

        linear_category.addView(category );


    }
    private void getCategory5() {
        final View category = getLayoutInflater().inflate(R.layout.layout_categories5, null, false);

        linear_category.addView(category );


    }
    private void getEssentials() {
        LoadData data = new LoadData(productAdapter);
        productAdapter = new ProductAdapter(getContext(),LoadData.prod, this);

        prodrecy.setAdapter(productAdapter);


    }

    private void getProducts() {
        LoadData data = new LoadData(productAdapter);
        allProductAdapter = new AllProductAdapter(getContext(),LoadData.allprod,this);

        allprodlist.setAdapter(allProductAdapter);

    }
    private void getProducts2() {
        final View products = getLayoutInflater().inflate(R.layout.layout_products2, null, false);

        linear_products.addView(products );

    }
    private void getProducts3() {
        final View products = getLayoutInflater().inflate(R.layout.layout_products3, null, false);

        linear_products.addView(products );

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_shop, container, false);
        ImageView cart =  parent.findViewById(R.id.shopbtn_shop);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(getContext(), Cart.class);
                startActivity(c);
            }
        });



        ImageCarousel carousel = parent.findViewById(R.id.carousel);
        carousel.registerLifecycle(getLifecycle());
        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem(R.drawable.ad_1));
        list.add(new CarouselItem(R.drawable.ad_2));
        carousel.setData(list);
        linear_pharma = parent.findViewById(R.id.toparma_linear);
        prodrecy = parent.findViewById(R.id.essentials_list_shop);
        allprodlist = parent.findViewById(R.id.products_list_shop);


        linear_category= parent.findViewById(R.id.categories_layout);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this.getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        prodrecy.setLayoutManager(layoutManager2);
        allprodlist.setLayoutManager(layoutManager);


        getTopPharma();

        getCategory();
        getCategory2();
        getCategory3();
        getCategory4();
        getCategory5();
        getCategory();
        getEssentials();

        getProducts();

        loadingDia.dismissDialog();
        return parent;
    }

    @Override
    public void onProdClick(int position) {
        showBottomDialog(LoadData.prod.get(position));

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
                partialPharmas = response.body();
                pharmaLoader(dialog, pharmalist);
            }

            @Override
            public void onFailure(Call<List<modelPartialPharma>> call, Throwable t) {

            }
        });




    }

    private void pharmaLoader(Dialog dialog, LinearLayout pharmalist) {
        for(int i=0;i<partialPharmas.size();i++){
            modelPartialPharma modelPartialPharma = new modelPartialPharma();
            modelPartialPharma = partialPharmas.get(i);

            final View pharm = dialog.getLayoutInflater().inflate(R.layout.layout_available_pharma_list,null, false);
            TextView pharm_name = pharm.findViewById(R.id.pharma_name);
            TextView price = pharm.findViewById(R.id.prod_price);

            pharm_name.setText(partialPharmas.get(i).pharmacy_name);
            price.setText("20.00km"+" • "+"₱"+df.format(partialPharmas.get(i).getMed_price()));

            pharmalist.addView(pharm);
            loadPharma(modelPartialPharma,pharm);
        }
    }

    @Override
    public void onAllProdClick(int position) {
        showBottomDialog(LoadData.allprod.get(position));
    }

    private void showBottomDialog(modelAllProduct product) {
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
                partialPharmas = response.body();
                pharmaLoader(dialog, pharmalist);
            }

            @Override
            public void onFailure(Call<List<modelPartialPharma>> call, Throwable t) {

            }
        });



    }
}