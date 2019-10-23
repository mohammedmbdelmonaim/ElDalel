package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetChangeAddressResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface ChangeAddressToPrimaryApi {

    @PUT("user/update/secondary/address/to/primary")
    Call<GetChangeAddressResponse> updateAddressApi(@Body Map<String, String> partMap);

    @PUT("company/update/secondary/address/to/primary")
    Call<GetChangeAddressResponse> updateAddressApicompany(@Body Map<String, String> partMap);
}
