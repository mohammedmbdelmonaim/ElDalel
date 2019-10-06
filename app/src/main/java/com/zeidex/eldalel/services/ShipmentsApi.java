package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetShipments;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShipmentsApi {

    @GET("user/shipments")
    Call<GetShipments> getShipments(@Query("token") String token);
}