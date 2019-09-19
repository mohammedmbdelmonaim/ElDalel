package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetAddresses;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddressAPI {

    @GET("user/addresses")
    Call<List<GetAddresses>> getAllAddresses(@Query("token") String token);
}
