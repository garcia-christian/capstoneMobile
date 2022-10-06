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

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private ArrayList<modelProduct> prod;
    private Context context;

    public ProductAdapter(Context context, ArrayList<modelProduct> prod) {
        this.prod = prod;
        this.context = context;
    }

    public ProductAdapter() {

    }

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_essentials, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, int position) {
        modelProduct modelProduct = prod.get(position);
        holder.setProdDetails(modelProduct);
    }

    @Override
    public int getItemCount() {
        return prod.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        TextView prodName;
        ImageView prodPic;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.essentials_label1);
            prodPic = itemView.findViewById(R.id.prod_pic);



        }

        void setProdDetails(modelProduct prod) {
            prodName.setText(prod.getGlobal_brand_name());
            Glide.with(context).load(apiClient.BASEURL+prod.getImage()).into(prodPic);
        }
    }
}
