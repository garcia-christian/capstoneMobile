package com.example.heremiStartup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {

    LinearLayout linear_pharma,linear_category,linear_essential,linear_products;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private void getTopPharma() {
        final View toppharma = getLayoutInflater().inflate(R.layout.layout_toppharma, null, false);

        linear_pharma.addView(toppharma );


    }
    private void getTopPharma2() {
        final View toppharma = getLayoutInflater().inflate(R.layout.layout_toppharma2, null, false);

        linear_pharma.addView(toppharma );


    }
    private void getTopPharma3() {
        final View toppharma = getLayoutInflater().inflate(R.layout.layout_toppharma3, null, false);

        linear_pharma.addView(toppharma );


    }
    private void getTopPharma4() {
        final View toppharma = getLayoutInflater().inflate(R.layout.layout_toppharma4, null, false);

        linear_pharma.addView(toppharma );


    }
    private void getTopPharma5() {
        final View toppharma = getLayoutInflater().inflate(R.layout.layout_toppharma5, null, false);

        linear_pharma.addView(toppharma );


    }
    private void getTopPharma6() {
        final View toppharma = getLayoutInflater().inflate(R.layout.layout_toppharma6, null, false);

        linear_pharma.addView(toppharma );


    }
    private void getCategory() {
        final View category = getLayoutInflater().inflate(R.layout.layout_categories, null, false);

        linear_category.addView(category );


    }
    private void getCategory2() {
        final View category = getLayoutInflater().inflate(R.layout.layout_categories2, null, false);

        linear_category.addView(category );


    }
    private void getCategory3() {
        final View category = getLayoutInflater().inflate(R.layout.layout_categories3, null, false);

        linear_category.addView(category );


    }
    private void getCategory4() {
        final View category = getLayoutInflater().inflate(R.layout.layout_categories4, null, false);

        linear_category.addView(category );


    }
    private void getCategory5() {
        final View category = getLayoutInflater().inflate(R.layout.layout_categories5, null, false);

        linear_category.addView(category );


    }
    private void getEssentials() {
        final View essential = getLayoutInflater().inflate(R.layout.layout_essentials, null, false);

        linear_essential.addView(essential );

    }
    private void getEssentials2() {
        final View essential = getLayoutInflater().inflate(R.layout.layout_essentials2, null, false);

        linear_essential.addView(essential );

    }
    private void getEssentials3() {
        final View essential = getLayoutInflater().inflate(R.layout.layout_essentials3, null, false);

        linear_essential.addView(essential );

    }
    private void getProducts() {
        final View products = getLayoutInflater().inflate(R.layout.layout_products, null, false);

        linear_products.addView(products );

    }
    private void getProducts2() {
        final View products = getLayoutInflater().inflate(R.layout.layout_products2, null, false);

        linear_products.addView(products );

    }
    private void getProducts3() {
        final View products = getLayoutInflater().inflate(R.layout.layout_products3, null, false);

        linear_products.addView(products );

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_shop, container, false);

        ImageCarousel carousel = parent.findViewById(R.id.carousel);
        carousel.registerLifecycle(getLifecycle());
        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem(R.drawable.ad_1));
        list.add(new CarouselItem(R.drawable.ad_2));
        carousel.setData(list);
        linear_pharma = parent.findViewById(R.id.toparma_linear);
        linear_essential = parent.findViewById(R.id.essentials_list_shop);
        linear_category= parent.findViewById(R.id.categories_layout);
        linear_products= parent.findViewById(R.id.products_list_shop);
        getTopPharma();
        getTopPharma2();
        getTopPharma3();
        getTopPharma4();
        getTopPharma5();
        getTopPharma6();
        getCategory();
        getCategory2();
        getCategory3();
        getCategory4();
        getCategory5();
        getCategory();
        getEssentials();
        getEssentials2();
        getEssentials3();
        getEssentials();
        getProducts();
        getProducts2();
        getProducts3();
        getProducts();
        return parent;
    }
}