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

public class PharmacyProductsAdapter extends RecyclerView.Adapter<PharmacyProductsAdapter.ItemHolder> {

    public ArrayList<modelPharmaProducts> prod;
    private Context context;
    private OnProdListener onProdListener;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public PharmacyProductsAdapter() {
    }

    public PharmacyProductsAdapter(ArrayList<modelPharmaProducts> prod, Context context, OnProdListener onProdListener) {
        this.prod = prod;
        this.context = context;
        this.onProdListener = onProdListener;
    }

    @NonNull
    @Override
    public PharmacyProductsAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_essentials2, parent, false);
        return new PharmacyProductsAdapter.ItemHolder(view, onProdListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyProductsAdapter.ItemHolder holder, int position) {
        modelPharmaProducts modelPharmaProducts = prod.get(position);
        holder.setDetails(modelPharmaProducts);
    }

    @Override
    public int getItemCount() {
        return prod.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView prodName, price,prodgeneric;
        ImageView prodPic;
        OnProdListener onProdListener;
        public ItemHolder(@NonNull View itemView, OnProdListener onProdListener) {
            super(itemView);
            prodName = itemView.findViewById(R.id.essentials_label2);
            prodPic = itemView.findViewById(R.id.allprod_image);
            price = itemView.findViewById(R.id.essentials_price2);
            prodgeneric = itemView.findViewById(R.id.generic_name);
            this.onProdListener = onProdListener;
            itemView.setOnClickListener(this);
        }

        public void setDetails(modelPharmaProducts modelPharmaProducts) {
            prodName.setText(modelPharmaProducts.getGlobal_brand_name());
            Glide.with(context).load(apiClient.BASEURL+modelPharmaProducts.getImage()).into(prodPic);
            price.setText("₱"+df.format(modelPharmaProducts.getMed_price()));
            prodgeneric.setText(modelPharmaProducts.getGlobal_generic_name());
        }

        @Override
        public void onClick(View view) {

        }
    }
    public interface OnProdListener{
        void onProdClick(int position);
    }
}
