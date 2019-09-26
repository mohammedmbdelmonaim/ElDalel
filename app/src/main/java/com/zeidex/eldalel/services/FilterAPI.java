package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetProducts;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface FilterAPI {
    @GET("products")
    Call<GetProducts> getProductsFromFilter(@QueryMap Map<String, Object> map, @Query("token") String token);
}
