package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetProfileInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProfileInfoApi {

    @GET("user/personal/info")
    Call<GetProfileInfo> getProfileInfo(@Query("token") String token);
}