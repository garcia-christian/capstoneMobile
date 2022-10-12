package com.example.heremiStartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pharmacyPage extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView name, loc;
    private Integer pharmacyID;
    private ImageView backbtn;
    modelPharmacy modelPharmacy = new modelPharmacy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_page);
        name = findViewById(R.id.pharmacyName);
        loc  = findViewById(R.id.locationPharma);
        tabLayout = findViewById(R.id.tablayoutpharma);
        viewPager = findViewById(R.id.allProdView);
        tabLayout.setupWithViewPager(viewPager);
        backbtn = findViewById(R.id.back_btn_med);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
       pharmacyID =  i.getIntExtra("pharmaID",4);
       fetchData(pharmacyID);


       TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
       tabLayoutAdapter.addFragment(new Allproducts(pharmacyID),"Allproducts");
        tabLayoutAdapter.addFragment(new Allproducts(pharmacyID),"");
        tabLayoutAdapter.addFragment(new Allproducts(pharmacyID),"");

       viewPager.setAdapter(tabLayoutAdapter);

    }

    private void fetchData(Integer pharmacyID) {
    Call<List<com.example.heremiStartup.modelPharmacy>> modelPharmacyCall = apiClient.getDeclaration().getPharma(pharmacyID);
    modelPharmacyCall.enqueue(new Callback<List<com.example.heremiStartup.modelPharmacy>>() {
        @Override
        public void onResponse(Call<List<com.example.heremiStartup.modelPharmacy>> call, Response<List<com.example.heremiStartup.modelPharmacy>> response) {
            modelPharmacy = response.body().get(0);
            onFetch();
        }

        @Override
        public void onFailure(Call<List<com.example.heremiStartup.modelPharmacy>> call, Throwable t) {

        }
    });
    }

    private void onFetch() {
        name.setText(modelPharmacy.getPharmacy_name());
        loc.setText(modelPharmacy.getPharmacy_location());
    }
}