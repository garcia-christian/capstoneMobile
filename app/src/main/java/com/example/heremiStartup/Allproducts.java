package com.example.heremiStartup;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Allproducts extends Fragment implements PharmacyProductsAdapter.OnProdListener {
    RecyclerView allprod;
    PharmacyProductsAdapter pharmacyProductsAdapter = new PharmacyProductsAdapter();
    List<modelPharmaProducts> modelPharmaProducts = new ArrayList<>();
    ArrayList<modelPharmaProducts> modelPharmaProductsAL = new ArrayList<>();
    Integer pharmaID = 0;

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


    @Override
    public void onProdClick(int position) {

    }
}