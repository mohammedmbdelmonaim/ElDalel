package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetActivatePostpaidResponse;
import com.zeidex.eldalel.response.GetMakeOrderResponse;
import com.zeidex.eldalel.response.GetPostPaidResponse;
import com.zeidex.eldalel.response.GetWalletResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MakeOrderApi {

    @POST("user/make/order")
    Call<GetMakeOrderResponse> makeOrderResponse(@Body Map<String, String> partMap);

    @POST("company/make/order")
    Call<GetMakeOrderResponse> makeOrderResponsecompany(@Body Map<String, String> partMap);

    @GET("company/post-pay-available")
    Call<GetPostPaidResponse> getPostPaidStatus(@Query("token") String token);

    @GET("user/wallet")
    Call<GetWalletResponse> getWalletStatus(@Query("token") String token);

    @POST("company/postPay")
    Call<GetActivatePostpaidResponse> activatePostpaid(@Query("token") String token);

}
