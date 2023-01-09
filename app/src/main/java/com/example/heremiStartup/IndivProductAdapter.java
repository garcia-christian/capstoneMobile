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

public class IndivProductAdapter extends RecyclerView.Adapter<IndivProductAdapter.ProductHolder> {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<modelIndivProduct> prod;
    private Context context;
    private OnAllProdListener onProdListener;

    public IndivProductAdapter(Context context, ArrayList<modelIndivProduct> prod, OnAllProdListener onProdListener) {
        this.prod = prod;
        this.context = context;
        this.onProdListener = onProdListener;
    }

    public IndivProductAdapter() {

    }

    @NonNull
    @Override
    public IndivProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_products2, parent, false);
        return new ProductHolder(view, onProdListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IndivProductAdapter.ProductHolder holder, int position) {
        modelIndivProduct modelIndivProduct = prod.get(position);
        holder.setProdDetails(modelIndivProduct);
    }

    @Override
    public int getItemCount() {
        return prod.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView prodName,prodgeneric,price,category,pharma,dist;
        ImageView prodPic;
        OnAllProdListener onProdListener;
        public ProductHolder(@NonNull View itemView, OnAllProdListener onProdListener) {
            super(itemView);
            prodName = itemView.findViewById(R.id.med_brand);
            prodPic = itemView.findViewById(R.id.imageView);
            price = itemView.findViewById(R.id.price_etc);
            prodgeneric = itemView.findViewById(R.id.med_generic);
            category = itemView.findViewById(R.id.med_category);
            pharma = itemView.findViewById(R.id.pharmaname);
            dist = itemView.findViewById(R.id.distance);

            this.onProdListener = onProdListener;
            itemView.setOnClickListener(this);


        }

        void setProdDetails(modelIndivProduct prod) {
            prodName.setText(prod.getGlobal_brand_name());
            Glide.with(context).load(apiClient.BASEURL+prod.getImage()).into(prodPic);
            price.setText(prod.getMed_price().toString());
            prodgeneric.setText(prod.getGlobal_generic_name());
            category.setText(prod.getMed_cat_desc());
            dist.setText(prod.getDistance());
            pharma.setText(prod.getPharmacy_name());
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
