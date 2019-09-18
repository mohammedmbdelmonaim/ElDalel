package com.zeidex.eldalel.services;

import com.zeidex.eldalel.response.GetCountries;
import com.zeidex.eldalel.response.GetHomeProducts;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountriesApi {

    @GET("countries")
    Call<GetCountries> getCountries();
}
