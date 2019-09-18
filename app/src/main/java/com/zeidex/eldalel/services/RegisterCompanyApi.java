package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetRegisterCompanyResponse;
import com.zeidex.eldalel.response.GetRegisterResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterCompanyApi {

    @POST("register/company")
    Call<GetRegisterCompanyResponse> getRegister(@Body Map<String, String> partMap);
}
