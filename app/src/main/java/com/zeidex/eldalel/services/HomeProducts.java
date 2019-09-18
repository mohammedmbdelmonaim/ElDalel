package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetHomeProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeProducts {

    @GET("home/category/products")
    Call<GetHomeProducts> getHomeProducts(@Query("token") String token);
}
