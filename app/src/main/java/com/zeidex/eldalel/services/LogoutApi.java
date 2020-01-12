package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.LogoutResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LogoutApi {
    @GET("user/logout")
    Call<LogoutResponse> logoutUser(@Query("token") String token);

    @GET("company/logout")
    Call<LogoutResponse> logoutCompany(@Query("token") String token);

    @GET("salesman/logout")
    Call<LogoutResponse> logoutSalesman(@Query("token") String token);
}
