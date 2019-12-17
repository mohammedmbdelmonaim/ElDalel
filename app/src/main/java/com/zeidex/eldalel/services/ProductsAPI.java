package com.zeidex.eldalel.services;


import com.zeidex.eldalel.response.GetProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductsAPI {

    @GET("products")
    Call<GetProducts> getProductsFromSubcategory(@Query("subcat_id") int subcategoryId, @Query("token") String token, @Query("page") int page);

    @GET("products")
    Call<GetProducts> getProductsFromCategory(@Query("cat_id") int categoryId, @Query("token") String token, @Query("page") int page);

    @GET("products")
    Call<GetProducts> getProductsFromSubSubCategory(@Query("sub_subcat_id") int subsubcategoryId, @Query("token") String token, @Query("page") int page);

}