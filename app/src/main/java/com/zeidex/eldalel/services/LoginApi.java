package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetLoginResponse;
import com.zeidex.eldalel.response.GetRegisterResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("login")
    Call<GetLoginResponse> getLogin(@Body Map<String, String> partMap);
}
