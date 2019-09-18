package com.zeidex.eldalel.services;


import com.zeidex.eldalel.response.GetCategorizedOffers;
import com.zeidex.eldalel.response.GetOffers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OffersAPI {

    @GET("offers")
    Call<GetOffers> getOffers();

    @GET("products")
    Call<GetCategorizedOffers> getOffersProducts(@Query("status") String status, @Query("subcat_id") int subCategoryId, @Query("token") String token);


}