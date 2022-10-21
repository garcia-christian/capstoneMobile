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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<modelCartDetails> cart;
    private Context context;
    private OnCartListener onCartListener;

    public CartAdapter(Context context, ArrayList<modelCartDetails> cart) {
        this.cart = cart;
        this.context = context;

    }


    public CartAdapter() {

    }

    @NonNull
    @Override
    public CartAdapter.CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_cart, parent, false);
        return new CartHolder(view, onCartListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartHolder holder, int position) {
        modelCartDetails modelCartDetails = cart.get(position);
        holder.setCartDetails(modelCartDetails);
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public void setOnItemClickListener(OnCartListener listener) {
        onCartListener = listener;
    }

    public class CartHolder extends RecyclerView.ViewHolder  {
        TextView prodName,prodGeneric,prodCategory,prodClass,prodqty,price;
        ImageView prodPic, decre,incre;


        public CartHolder(@NonNull View itemView, OnCartListener onCartListener) {
            super(itemView);
            prodName = itemView.findViewById(R.id.med_name_cart);
            prodPic = itemView.findViewById(R.id.med_image);
            price = itemView.findViewById(R.id.med_price_cart);
            prodGeneric = itemView.findViewById(R.id.med_generic_cart);
            prodCategory = itemView.findViewById(R.id.med_category_cart);
            prodClass = itemView.findViewById(R.id.med_classification_cart);
            prodqty = itemView.findViewById(R.id.cart_qty);
            decre = itemView.findViewById(R.id.decrement);
            incre = itemView.findViewById(R.id.increment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onCartListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onCartListener.onCartClick(position);
                        }
                    }
                }
            });
            incre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onCartListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onCartListener.onIncrementClick(position);
                        }
                    }
                }
            });
            decre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onCartListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onCartListener.onDecrementClick(position);
                        }
                    }
                }
            });
        }

        void setCartDetails(modelCartDetails cart) {
            prodName.setText(cart.getGlobal_brand_name());
            prodGeneric.setText(cart.getGlobal_generic_name());
            prodCategory.setText(cart.getMed_cat_desc());
            prodClass.setText(cart.getClass_desc());
            Glide.with(context).load(apiClient.BASEURL+cart.getImage()).into(prodPic);
            price.setText(df.format(cart.getMed_price()));
            prodqty.setText(cart.getCart_qty());



        }



    }
    public interface OnCartListener{
        void onCartClick(int position);
        void onIncrementClick(int position);
        void onDecrementClick(int position);
    }

}
