package com.zeidex.eldalel.services;


import com.zeidex.eldalel.response.GetNewProducts;
import com.zeidex.eldalel.response.GetOffers;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewProductsAPI {

    @GET("home/new/products")
    Call<GetNewProducts> getNewProducts();

}