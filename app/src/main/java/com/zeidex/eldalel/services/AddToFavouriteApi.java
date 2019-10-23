package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetAddToFavouriteResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddToFavouriteApi {

    @POST("user/add/to/favorite")
    Call<GetAddToFavouriteResponse> getAddToFavourite(@Body Map<String, String> partMap);

    @POST("company/add/to/favorite")
    Call<GetAddToFavouriteResponse> getAddToFavouritecompany(@Body Map<String, String> partMap);
}
