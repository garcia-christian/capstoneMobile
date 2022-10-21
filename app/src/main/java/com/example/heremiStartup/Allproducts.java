package com.example.heremiStartup;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Allproducts extends Fragment implements PharmacyProductsAdapter.OnPharmaProdListener {
    RecyclerView allprod;
    PharmacyProductsAdapter pharmacyProductsAdapter = new PharmacyProductsAdapter();
    List<modelPharmaProducts> modelPharmaProducts = new ArrayList<>();
    ArrayList<modelPharmaProducts> modelPharmaProductsAL = new ArrayList<>();
    Integer pharmaID = 0, quantity=1;
    modelPharmacy modelPharmacy;
    modelCart modelCart;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public Allproducts(Integer pharmaID) {
        this.pharmaID = pharmaID;
    }

    public Allproducts() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent =  inflater.inflate(R.layout.fragment_allproducts, container, false);
        allprod = parent.findViewById(R.id.allprod_recycler);

        allprod.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        initializeData();
        return parent;
    }

    private void initializeData() {
        Call<List<modelPharmaProducts>> modelPharmaProdCall = apiClient.getDeclaration().getPharmaProducts(pharmaID);
        modelPharmaProdCall.enqueue(new Callback<List<modelPharmaProducts>>() {
            @Override
            public void onResponse(Call<List<modelPharmaProducts>> call, Response<List<modelPharmaProducts>> response) {
                modelPharmaProducts = response.body();
                scanData();
            }

            @Override
            public void onFailure(Call<List<modelPharmaProducts>> call, Throwable t) {

            }
        });
        Call<List<modelPharmacy>> modelpharmacall = apiClient.getDeclaration().getPharma(pharmaID);
        modelpharmacall.enqueue(new Callback<List<com.example.heremiStartup.modelPharmacy>>() {
            @Override
            public void onResponse(Call<List<com.example.heremiStartup.modelPharmacy>> call, Response<List<com.example.heremiStartup.modelPharmacy>> response) {
                modelPharmacy = response.body().get(0);
            }

            @Override
            public void onFailure(Call<List<com.example.heremiStartup.modelPharmacy>> call, Throwable t) {

            }
        });

    }

    private void scanData() {
        modelPharmaProductsAL.clear();
        for (int i =0;i<modelPharmaProducts.size();i++){
            modelPharmaProductsAL.add(modelPharmaProducts.get(i));
        }
        setUI();
    }

    private void setUI() {
        pharmacyProductsAdapter = new PharmacyProductsAdapter(modelPharmaProductsAL,getContext(),this);
        allprod.setAdapter(pharmacyProductsAdapter);

    }



    private void viewProd(modelPharmaProducts product) {
        final Dialog dialog = new Dialog(getContext());
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

        pharamatxt.setText(modelPharmacy.getPharmacy_name()+": "+"10km");
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

    private void addItemToCart(modelPharmaProducts product, int qty, Dialog dialog) {
        modelCart = new modelCart(product.getMed_id(),product.getGlobal_med_id(),pharmaID,qty,1);
        Call<List<modelCart>> modelCartCall = apiClient.getDeclaration().saveCart(modelCart);
        modelCartCall.enqueue(new Callback<List<com.example.heremiStartup.modelCart>>() {
            @Override
            public void onResponse(Call<List<com.example.heremiStartup.modelCart>> call, Response<List<com.example.heremiStartup.modelCart>> response) {
                if(response.code()==401){
                    showAlertDialog("Would you like to replace the contents of the cart?", product,qty,dialog);

                   dialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<List<com.example.heremiStartup.modelCart>> call, Throwable t) {

            }
        });


    }

    private void showAlertDialog(String msg, modelPharmaProducts product, int qty, Dialog dialog) {
        AlertDialog adialog = new AlertDialog.Builder(this.getContext())
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

    private void executeWipe(modelPharmaProducts product, int qty, Dialog dialog) {
       Call<ResponseBody> del = apiClient.getDeclaration().deleteCart(1);

       del.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if (response.isSuccessful()){
                   Toast.makeText(getContext(), "Cart Wiped", Toast.LENGTH_SHORT).show();
                   addItemToCart(product,quantity,dialog);
               }
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {

           }
       });

    }


    @Override
    public void OnPharmaProdClick(int position) {
       if(modelPharmaProductsAL.get(position).getMed_qty()==0){
           Toast.makeText(getContext(), "This Product is Unavailable", Toast.LENGTH_SHORT).show();
       }else{
           viewProd(modelPharmaProductsAL.get(position));
       }

    }
}