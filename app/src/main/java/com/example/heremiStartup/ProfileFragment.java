package com.example.heremiStartup;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    LinearLayout medlist,remlist,missedrem;
    LoadingDia loadingDia;
    LoadData data;
    SwipeRefreshLayout swpiey;
    List<modelReminderRes> allrem = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment(LoadingDia loadingDia) {
        this.loadingDia = loadingDia;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    private void getMeds() {
        final View meds = getLayoutInflater().inflate(R.layout.layout_medicines, null, false);

        medlist.addView(meds);


    }
    private void getRems(int i) {
        final View rems = getLayoutInflater().inflate(R.layout.layout_reminders, null, false);


        TextView medName =  rems.findViewById(R.id.med_name_rem);
        TextView days = rems.findViewById(R.id.med_day_rem);
        TextView time= rems.findViewById(R.id.med_time_rem);
        TextView date= rems.findViewById(R.id.med_date_rem);
        TextView left = rems.findViewById(R.id.med_left_rem);

       medName.setText(allrem.get(i).getMed_name());
       days.setText(allrem.get(i).toString());
       date.setText(allrem.get(i).getDose()+" dose");
       left.setText(String.valueOf(allrem.get(i).getMed_quantity())+" tablets left");
       String timeString = Arrays.toString(allrem.get(i)
               .getRem_time())
               .replace("[","")
               .replace("]","");


       time.setText(timeString);

        if((time.getText().toString()).length() >= 24){
            time.startAnimation((Animation) AnimationUtils.loadAnimation(getContext(),R.anim.scroll_anim));
        }
        else{
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                    time.getLayoutParams();
            params.weight = 1.0f;
            time.setLayoutParams(params);
        }
        remlist.addView(rems);


    }
    private void getMissedRems() {
        final View Mrems = getLayoutInflater().inflate(R.layout.layout_missed, null, false);


        missedrem.addView(Mrems);



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_profile, container, false);
        data = new LoadData();
        medlist = parent.findViewById(R.id.mymed_list);
        remlist = parent.findViewById(R.id.myrem_list);
        missedrem = parent.findViewById(R.id.mymissedrem_list);
        Button edit = parent.findViewById(R.id.materialButton);
        final View empty = getLayoutInflater().inflate(R.layout.layout_empty, null, false);
        swpiey = parent.findViewById(R.id.swipeyy);
        allrem = LoadData.allrems;
        if (allrem.size()==0){
            remlist.addView(empty);
        }else{
            for(int i=0; i<LoadData.allrems.size();i++){
                getRems(i);
            }
        }


        swpiey.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                data.datainit();
                allrem = LoadData.allrems;
                remlist.removeAllViews();
                if (allrem.size()==0){
                    remlist.addView(empty);
                }else{
                    for(int i=0; i<LoadData.allrems.size();i++){
                        getRems(i);
                    }
                }
                swpiey.setRefreshing(false);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        getMeds();
        getMeds();
        getMeds();
        getMeds();
        getMeds();

        getMissedRems();
        getMissedRems();
        getMissedRems();
        loadingDia.dismissDialog();
        return parent;
    }


}