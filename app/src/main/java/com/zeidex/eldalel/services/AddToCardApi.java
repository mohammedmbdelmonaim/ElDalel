package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetAddToCardResponse;
import com.zeidex.eldalel.response.GetAddToFavouriteResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddToCardApi {

    @POST("user/add/to/cart")
    Call<GetAddToCardResponse> getAddToFavourite(@Body Map<String, String> partMap);
}
