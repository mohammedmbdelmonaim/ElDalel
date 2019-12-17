package com.zeidex.eldalel.services;


import com.zeidex.eldalel.response.GetOffers;
import com.zeidex.eldalel.response.GetProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OffersAPI {

    @GET("offers")
    Call<GetOffers> getOffers();

    @GET("products")
    Call<GetProducts> getOffersProducts(@Query("status") String status, @Query("subcat_id") int subCategoryId, @Query("token") String token, @Query("page") int page);

    @GET("products")
    Call<GetProducts> getOffersProductsFromCategories(@Query("status") String status, @Query("cat_id") int categoryId, @Query("token") String token, @Query("page") int page);

}