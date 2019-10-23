package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetMakeOrderResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MakeOrderApi {

    @POST("user/make/order")
    Call<GetMakeOrderResponse> makeOrderResponse(@Body Map<String, String> partMap);

    @POST("company/make/order")
    Call<GetMakeOrderResponse> makeOrderResponsecompany(@Body Map<String, String> partMap);
}
