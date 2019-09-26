package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetDetailProduct;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailProduct {

    @GET("product/{id}")
    Call<GetDetailProduct> getDetailProduct(@Path("id") int id, @Query("token") String token);
}
