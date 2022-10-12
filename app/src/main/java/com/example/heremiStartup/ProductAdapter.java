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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<modelProduct> prod;
    private Context context;
    private OnProdListener onProdListener;

    public ProductAdapter(Context context, ArrayList<modelProduct> prod, OnProdListener onProdListener) {
        this.prod = prod;
        this.context = context;
        this.onProdListener = onProdListener;
    }

    public ProductAdapter() {

    }

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_essentials, parent, false);
        return new ProductHolder(view, onProdListener);
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

    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView prodName,price;
        ImageView prodPic;
        OnProdListener onProdListener;
        public ProductHolder(@NonNull View itemView, OnProdListener onProdListener) {
            super(itemView);
            prodName = itemView.findViewById(R.id.essentials_label1);
            prodPic = itemView.findViewById(R.id.prod_pic);
            price = itemView.findViewById(R.id.essentials_price);
            this.onProdListener = onProdListener;
            itemView.setOnClickListener(this);


        }

        void setProdDetails(modelProduct prod) {
            prodName.setText(prod.getGlobal_brand_name());
            Glide.with(context).load(apiClient.BASEURL+prod.getImage()).into(prodPic);
            price.setText("₱"+df.format(prod.getMin())+" - "+"₱"+df.format(prod.getMax()));
        }

        @Override
        public void onClick(View view) {
            onProdListener.onProdClick(getAdapterPosition());
        }
    }
    public interface OnProdListener{
        void onProdClick(int position);
    }
}
