package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetChangeQuantityResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChangeQuantityApi {

    @POST("user/cart/change/quantity")
    Call<GetChangeQuantityResponse> getChangeQuantity(@Body Map<String, String> partMap);

    @POST("company/cart/change/quantity")
    Call<GetChangeQuantityResponse> getChangeQuantitycompany(@Body Map<String, String> partMap);
}
