package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.DeleteBasketResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeleteBasketAPI {


    @DELETE("user/delete/my/cart/{cart_id}")
    Call<DeleteBasketResponse> deleteBasketProduct(@Path("cart_id") String cartId, @Query("token") String token);

    @DELETE("company/delete/my/cart/{cart_id}")
    Call<DeleteBasketResponse> deleteBasketProductcompany(@Path("cart_id") String cartId, @Query("token") String token);
}
