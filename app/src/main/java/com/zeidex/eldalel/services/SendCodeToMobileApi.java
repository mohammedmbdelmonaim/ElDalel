package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetSendCodeResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SendCodeToMobileApi {

    @POST("user/send/code/by/message")
    Call<GetSendCodeResponse> sendCode(@Body Map<String, String> partMap);

    @POST("company/send/code/by/message")
    Call<GetSendCodeResponse> sendCodecompany(@Body Map<String, String> partMap);
}
