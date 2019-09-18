package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetDetailProduct;
import com.zeidex.eldalel.response.GetHomeProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DetailProduct {

    @GET("product/{id}")
    Call<GetDetailProduct> getDetailProduct(@Path("id") int id);
}
