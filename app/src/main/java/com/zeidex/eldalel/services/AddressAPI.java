package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetAddresses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddressAPI {

    @GET("user/addresses")
    Call<GetAddresses> getAllAddresses(@Query("token") String token);

    @GET("company/addresses")
    Call<GetAddresses> getAllAddressescompany(@Query("token") String token);
}
