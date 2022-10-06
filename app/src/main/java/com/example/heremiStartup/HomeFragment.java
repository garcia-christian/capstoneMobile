package com.example.heremiStartup;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    LinearLayout linear_notif, linear_essential,linear_doctors;
    LoadingDia loadingDia;
    RecyclerView notifrecy, prodrecy;
    NotifAdapter notifAdapter = new NotifAdapter();
    ProductAdapter productAdapter = new ProductAdapter();
    List<modelReminderRes> allrem = new ArrayList<>();
    List<modelTimeRes> timeToday = new ArrayList<>();
    List<modelTimeRes> allTime = new ArrayList<>();
    List<modelReminderRes> allremToday = new ArrayList<>();
    ArrayList<modelLogRes> allData = new ArrayList<>();
    LoadData loadData;
    SwipeRefreshLayout swipeRefreshLayout;

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
        productAdapter = new ProductAdapter(getContext(),LoadData.prod);
        LoadData data = new LoadData(productAdapter);
        prodrecy.setAdapter(productAdapter);
        notifAdapter.notifyDataSetChanged();
    }


    @SuppressLint("NotifyDataSetChanged")
    private void initializeNotifView() {
        notifAdapter = new NotifAdapter(getContext(),LoadData.allData);

        LoadData data = new LoadData(notifAdapter);

        notifrecy.setAdapter(notifAdapter);


        notifAdapter.notifyDataSetChanged();

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


}