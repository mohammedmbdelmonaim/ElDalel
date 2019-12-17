package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchProductAPI {

    @GET("products")
    Call<GetProducts> getProductsFromNameSearch(@Query("product_name") String productName, @Query("token") String token, @Query("page") int page);
}
