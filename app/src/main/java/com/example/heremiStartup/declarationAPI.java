package com.example.heremiStartup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface declarationAPI {


    @POST("/reminders/addmed")
    Call<List<modelMedicineRes>> saveMedicine(@Body modelMedicine modelMedicine);

    @POST("/reminders/addrem")
    Call<List<modelReminderRes>> saveRemidner(@Body modelReminder modelReminder);

    @POST("/reminders/addtime")
    Call<List<modelTimeRes>> saveTime(@Body modelTime modelTime);


    @GET("/reminders/getmed")
    Call<List<modelMedicineRes>> getMedicine();


    @GET("/reminders/gettime/{id}")
    Call<List<modelTimeRes>> getTime(@Path("id") int id);

    @GET("/reminders/gettime/")
    Call<List<modelTimeRes>> getTime();

    @GET("/reminders/getmed/{id}")
    Call<List<modelMedicineRes>> getMedicine(@Path("id") int id);

    @GET("/reminders/getrem")
    Call<List<modelReminderRes>> getReminder();

    @GET("/mobileshop/get-products")
    Call<List<modelProduct>> getproducts();
}
