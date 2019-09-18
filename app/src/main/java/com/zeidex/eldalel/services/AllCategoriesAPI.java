package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetAllCategories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AllCategoriesAPI {
    @GET("categories")
    Call<GetAllCategories> getAllCategories(@Query("without_products") int status);
}
