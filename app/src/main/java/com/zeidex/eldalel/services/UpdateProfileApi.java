package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetUpdateProfileResponse;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UpdateProfileApi {

    @PUT("user/update/personal/info")
    Call<GetUpdateProfileResponse> updateProfile(@Query("token") String token, @Query("language") String language, @Query("firstName") String firstName, @Query("lastName") String lastName, @Query("country_id") int country_id,@Query("city_id") int city_id,@Query("subsidiary_id") int subsidiary_id,@Query("addressHome") String addressHome,@Query("mobile") String mobile, @Query("email") String email);
}
