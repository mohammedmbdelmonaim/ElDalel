package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetBasketProducts;
import com.zeidex.eldalel.response.GetHomeProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BasketProductsApi {

    @GET("user/my/cart")
    Call<GetBasketProducts> getBasketProducts(@Query("token") String token);
}
