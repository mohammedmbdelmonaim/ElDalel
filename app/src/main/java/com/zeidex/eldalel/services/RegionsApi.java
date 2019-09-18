package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetCountries;
import com.zeidex.eldalel.response.GetRegions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RegionsApi {

    @GET("country/subsidiaries/{id}")
    Call<GetRegions> getRegions(@Path("id") int id);
}
