package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetBasketCompanyProducts;
import com.zeidex.eldalel.response.GetBasketProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BasketProductsApi {

    @GET("user/my/cart")
    Call<GetBasketProducts> getBasketProducts(@Query("token") String token);

    @GET("company/my/cart")
    Call<GetBasketCompanyProducts> getBasketProductscompany(@Query("token") String token);
}
