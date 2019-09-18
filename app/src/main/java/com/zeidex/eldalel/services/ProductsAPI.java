package com.zeidex.eldalel.services;


import com.zeidex.eldalel.response.GetProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductsAPI {

    @GET("products")
    Call<GetProducts> getProducts(@Query("subcat_id") int subcategoryId, @Query("token") String token);

    @GET("products")
    Call<GetProducts> getProductsFromSubSubCategory(@Query("sub_subcat_id") int subcategoryId, @Query("token") String token);

}