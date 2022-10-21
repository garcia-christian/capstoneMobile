package com.example.heremiStartup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.ProductHolder> {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<modelAllProduct> prod;
    private Context context;
    private OnAllProdListener onProdListener;

    public AllProductAdapter(Context context, ArrayList<modelAllProduct> prod, OnAllProdListener onProdListener) {
        this.prod = prod;
        this.context = context;
        this.onProdListener = onProdListener;
    }

    public AllProductAdapter() {

    }

    @NonNull
    @Override
    public AllProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_products, parent, false);
        return new ProductHolder(view, onProdListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductAdapter.ProductHolder holder, int position) {
        modelAllProduct modelAllProduct = prod.get(position);
        holder.setProdDetails(modelAllProduct);
    }

    @Override
    public int getItemCount() {
        return prod.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView prodName,prodgeneric,price,category;
        ImageView prodPic;
        OnAllProdListener onProdListener;
        public ProductHolder(@NonNull View itemView, OnAllProdListener onProdListener) {
            super(itemView);
            prodName = itemView.findViewById(R.id.med_brand);
            prodPic = itemView.findViewById(R.id.imageView);
            price = itemView.findViewById(R.id.price_etc);
            prodgeneric = itemView.findViewById(R.id.med_generic);
            category = itemView.findViewById(R.id.med_category);
            this.onProdListener = onProdListener;
            itemView.setOnClickListener(this);


        }

        void setProdDetails(modelAllProduct prod) {
            prodName.setText(prod.getGlobal_brand_name());
            Glide.with(context).load(apiClient.BASEURL+prod.getImage()).into(prodPic);
            price.setText("₱"+df.format(prod.getMin())+" - "+"₱"+df.format(prod.getMax()));
            prodgeneric.setText(prod.getGlobal_generic_name());
            category.setText(prod.getMed_cat_desc());
        }

        @Override
        public void onClick(View view) {
            onProdListener.onAllProdClick(getAdapterPosition());
        }
    }
    public interface OnAllProdListener{
        void onAllProdClick(int position);
    }
}
