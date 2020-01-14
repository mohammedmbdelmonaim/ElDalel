package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetOffersCategories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OffersCategoriesAPI {
    @GET("categories")
    Call<GetOffersCategories> getOffersCategories(@Query("status") String status, @Query("token") String token);
}
