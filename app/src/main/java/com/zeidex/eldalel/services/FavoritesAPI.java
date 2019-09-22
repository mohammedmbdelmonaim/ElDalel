package com.zeidex.eldalel.services;

import com.zeidex.eldalel.models.GetFavorites;
import com.zeidex.eldalel.response.DeleteFavoriteResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FavoritesAPI {

    @GET("user/my/favorites")
    Call<GetFavorites> getAllFavorites(@Query("token") String token);

    @DELETE("user/remove/from/favorite/{product_id}") /*{product_id}?token={token})*/
    Call<DeleteFavoriteResponse> deleteFavorite(@Path("product_id") int productId, @Query("token") String token);
}
