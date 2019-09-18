package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetAddToFavouriteResponse;
import com.zeidex.eldalel.response.GetLoginResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddToFavouriteApi {

    @POST("user/add/to/favorite")
    Call<GetAddToFavouriteResponse> getAddToFavourite(@Body Map<String, String> partMap);
}
