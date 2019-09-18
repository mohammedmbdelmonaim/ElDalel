package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetHomeProducts;
import com.zeidex.eldalel.response.GetRegisterResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegisterApi {

    @POST("register/user")
    Call<GetRegisterResponse> getRegister(@Body Map<String, String> partMap);
}
