package com.zeidex.eldalel.services;

import com.zeidex.eldalel.models.GetFavorites;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FavoritesAPI {

    @GET("user/my/favorites")
    Call<GetFavorites> getAllFavorites(@Query("token") String token);
}
