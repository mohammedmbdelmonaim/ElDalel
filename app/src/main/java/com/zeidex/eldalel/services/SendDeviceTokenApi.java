package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetSendDeviceTokenResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SendDeviceTokenApi {

    @POST("user/reg/notify")
    Call<GetSendDeviceTokenResponse> sendDeviceTokenResponse(@Body Map<String, String> partMap);

    @POST("company/reg/notify")
    Call<GetSendDeviceTokenResponse> sendDeviceTokenResponsecompany(@Body Map<String, String> partMap);
}
