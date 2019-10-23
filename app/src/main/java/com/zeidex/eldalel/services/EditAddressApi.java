package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetAddAddressResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface EditAddressApi {

    @PUT("user/update/address/info")
    Call<GetAddAddressResponse> editAddressApi(@Body Map<String, String> partMap);

    @PUT("company/update/address/info")
    Call<GetAddAddressResponse> editAddressApicompany(@Body Map<String, String> partMap);
}
