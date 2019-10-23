package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetRegisterResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterApi {

    @POST("register/user")
    Call<GetRegisterResponse> getRegister(@Body Map<String, String> partMap);

    @POST("register/company")
    Call<GetRegisterResponse> getRegistercompany(@Body Map<String, String> partMap);
}
