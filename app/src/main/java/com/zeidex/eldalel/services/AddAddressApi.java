package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetAddAddressResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddAddressApi {

    @POST("user/create/new/address")
    Call<GetAddAddressResponse> getAddressApi(@Body Map<String, String> partMap);
}
