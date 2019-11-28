package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetSendCodeResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VerifyCodeMobileApi {

    @POST("user/verify/message/code")
    Call<GetSendCodeResponse> verifyCode(@Body Map<String, String> partMap);

    @POST("register/verify")
    Call<GetSendCodeResponse> verifyCodecompany(@Body Map<String, String> partMap);
}
