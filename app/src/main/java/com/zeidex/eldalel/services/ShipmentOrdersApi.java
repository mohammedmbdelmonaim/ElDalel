package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetShipmentOrders;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShipmentOrdersApi {

    @GET("user/shipment/orders/{id}")
    Call<GetShipmentOrders> getShipmentOrders(@Path("id") int id, @Query("token") String token);
}