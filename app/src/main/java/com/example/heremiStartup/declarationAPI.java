package com.example.heremiStartup;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface declarationAPI {


    @POST("/reminders/addmed")
    Call<List<modelMedicineRes>> saveMedicine(@Body modelMedicine modelMedicine);

    @POST("/reminders/addrem")
    Call<List<modelReminderRes>> saveRemidner(@Body modelReminder modelReminder);

    @POST("/reminders/addtime")
    Call<List<modelTimeRes>> saveTime(@Body modelTime modelTime);


    @GET("/reminders/gettime/{id}")
    Call<List<modelTimeRes>> getTime(@Path("id") int id);

    @GET("/reminders/gettime/")
    Call<List<modelTimeRes>> getTime();

    @GET("/reminders/getmed/{id}")
    Call<List<modelMedicineRes>> getMedicine(@Path("id") int id);

    @GET("/reminders/getrem/{id}")
    Call<List<modelReminderRes>> getReminder(@Path("id") int id);

    @GET("/mobileshop/get-products")
    Call<List<modelProduct>> getproducts();

    @GET("/mobileshop/get-allproducts")
    Call<List<modelAllProduct>> getallproducts();

    @GET("/mobileshop/get-pharmacies/{id}")
    Call<List<modelPartialPharma>> getAvailablePharma(@Path("id") int id);

    @GET("/mobileshop/get-pharmacy/{id}")
    Call<List<modelPharmacy>>getPharma(@Path("id") int id);

    @GET("/mobileshop/get-pharmacy/")
    Call<List<modelPharmacy>> getPharma();

    @GET("/mobileshop/get-pharmaproducts/{id}")
    Call<List<modelPharmaProducts>>getPharmaProducts(@Path("id") int id);

    @POST("/mobileshop/add-cart/")
    Call<List<modelCart>> saveCart(@Body modelCart modelCart);

    @GET("/mobileshop/get-cart/{id}")
    Call<List<modelCartDetails>> getCart(@Path("id") int bookId);

    @DELETE("/mobileshop/clear-cart/{id}")
    Call<ResponseBody> deleteCart(@Path("id") int bookId);

    @POST("/mobileshop/save-order")
    Call<modelOrders> saveOrder(@Body modelOrders modelOrders);

    @POST("/mobileshop/save-items")
    Call<ResponseBody> saveItems(@Body modelOrdersItems modelOrdersItems);

    @GET("/mobileshop/get-pendingorder/{id}")
    Call<List<modelOrders>> getPending(@Path("id") int id);

    @POST("/mobileshop/auth/login")
    Call<modelTokens> login(@Body modelLogin modelLogin);

    @GET("/mobileshop/auth/is-verify")
    Call<ResponseBody> verify(@Header("token") String header);

    @GET("/mobileshop/get-user")
    Call<modelCustomer> getUser(@Header("token") String header);

    @POST("/mobileshop/auth/registerm")
    Call<modelTokens> signin(@Body modelSignin signin);

    @PUT("/mobileshop/put-userinfo/{id}")
    Call<ResponseBody> userInfo(@Path("id") int id, @Body modelCustomer modelCustomer);

    @PUT("/mobileshop/decrement-cart/{id}/{uid}")
    Call<ResponseBody> decrementCart(@Path("id") int id, @Path("uid") int uid);

    @PUT("/mobileshop/increment-cart/{id}/{uid}")
    Call<ResponseBody> incrementCart(@Path("id") int id, @Path("uid") int uid);

    @GET("/mobileshop/get-indivproducts")
    Call<List<modelIndivProduct>> indivProd();

    @GET("/mobileshop/order/{id}")
    Call<List<modelOrders>> getOrder(@Path("id") int id);

    @GET("/mobileshop/order-items/{id}")
    Call<List<modelOrderItems>> getOrderItems(@Path("id") int id);


    @GET("/sell/discount/{id}")
    Call<List<modelDiscount>> getDiscount(@Path("id") int id);
}
