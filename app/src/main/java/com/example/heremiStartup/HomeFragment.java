package com.example.heremiStartup;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements ProductAdapter.OnProdListener {


    LoadingDia loadingDia;
    RecyclerView notifrecy, prodrecy;
    NotifAdapter notifAdapter = new NotifAdapter();
    ProductAdapter productAdapter = new ProductAdapter();
    List<modelReminderRes> allrem = new ArrayList<>();
    List<modelTimeRes> timeToday = new ArrayList<>();
    List<modelTimeRes> allTime = new ArrayList<>();
    List<modelReminderRes> allremToday = new ArrayList<>();
    List<modelPartialPharma> pharmas = new ArrayList<modelPartialPharma>();
    ArrayList<modelPartialPharma> pharmaslist = new ArrayList<>();
    ArrayList<modelLogRes> allData = new ArrayList<>();
    TextView pharma, price;
    LoadData loadData;
    SwipeRefreshLayout swipeRefreshLayout;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment(LoadingDia loadingDia) {
        this.loadingDia = loadingDia;
    }

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @SuppressLint("NotifyDataSetChanged")
    private void getEssentials() {
        LoadData data = new LoadData(productAdapter);
        productAdapter = new ProductAdapter(getContext(),LoadData.prod, this);

        prodrecy.setAdapter(productAdapter);
        notifAdapter.notifyDataSetChanged();
    }


    @SuppressLint("NotifyDataSetChanged")
    private void initializeNotifView() {
        LoadData data = new LoadData(notifAdapter);
        notifAdapter = new NotifAdapter(getContext(),LoadData.allData);



        notifrecy.setAdapter(notifAdapter);


        notifAdapter.notifyDataSetChanged();

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
        pricerange.setText("₱"+df.format(product.getMin())+" - "+"₱"+df.format(product.getMax()));
        brand_name.setText(product.getGlobal_brand_name());
        generic_name.setText(product.getGlobal_generic_name());
        Glide.with(dialog.getContext()).load(apiClient.BASEURL+product.getImage()).into(prod_img);
        Call<List<modelPartialPharma>> modelPartialPharmaCall = apiClient.getDeclaration().getAvailablePharma(product.getGlobal_med_id());

        modelPartialPharmaCall.enqueue(new Callback<List<modelPartialPharma>>() {
            @Override
            public void onResponse(Call<List<modelPartialPharma>> call, Response<List<modelPartialPharma>> response) {
            pharmas = response.body();
                pharmaLoader(dialog, pharmalist);
            }

            @Override
            public void onFailure(Call<List<modelPartialPharma>> call, Throwable t) {

            }
        });




    }
    private void pharmaLoader(Dialog dialog, LinearLayout pharmalist){


        for(int i=0;i<pharmas.size();i++){
            modelPartialPharma modelPartialPharma = new modelPartialPharma();
            modelPartialPharma = pharmas.get(i);
            final View pharm = dialog.getLayoutInflater().inflate(R.layout.layout_available_pharma_list,null, false);
            TextView pharm_name = pharm.findViewById(R.id.pharma_name);
            TextView price = pharm.findViewById(R.id.prod_price);
            pharm_name.setText(pharmas.get(i).pharmacy_name);
            price.setText("20.00km"+" • "+"₱"+df.format(pharmas.get(i).getMed_price()));
            pharmalist.addView(pharm);
            loadPharma(modelPartialPharma,pharm);
        }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_home, container, false);



        notifrecy = parent.findViewById(R.id.notification_layout);
        prodrecy = parent.findViewById(R.id.essentials_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this.getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        initializeNotifView();
        getEssentials();
        notifrecy.setLayoutManager(layoutManager);
        prodrecy.setLayoutManager(layoutManager2);

        swipeRefreshLayout = parent.findViewById(R.id.swipeyyHome);
        //initData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                initializeNotifView();
                getEssentials();
                notifAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });




        loadingDia.dismissDialog();

        return parent;
    }


    @Override
    public void onProdClick(int position) {
        showBottomDialog(LoadData.prod.get(position));
    }
}