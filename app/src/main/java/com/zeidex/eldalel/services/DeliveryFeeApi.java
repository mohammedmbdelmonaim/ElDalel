package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetDeliveryFee;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DeliveryFeeApi {

    @GET("delivery-fee")
    Call<GetDeliveryFee> getDeliveryFee();
}
