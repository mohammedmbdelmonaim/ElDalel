package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetOffersCategories;
import com.zeidex.eldalel.response.GetProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewArrivalsAPI {

    @GET("categories")
    Call<GetOffersCategories> getNewArrivalsCategories(@Query("without_products") int withoutProducts, @Query("status") String status);

    @GET("products")
    Call<GetProducts> getNewArrivalsProducts(@Query("subcat_id") int subcategoryId, @Query("status") String status, @Query("token") String token);


}
