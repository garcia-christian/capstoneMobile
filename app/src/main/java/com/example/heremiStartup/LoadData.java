package com.example.heremiStartup;

import android.annotation.SuppressLint;
import android.icu.util.Calendar;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadData extends ProfileFragment {
    public static List<modelReminderRes> allrems = new ArrayList<>();
    public static List<modelTimeRes> timeResToday = new ArrayList<>();

    NotifAdapter notifAdapter;
    ProductAdapter productAdapter;
    List<modelReminderRes> allremToday = new ArrayList<>();
    List<modelTimeRes> timeToday = new ArrayList<>();
    List<modelProduct> allProds = new ArrayList<>();
    List<modelAllProduct> alphaProds = new ArrayList<>();
    public static ArrayList<modelLogRes> allData = new ArrayList<>();
    public static ArrayList<modelProduct> prod = new ArrayList<>();
    public static ArrayList<modelAllProduct> allprod = new ArrayList<>();
    public LoadData(NotifAdapter notifAdapter) {
        this.notifAdapter = notifAdapter;
        datainit();

    }

    public LoadData(ProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
        getproducts();
        getAllproducts();
    }
    public LoadData(){

    }

    public void datainit() {
        Call<List<modelReminderRes>> modelMedicineCall = apiClient.getDeclaration().getReminder();
        Boolean check;

        modelMedicineCall.enqueue(new Callback<List<modelReminderRes>>() {
            @Override
            public void onResponse(Call<List<modelReminderRes>> call, Response<List<modelReminderRes>> response) {
                if(response.isSuccessful()){
                    allrems = response.body();
                    allrem = response.body();
                    getTimeforToday();

                }
            }

            @Override
            public void onFailure(Call<List<modelReminderRes>> call, Throwable t) {

            }
        });


    }

    public void getTimeforToday(){

        Call<List<modelTimeRes>> calltimeres = apiClient.getDeclaration().getTime();
        calltimeres.enqueue(new Callback<List<modelTimeRes>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<modelTimeRes>> call, Response<List<modelTimeRes>> response) {
                timeResToday = response.body();

                initData();

            }

            @Override
            public void onFailure(Call<List<modelTimeRes>> call, Throwable t) {

            }
        });

    }

    private int getToday() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int index = 0;

        switch (day) {
            case Calendar.MONDAY:
                index = 3;
                break;
            case Calendar.TUESDAY:
                index = 4;
                break;
            case Calendar.WEDNESDAY:
                index = 5;
                break;
            case Calendar.THURSDAY:
                index = 6;
                break;
            case Calendar.FRIDAY:
                index = 7;
                break;
            case Calendar.SATURDAY:
                index = 8;
                break;
            case Calendar.SUNDAY:
                index = 9;
                break;

        }
        return index;
    }
    public void getremToday(int day){

        for(int i=0; i<allrem.size();i++){
            switch (day){
                case 3:
                    if (allrem.get(i).isMon())
                        allremToday.add(allrem.get(i));

                    break;
                case 4:
                    if (allrem.get(i).isTue())
                        allremToday.add(allrem.get(i));
                    break;
                case 5:
                    if (allrem.get(i).isWed())
                        allremToday.add(allrem.get(i));
                    break;
                case 6:
                    if (allrem.get(i).isThu())
                        allremToday.add(allrem.get(i));
                    break;
                case 7:
                    if (allrem.get(i).isFri())
                        allremToday.add(allrem.get(i));
                    break;
                case 8:
                    if (allrem.get(i).isSat())
                        allremToday.add(allrem.get(i));
                    break;
                case 9:
                    if (allrem.get(i).isSun())
                        allremToday.add(allrem.get(i));
                    break;

            }
        }
    }
    public void notiftoday(int rem){
        timeToday.clear();
        for (int i = 0; i<timeResToday.size();i++){
            if(timeResToday.get(i).getRem_id()==rem){
                timeToday.add(timeResToday.get(i));
            }
        }



    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initData(){

        getremToday(getToday());
        System.out.println("ALL rem size "+ allremToday.size());
        allData.clear();
        for(int i =0; i<allremToday.size();i++){
            notiftoday(allremToday.get(i).getRem_id());
            System.out.println(timeToday.size()+" tt");
            for (int o = 0; o<timeToday.size();o++){
                modelLogRes log = new modelLogRes();
                log.setRemID(allremToday.get(i).getRem_id());
                log.setMedID(allremToday.get(i).getMed_id());
                log.setDateCreated(LocalDate.now()+"");
                log.setTime(timeToday.get(o).getTime());
                log.setDay(LocalDate.now().getDayOfWeek().name());
                log.setTimeID(allremToday.get(i).getRem_id());
                log.setMed_name(allremToday.get(i).getMed_name());
                log.setMed_dose(allremToday.get(i).getDose());

                allData.add(log);
            }
        }
        System.out.println(allData.size()+"size");

    }
    public void getproducts(){
        Call<List<modelProduct>> modelProductCall = apiClient.getDeclaration().getproducts();

        modelProductCall.enqueue(new Callback<List<modelProduct>>() {
            @Override
            public void onResponse(Call<List<modelProduct>> call, Response<List<modelProduct>> response) {
                allProds = response.body();
                getprod();
            }

            @Override
            public void onFailure(Call<List<modelProduct>> call, Throwable t) {

            }
        });
    }
    public void getAllproducts(){
        Call<List<modelAllProduct>> modelProductCall = apiClient.getDeclaration().getallproducts();

        modelProductCall.enqueue(new Callback<List<modelAllProduct>>() {
            @Override
            public void onResponse(Call<List<modelAllProduct>> call, Response<List<modelAllProduct>> response) {
                alphaProds = response.body();
                getallprod();
            }

            @Override
            public void onFailure(Call<List<modelAllProduct>> call, Throwable t) {

            }
        });
    }

    private void getallprod() {
        allprod.clear();
        for(int i = 0; i<alphaProds.size(); i++){
            allprod.add(alphaProds.get(i));
        }
    }

    private void  getprod(){

        prod.clear();
        for(int i = 0; i<5; i++){
            prod.add(allProds.get(i));
        }
    }

















}
